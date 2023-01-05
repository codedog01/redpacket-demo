package com.lengao.seckill.config;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.lengao.seckill.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>description goes here</p>
 *
 * @author 冷澳
 * @date 2023/1/5
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<Object> exceptionGet(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        e.printStackTrace();
        Class<? extends Exception> exceClazz = e.getClass();
        if (exceClazz.equals(BusinessException.class)) {
            BusinessException ex = (BusinessException) e;
            log.error(e.getMessage());
            return Result.ofFail(ex.getErrCode(), ex.getErrMsg(), ex.getData());
        } else {
            log.error(e.getMessage());
            response.setStatus(500);// 抛出500,而不是原本的
            return Result.ofFail(e);
        }
    }
}
