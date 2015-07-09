package com.gxws.tool.validator.exception;

import com.gxws.tool.common.exception.BaseException;

/**
 * 参数验证错误异常
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public class BoParamValidateException extends BaseException {
	public String getMessage() {
		return this.getMsg();
	}
}
