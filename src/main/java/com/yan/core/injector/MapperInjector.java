package com.yan.core.injector;

import java.lang.reflect.Field;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.yan.core.annotation.MapperInject;
import com.yan.core.persistence.DelegateMapper;

/**
 * 名称：MapperInjector<br>
 *
 * 描述：自定义Mapper注入器，用户注解注入Mapper对象<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-08-24 16:32:37<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
public class MapperInjector implements BeanPostProcessor {

	/**
	 * 根据 spring 配置获取 mybatis 中的 sqlSessionTemplate 模板<br>
	 */
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的初始化任务（前置处理器）<br>
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
			/**
			 * 通过注解（反射）创建 Mapper 实例<br>
			 * 
			 * @see org.springframework.util.ReflectionUtils.FieldCallback#doWith(java.lang.reflect.Field)
			 */
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				ReflectionUtils.makeAccessible(field);
				if (field.isAnnotationPresent(MapperInject.class)) {
					MapperInject annotation = field.getAnnotation(MapperInject.class);
					Class<?> clazz = annotation.value();
					if ("DelegateMapper".equals(field.getType().getSimpleName())) {
						field.set(bean, new DelegateMapper(sqlSessionTemplate));
					} else {
						field.set(bean, sqlSessionTemplate.getMapper(clazz));
					}
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
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
