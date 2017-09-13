package com.yan.core.mybatis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * 名称：SqlSessionFactoryBeanExtend<br>
 *
 * 描述：自定义 typeAliasesPackage 支持通配符，为 mybatisMapper.xml 支持别名<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-08-06 18:17:11<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
public class SqlSessionFactoryBeanExtend extends SqlSessionFactoryBean {
	
	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	/**
	 * 日志记录类<br>
	 */
	private static final Logger log = LoggerFactory.getLogger(SqlSessionFactoryBeanExtend.class);

	/**
	 * 设置通配符包名<br>
	 *
	 * @param typeAliasesPackage 带有通配符的包名
	 * @see org.mybatis.spring.SqlSessionFactoryBean#setTypeAliasesPackage(java.lang.String)
	 */
	@Override
	public void setTypeAliasesPackage(String typeAliasesPackage) {
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
		typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ ClassUtils.convertClassNameToResourcePath(typeAliasesPackage) + "/" + DEFAULT_RESOURCE_PATTERN;

		// 将加载多个绝对匹配的所有Resource
		// 将首先通过ClassLoader.getResource("WEB-INF")加载非模式路径部分
		// 然后进行遍历模式匹配
		try {
			List<String> result = new ArrayList<String>();
			Resource[] resources = resolver.getResources(typeAliasesPackage);
			if (resources != null && resources.length > 0) {
				MetadataReader metadataReader = null;
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						metadataReader = metadataReaderFactory.getMetadataReader(resource);
						try {
							result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage()
									.getName());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (result.size() > 0) {
				super.setTypeAliasesPackage(StringUtils.join(result.toArray(), ","));
			} else {
				log.warn("Yan-> 执行 YanSqlSessionFactoryBean - setTypeAliasesPackage() 参数 : typeAliasesPackage = " + typeAliasesPackage + "，未找到任何包");
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.warn("Yan -> 执行 YanSqlSessionFactoryBean - setTypeAliasesPackage() 异常 : IOException = "+ e.getMessage());
		}
	}
}
