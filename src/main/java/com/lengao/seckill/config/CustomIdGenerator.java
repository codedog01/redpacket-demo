package com.lengao.seckill.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lengao.seckill.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Component;

/**
 * @author LengAo
 * @date 2021/10/20 8:44
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Long nextId(Object entity) {
        //返回生成的id值即可.
        return SnowflakeIdWorker.generateId();
    }
}
