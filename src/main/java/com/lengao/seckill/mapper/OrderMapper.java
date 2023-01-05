package com.lengao.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengao.seckill.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/3
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {

}
