package com.lengao.seckill.service;

import com.lengao.seckill.pojo.Goods;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/3
 */
public interface SellService {
    boolean sell(Long goodsId);

    boolean addGoods(Goods goods);
}
