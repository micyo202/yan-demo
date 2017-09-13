package com.yan.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 名称：MapperInject<br>
 *
 * 描述：自定义注解（注入Mapper对象）<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-08-24 17:39:37<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MapperInject {
	
	/**
	 * 对象类型（默认 Object 则认为是 DelegateMapper 类型）<br>
	 *
	 * @return Class<?> 对象类型（反射机制）
	 */
	Class<?> value() default java.lang.Object.class;
}