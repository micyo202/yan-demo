package com.yan.core.constant;

/**
 * 名称：DataSourceName<br>
 *
 * 描述：常量定义，枚举类型多数据源名称<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-09-06 09:43:23<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
public enum DataSourceName {

	/**
	 * 默认数据源<br>
	 */
	DEFAULT("defaultDataSource"),
	
	/**
	 * 扩展数据源<br>
	 */
	EXTEND("extendDataSource");

	/**
	 * 数据源名称<br>
	 */
	private String name; 

	/**
	 * 构造方法<br>
	 * 
	 * @param name 数据源名称
	 */
	DataSourceName(String name) {
		this.name = name;
	}

	/**
	 * 获取名称的 get 方法 <br>
	 *
	 * @return String 数据源真实名称
	 */
	public String getName() {
		return name;
	}

}
