package com.lengao.seckill.service;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/5
 */
public interface RedPacketRainService {
    void action(int count, int amount, int max);

    Long getReadPacket(String userId);
}
