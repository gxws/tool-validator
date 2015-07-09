package com.gxws.tool.validator.core;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gxws.tool.common.data.dto.BaseDto;
import com.gxws.tool.common.exception.BaseException;

/**
 * 构造异常时的返回对象
 * 
 * @author zhuwl120820@gxwsxx.com
 * @since 1.0
 */
public class ResultReflectCore {

	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 获取bo返回对象
	 * 
	 * @author zhuwl120820@gxwsxx.com
	 * @param os
	 *            bo参数对象
	 * @param methodName
	 *            调用bo的方法名
	 * @param targetClass
	 *            bo对象的类
	 * @param e
	 *            要抛出的异常
	 * @return bo方法返回的对象
	 * @since 1.0
	 */
	public Object result(Object[] os, String methodName, Class<?> targetClass,
			BaseException e) {
		Class<?>[] osClass = new Class[os.length];
		for (int i = 0; i < osClass.length; i++) {
			osClass[i] = os[i].getClass();
		}
		try {
			Method method = targetClass.getMethod(methodName, osClass);
			Class<?> resultClass = method.getReturnType();
			if (null == resultClass) {
				return null;
			}
			for (int i = 0; i < osClass.length; i++) {
				if (resultClass.equals(osClass[i])) {
					if (isSubBaseDto(resultClass)) {
						BaseDto bdto = (BaseDto) os[i];
						bdto.setException(e);
						return bdto;
					}
				}
			}
		} catch (NoSuchMethodException | SecurityException e1) {
			log.error(e1.getMessage(), e);
			return null;
		}
		return null;
	}

	private boolean isSubBaseDto(Class<?> cls) {
		Class<?> sClass = cls.getSuperclass();
		if (Object.class.equals(sClass)) {
			return false;
		} else if (BaseDto.class.equals(sClass)) {
			return true;
		} else {
			return isSubBaseDto(sClass);
		}
	}
}
