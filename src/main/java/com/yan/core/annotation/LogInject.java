package com.yan.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 名称：LogInject<br>
 *
 * 描述：自定义注解（用户注入Logger对象）<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-08-24 17:38:53<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LogInject {
}