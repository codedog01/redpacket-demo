package com.lengao.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/3
 */
@Data
@TableName("order")
public class Order implements Serializable {

    @TableId(type= IdType.ASSIGN_ID)
    Long id;
    Long orderNum;
    Long user;
    Long goodsId;
}
