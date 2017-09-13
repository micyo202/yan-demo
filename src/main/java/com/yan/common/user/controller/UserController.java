package com.yan.common.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yan.common.user.mapper.TbSysUserMapper;
import com.yan.common.user.model.TbSysUser;
import com.yan.core.annotation.MapperInject;
import com.yan.core.controller.BaseController;
import com.yan.core.model.PageModel;

/**
 * 名称：UserController<br>
 *
 * 描述：用户管理模块<br>
 *
 * @author Yanzheng 严正<br>
 * 时间：<br>
 * 2017-09-07 15:43:05<br>
 * 版权：<br>
 * Copyright 2017 <a href="https://github.com/micyo202" target="_blank">https://github.com/micyo202</a>. All rights reserved.
 */
@Controller
@RequestMapping("/common/user")
public class UserController extends BaseController {

	@MapperInject(TbSysUserMapper.class)
	private TbSysUserMapper mapper;

	@RequestMapping("/init")
	public String init() {
		return "common/user/init";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public PageModel<TbSysUser> list(int offset, int limit, String search, String sort, String order) {
		this.offsetPage(offset, limit);
		List<TbSysUser> list = mapper.selectByExample(null);
		return this.resultPage(list);
	}

	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request) {
		List<String> fileNames = this.fileUpLoad(request);
		System.out.println(fileNames);
		return "success";
	}

}
