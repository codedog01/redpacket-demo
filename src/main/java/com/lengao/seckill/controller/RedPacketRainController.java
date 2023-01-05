package com.lengao.seckill.controller;

import com.lengao.seckill.service.RedPacketRainService;
import com.lengao.seckill.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/5
 */
@RestController
@RequestMapping("/redPacketRain")
public class RedPacketRainController {

    @Autowired
    RedPacketRainService redPacketRainService;


    @GetMapping("/action")
    public void action(int count, int amount, int max) {
        redPacketRainService.action(count, amount, max);

    }

    @GetMapping("/getReadPacket")
    public Result<String> getReadPacket(String userId) {
        Long amount = redPacketRainService.getReadPacket(userId);
        double v = Double.parseDouble(String.valueOf(amount));
        return Result.ofSuccess(v / 100 + "");
    }
}
