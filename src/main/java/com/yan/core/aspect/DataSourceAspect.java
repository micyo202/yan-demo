package com.yan.core.aspect;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import com.yan.core.annotation.DynamicDataSource;
import com.yan.core.constant.DataSourceName;
import com.yan.core.spring.DataSourceContextHolder;

/**
 * 名称：DataSourceAspect<br>
 *
 * 描述：切换数据源 aop 切面（order=1 优先级最高）<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-09-06 10:29:16<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
public class DataSourceAspect implements MethodBeforeAdvice, AfterReturningAdvice {

	/**
	 * 前置通知（用于在事物开启之前切换数据源）<br>
	 * 
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		if (method.isAnnotationPresent(DynamicDataSource.class)) {
			DynamicDataSource dataSource = method.getAnnotation(DynamicDataSource.class);
			if (dataSource.value() != DataSourceName.DEFAULT) {
				DataSourceContextHolder.setDataSource(dataSource.value().getName());
			}
		}
	}

	/**
	 * 后置通知（用于清理切换过的数据源，还原默认数据源）<br>
	 * 
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if (method.isAnnotationPresent(DynamicDataSource.class)) {
			DynamicDataSource dataSource = method.getAnnotation(DynamicDataSource.class);
			if (dataSource.value() != DataSourceName.DEFAULT) {
				DataSourceContextHolder.clearDataSource();
			}
		}
	}

}
