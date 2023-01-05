package com.lengao.seckill.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author LengAo
 * @since 2021-10-20
 *       自定义业务异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 5661893550169059771L;

	private Integer errCode;

	private String errMsg;

	private Object data;

	private Map<String, Object> detailMap;

	public BusinessException() {
	}

	/**
	 * 新建业务异常
	 *
	 * @param errCode 异常编码
	 * @param errMsg  异常信息
	 */
	public BusinessException(Integer errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public BusinessException(Integer errCode, String errMsg, Object data) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.data = data;
	}




	/**
	 * 新建业务异常
	 *
	 * @param errCode
	 * @param errMsg
	 * @param request 出现异常时的请求
	 */
	public BusinessException(Integer errCode, String errMsg, Map<String, Object> detailMap) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.detailMap = detailMap;
	}

	public BusinessException(String message, Throwable e) {
		super(message, e);
	}

}
