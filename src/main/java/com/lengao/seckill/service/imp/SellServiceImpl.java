package com.lengao.seckill.service.imp;

import com.google.gson.Gson;
import com.lengao.seckill.mapper.GoodsMapper;
import com.lengao.seckill.mapper.OrderMapper;
import com.lengao.seckill.pojo.Goods;
import com.lengao.seckill.pojo.Order;
import com.lengao.seckill.service.SellService;
import com.lengao.seckill.utils.JedisUtil;
import com.lengao.seckill.utils.SerializableUtil;
import com.lengao.seckill.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.NotSerializableException;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/3
 */
@Service
@Slf4j
public class SellServiceImpl implements SellService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    RabbitTemplate  rabbitTemplate;

    private static final String GOODS_KEY_PRE = "GOODS:";

    @Override
    public boolean sell(Long goodsId) {
        Long decr = jedisUtil.decr(GOODS_KEY_PRE + goodsId);
        if (decr < 0) {
            log.info("商品【{}】抢购失败!", goodsId);
            return false;
        }

        Order r = new Order();
        r.setGoodsId(goodsId);
        r.setOrderNum(SnowflakeIdWorker.generateId());
        rabbitTemplate.convertAndSend("order", r);
        log.info("商品【{}】抢购成功，库存剩余{}", goodsId,decr);
        return true;
    }

    @Override
    public boolean addGoods(Goods goods) {
        int insert = goodsMapper.insert(goods);
        return insert >= 1;
    }

}
