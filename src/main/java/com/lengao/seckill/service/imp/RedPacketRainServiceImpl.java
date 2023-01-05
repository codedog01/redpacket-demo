package com.lengao.seckill.service.imp;

import com.lengao.seckill.config.BusinessException;
import com.lengao.seckill.service.RedPacketRainService;
import com.lengao.seckill.utils.RandSplitNumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Random;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/5
 */
@Service
public class RedPacketRainServiceImpl implements RedPacketRainService {

    @Autowired
    JedisSentinelPool jedisSentinelPool;


    /**
     * 发起红包雨
     * @param count 红包数量
     * @param amount 红包雨总金额(元)
     * @param max 单个红包最大金额
     */
    @Override
    public void action(int count, int amount, int max) {
        Jedis jedis = jedisSentinelPool.getResource();
        String[] strings = RandSplitNumUtils.genRandList(amount * 100, count, 10, max).stream().map(Object::toString).toArray(String[]::new);
        String key = "RAIN:REDPACKET";
        jedis.del(key);
        jedis.lpush(key, strings);
    }

    @Override
    public Long getReadPacket(String userId) {
        String luaScript = "if redis.call('hexists', KEYS[3], KEYS[4]) == 1 then " +
                "  return -1 " +
                "else " +
                "  local luckMoney = redis.call('rpop', KEYS[1]); " +
                "  if luckMoney then " +
                "    local data = {}" +
                "    data['amount'] = luckMoney;" +
                "    data['userId'] = KEYS[4];" +
                "    local re = cjson.encode(data); " +
                "    redis.call('hset', KEYS[3], KEYS[4], 1); " +
                "    redis.call('lpush', KEYS[2], re); " +
                "    return re; " +
                "  end " +
                "end " +
                "return -2";
        Jedis jedis = jedisSentinelPool.getResource();
        Object res = jedis.eval(luaScript, 4, "RAIN:REDPACKET", "RAIN:USERPACKET", "RAIN:HASGET", userId);

        if (res instanceof String) {
            if (res.equals("-1")) {
                throw new BusinessException(500, "已经参加过本轮红包雨");
            } else if (res.equals("-2")) {
                throw new BusinessException(500,"该轮红包雨已结束");
            }
        }

        return (Long) res;
    }
    public static void main(String[] args) {
        Random random = new Random();
        int i = random.nextInt(100);

    }
}
