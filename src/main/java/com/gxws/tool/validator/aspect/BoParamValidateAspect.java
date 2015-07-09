package com.gxws.tool.validator.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.common.exception.BaseException;
import com.gxws.tool.validator.core.ResultReflectCore;
import com.gxws.tool.validator.core.ValidateCore;

/**
 * 获取要验证参数对象的aop bean
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public class BoParamValidateAspect {

	private ValidateCore vcore = new ValidateCore();

	private ResultReflectCore rcore = new ResultReflectCore();

	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 用于验证参数
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param pjp
	 *            aop JoinPoint对象
	 * @return bo bo方法返回的对象
	 * @throws Throwable
	 *             异常
	 * @since 1.0
	 */
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object[] os = pjp.getArgs();
		if (null != os && 0 < os.length) {
			Object target = pjp.getTarget();
			String methodName = pjp.getSignature().getName();
			try {
				vcore.validate(os, methodName, target.getClass());
			} catch (BaseException e) {
				log.error(e.getMessage(), e);
				return rcore.result(os, methodName, target.getClass(), e);
			}
		}
		return pjp.proceed();
	}
}
