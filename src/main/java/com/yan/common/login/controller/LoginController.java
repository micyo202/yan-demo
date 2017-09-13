package com.yan.common.login.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yan.common.login.model.LoginModel;
import com.yan.common.user.mapper.TbSysUserMapper;
import com.yan.common.user.model.TbSysUser;
import com.yan.common.user.model.TbSysUserExample;
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
		
		LoginModel loginModel = new LoginModel();

		TbSysUserMapper mapper = this.getMapper(TbSysUserMapper.class);
		TbSysUserExample example = new TbSysUserExample();
		example.createCriteria().andUserCodeEqualTo(username).andUserPasswordEqualTo(password)
				.andUserValidEqualTo(true);
		List<TbSysUser> userList = mapper.selectByExample(example);
		TbSysUser user = null;
		if (!this.isNull(userList)) {
			user = userList.get(0);
		}

		if (!this.isNull(user)) {
			this.getSession().setAttribute("user", user);
			loginModel.setStatus(1);
			loginModel.setMsg("登录成功！");
			loginModel.setUrl("views/index.jsp");
			loginModel.setRemember(remember);
		} else {
			loginModel.setStatus(0);
			loginModel.setMsg("用户名、密码不正确！");
			//loginModel.setUrl("views/login.jsp");
		}

		return loginModel;
	}

	/**
	 * 退出登录（注销方法）<br>
	 *
	 * @return String 重定向登录页面
	 */
	@RequestMapping("/signout")
	public String signout() {
		this.getSession().invalidate();
		return "redirect:/views/login.jsp";
	}

}
