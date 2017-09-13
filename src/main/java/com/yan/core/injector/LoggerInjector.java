package com.yan.core.injector;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.yan.core.annotation.LogInject;

/**
 * 名称：LoggerInjector<br>
 *
 * 描述：自定义Logger注入器，用户注解注入Logger对象<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-08-24 15:13:36<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
public class LoggerInjector implements BeanPostProcessor {

	/**
	 * 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的初始化任务（前置处理器）<br>
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
			/**
			 * 通过注解（反射）创建 Logger 实例<br>
			 * 
			 * @see org.springframework.util.ReflectionUtils.FieldCallback#doWith(java.lang.reflect.Field)
			 */
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				ReflectionUtils.makeAccessible(field);
				if (field.getAnnotation(LogInject.class) != null) {
					// String fullName = bean.getClass().getName();
					// String simpleName = bean.getClass().getSimpleName();
					// Logger logger = LoggerFactory.getLogger(simpleName);
					Logger logger = LoggerFactory.getLogger(bean.getClass());
					field.set(bean, logger);
				}
			}
		});
		return bean;
	}

	/**
	 * 实例化、依赖注入、初始化完毕时执行（后置处理器）<br>
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}