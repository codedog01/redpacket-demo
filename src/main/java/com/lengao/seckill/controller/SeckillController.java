package com.lengao.seckill.controller;

import com.lengao.seckill.pojo.Goods;
import com.lengao.seckill.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/3
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    SellService sellService;

    @GetMapping("/sell")
    public boolean sell(Long goodsId) {
        return sellService.sell(goodsId);
    }


    @PostMapping("/addGoods")
    public boolean addGoods(Goods goods) {
        return sellService.addGoods(goods);
    }

}
