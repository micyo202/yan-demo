package com.yan.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yan.core.constant.DataSourceName;

/**
 * 名称：DynamicDataSource<br>
 *
 * 描述：动态数据源切换注解（默认切换扩展数据源）<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-09-06 11:17:12<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DynamicDataSource {
	
	/**
	 * 需要切换的数据源名称<br>
	 *
	 * @return DataSourceName
	 */
	public DataSourceName value() default DataSourceName.EXTEND;
}
