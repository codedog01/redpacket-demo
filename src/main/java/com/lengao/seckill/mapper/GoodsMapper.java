package com.lengao.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengao.seckill.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户账户表 Mapper 接口
 * </p>
 *
 * @author LengAo
 * @since 2021-10-19
 */
@Mapper
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {



    @Update("update goods set count = count-1 where id =#{goodsId}")
    int decrNum(Long goodsId);

    @Update("update goods set count = count+1 where id =#{goodsId}")
    int incrNum(Long goodsId);
}
