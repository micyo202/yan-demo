/*
 * Copyright (c) 2017 Yanzheng [https://github.com/micyo202/yan_demo]
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.yan.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yan.common.user.model.SysUser;

/**
 * 名称：LoginFilter<br>
 *
 * 描述：自定义登录过滤器 Filter，对未登录的 *.jsp 进行过滤拦截<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-08-01 14:22:25<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
@Deprecated
public class LoginFilter implements Filter {

	/**
	 * 日志记录类<br>
	 */
	private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);

	/**
	 * 过滤器初始化方法<br>
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	/**
	 * 过滤方法<br>
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getContextPath();

		String loginPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path
				+ "/views/login.jsp";

		String uri = req.getRequestURI();
		log.debug("Yan -> 执行 LoginFilter - doFilter() 请求参数 : uri = " + uri);

		if (uri.endsWith("login.jsp")) {
			chain.doFilter(request, response);
			return;
		}

		SysUser user = (SysUser) req.getSession().getAttribute("user");
		if (null == user) {
			res.sendRedirect(loginPath);
			return;
		}

		chain.doFilter(request, response);
	}
	
	/**
	 * 过滤器销毁方法<br>
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}
