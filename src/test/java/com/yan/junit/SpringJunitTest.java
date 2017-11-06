package com.yan.junit;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yan.core.annotation.LogInject;
import com.yan.core.annotation.MapperInject;
import com.yan.core.persistence.DelegateMapper;
import com.yan.core.spring.DataSourceContextHolder;
import com.yan.demo.product.mapper.TbProductMapper;
import com.yan.demo.product.model.TbProduct;

/**
 * @ClassName: SpringJunitTest
 * @Description: 单元测试类
 * @author Yanzheng
 * @date 2017-08-02 17:37:58
 */
@RunWith(SpringJUnit4ClassRunner.class) // 使用 Spring Junit4 单元测试
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml") // 加载配置文件
public class SpringJunitTest {

    @LogInject
    private static Logger log;

    @MapperInject
    private DelegateMapper delegateMapper;

    @MapperInject(TbProductMapper.class)
    private TbProductMapper mapper;

    @Test
    public void test() {

        log.debug("Yan -> 执行 Junit - test() 开始...[Begin]...");

        List<Map<String, Object>> sysUserList = delegateMapper.selectList("com.yan.junit.JunitMapper.getSysUser");
        System.out.println("Yan -> sysUserList : " + sysUserList);

        DataSourceContextHolder.setDataSource("extendDataSource");// 切换数据源
        List<TbProduct> productList = mapper.selectByExample(null);
        System.out.println("Yan -> productList" + productList);
        DataSourceContextHolder.clearDataSource();// 清楚数据源（恢复默认数据源）

        List<Map<String, Object>> logList = delegateMapper.selectList("com.yan.junit.JunitMapper.getLoggingEvent");
        System.out.println("Yan -> logList : " + logList);

        log.debug("Yan -> 执行 Junit - test() 完毕...[End]...");
    }

}