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
package com.yan.core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yan.common.login.model.LoginUser;

/**
 * 名称：LoginInterceptor<br>
 *
 * 描述：自定义登录Interceptor拦截器，用户拦截未登录的请求<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-08-01 12:45:08<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
@Deprecated
public class LoginInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	/**
	 * 在请求处理方法之前执行，返回 true 执行下一个拦截器，返回 false 不执行下面的拦截器<br>
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		LoginUser user = (LoginUser) request.getSession().getAttribute("user");

		if (null != user) {
			return true;
		}

		String uri = request.getRequestURI();
		log.debug("Yan -> 执行 LoginInterceptor - preHandle() 拦截到请求的 uri = " + uri);

		if (uri.contains("/signin")) {
			return true;
		}

		// if (uri.endsWith("login")) {}

		if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			// 如果是ajax请求响应头会有，x-requested-with
			log.debug("Yan -> 执行 LoginInterceptor - preHandle() 当前是ajax请求.");
			PrintWriter out = response.getWriter();
			out.print("nologin");// session失效
			out.flush();
			return false;
		} else {
			log.debug("Yan -> 执行 LoginInterceptor - preHandle() 用户没有登录.");
			response.sendRedirect(request.getContextPath() + "/views/login.jsp");
			return false;
		}

	}

	/**
	 * 在请求处理方法之后执行<br>
	 *
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex)
			throws Exception {
	}

	/**
	 * 在DispatcherServlet处理后执行（清理工作）<br>
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {
	}

}
