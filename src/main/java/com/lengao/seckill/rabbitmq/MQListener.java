package com.lengao.seckill.rabbitmq;

import com.lengao.seckill.mapper.GoodsMapper;
import com.lengao.seckill.pojo.Order;
import com.lengao.seckill.utils.JedisUtil;
import com.lengao.seckill.utils.SerializableUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.NotSerializableException;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/3
 */
@Component
@Slf4j
public class MQListener {

    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "order")
    public void order(Message message, Channel channel) throws IOException {
        long deliverTag = message.getMessageProperties().getDeliveryTag();
        boolean isConsume = false;
        //消费成功，确认消息
        Order order = (Order) SerializableUtil.deserialize(message.getBody());
        isConsume = jedisUtil.exists(order.getOrderNum().toString());
        if (isConsume) {
            rabbitTemplate.convertAndSend("order_done", order);
            channel.basicAck(deliverTag, true);
        } else {
            //nack返回false，并重新回到队列
            channel.basicNack(deliverTag, false, true);
            log.info("{}未付款", order.getOrderNum());

        }
    }

    @RabbitListener(queues = "order_cancel")
    public void order_cancel(Message message, Channel channel) throws IOException {
//        long deliverTag = message.getMessageProperties().getDeliveryTag();
//        Order order = (Order) SerializableUtil.deserialize(message.getBody());
//        Long goodsId = order.getGoodsId();
//        int count = goodsMapper.incrNum(goodsId);
//        if (count >= 1) {
//            channel.basicAck(deliverTag, true);
//        } else {
//            channel.basicNack(deliverTag, false, true);
//        }
        log.info("MQListener.order_cancel");
    }

    @RabbitListener(queues = "order_done")
    public void order_done(Message message, Channel channel) throws IOException {
        long deliverTag = message.getMessageProperties().getDeliveryTag();
        Order order = (Order) SerializableUtil.deserialize(message.getBody());
        Long goodsId = order.getGoodsId();
        int count = goodsMapper.decrNum(goodsId);
        if (count >= 1) {
            channel.basicAck(deliverTag, true);
        } else {
            channel.basicNack(deliverTag, false, true);
        }
        log.info("MQListener.order_done");
    }
}
