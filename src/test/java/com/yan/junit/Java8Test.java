package com.yan.junit;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 描述：TODO<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2018/2/5 下午3:41<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
public class Java8Test {

    @Test
    public void test() {
        String aa = DateTimeFormatter.ofPattern("yyyy-MM").format(LocalDate.now());
        System.out.println(aa);
    }

}
