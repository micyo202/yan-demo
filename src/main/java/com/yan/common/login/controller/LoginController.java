package com.yan.common.login.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yan.common.login.model.LoginModel;
import com.yan.common.user.mapper.SysUserMapper;
import com.yan.common.user.model.SysUser;
import com.yan.common.user.model.SysUserExample;
import com.yan.core.controller.BaseController;

/**
 * 名称：LoginController<br>
 *
 * 描述：登录模块<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-08-22 12:52:52<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
@Controller
@RequestMapping("/common/login")
public class LoginController extends BaseController {
	
	/**
	 * 登录方法<br>
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @param remember 是否记住密码（写入cookie）
	 * @return LoginModel 登录模型
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ResponseBody
	public LoginModel signin(String username, String password, boolean remember) {
		
		SysUserMapper mapper = this.getMapper(SysUserMapper.class);
		SysUserExample example = new SysUserExample();
		example.createCriteria().andUserCodeEqualTo(username).andUserPasswordEqualTo(password);
		List<SysUser> userList = mapper.selectByExample(example);
		SysUser user = null;
		
		if (!this.isNull(userList))
			user = userList.get(0);
		
		if(this.isNull(user))
			return new LoginModel(0, "用户名、密码不正确！");
		
		if(Boolean.FALSE.equals(user.getUserValid()))
			return new LoginModel(0, "该用户已失效！");
		
		try{
			Subject subject = SecurityUtils.getSubject();
			if(!subject.isAuthenticated()) { // 当前用户是否已通过身份验证
				UsernamePasswordToken token = new UsernamePasswordToken(username, password, remember);
	            // 使用 shiro 来验证
	            subject.login(token);//验证角色和权限
	            this.getSession().setAttribute("user", user);
			}
			return new LoginModel(1, "/", remember);
        }catch(AuthenticationException e){
        	e.printStackTrace();
        	return new LoginModel(0, "登录失败，未知异常！");
        }
	}

	/**
	 * 退出登录（注销方法）<br>
	 *
	 * @return String 重定向登录页面
	 */
	@RequestMapping("/signout")
	public String signout() {
		Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //this.getSession().invalidate();
		return "redirect:/login";
	}

}
