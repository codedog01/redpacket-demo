package com.lengao.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/3
 */
@Configuration
public class RabbitMQConfig {

    //交换机和队列要进行绑定:
    //默认的交换机是DirectExchange,
    //每个交换机都需要利用路由键来和队列绑定在一起.
    //如果采用的是DirectExchange交换机,默认情况下,队里的名字就是路由键的名字.
    //该交换机是一对一的,一个消息被发送者发送出去之后,只能被一个消费者接受.
    @Bean
    public Queue order(){
        Map<String, Object> args = deadQueueArgs();   // 队列设置消息过期时间 60 秒
        args.put("x-message-ttl", 60 * 1000);
        return new Queue("order", true, false, false, args);

    }

    @Bean
    public Queue orderCancel(){
        return new Queue("order_cancel");
    }

    @Bean
    public Queue orderDone(){
        return new Queue("order_done");
    }


    /**
     * 死信交换机
     */
    @Bean
    DirectExchange deadExchange() {
        return new DirectExchange("deadExchange", true, false);
    }

    @Bean
    Binding deadRouteBinding( Queue order,Queue orderCancel) {
        return BindingBuilder.bind(orderCancel).to(deadExchange()).with("cancelOrder");
    }

    /**
     * 转发到 死信队列，配置参数
     */
    private Map<String, Object> deadQueueArgs() {
        Map<String, Object> map = new HashMap<>();
        // 绑定该队列到私信交换机
        map.put("x-dead-letter-exchange", "deadExchange");
        map.put("x-dead-letter-routing-key", "cancelOrder");
        return map;
    }

}

