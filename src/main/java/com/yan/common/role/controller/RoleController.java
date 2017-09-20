package com.yan.common.role.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yan.common.role.mapper.RoleMenuRelMapper;
import com.yan.common.role.mapper.SysRoleMapper;
import com.yan.common.role.model.RoleMenuRel;
import com.yan.common.role.model.RoleMenuRelExample;
import com.yan.common.role.model.RoleNode;
import com.yan.common.role.model.SysRole;
import com.yan.common.role.model.SysRoleExample;
import com.yan.core.annotation.MapperInject;
import com.yan.core.controller.BaseController;
import com.yan.core.model.MsgModel;
import com.yan.core.persistence.DelegateMapper;

@Controller
@RequestMapping("/common/role")
public class RoleController extends BaseController {

	private static final String NAMESPACE = "com.yan.common.role.mapper.SysRoleCustomMapper";

	@MapperInject
	private DelegateMapper delegateMapper;

	@MapperInject(SysRoleMapper.class)
	private SysRoleMapper mapper;

	@RequestMapping("/manage")
	public String manage() {
		return "common/role/manage";
	}

	@RequestMapping(value = "/roleTree", method = RequestMethod.POST)
	@ResponseBody
	public List<RoleNode> getRoleTree(String id) {
		if (this.isNull(id)) {
			id = "00000000000000000000000000000000";
		}
		List<RoleNode> nodeList = new ArrayList<>();
		List<RoleNode> rootList = delegateMapper.selectList(NAMESPACE + ".getRoleNode", id);
		for (RoleNode roleNode : rootList) {
			roleNode.setChildren(getRoleNode(roleNode.getId()));
			nodeList.add(roleNode);
		}
		return nodeList;
	}

	@RequestMapping("/{roleId}/add")
	public String add(@PathVariable String roleId, Model model) {
		SysRole pRole = mapper.selectByPrimaryKey(roleId);
		SysRole role = new SysRole();
		role.setRolePid(pRole.getRoleId());
		role.setRoleLevel(pRole.getRoleLevel() + 1);
		role.setRoleType("group");
		role.setRoleValid(pRole.getRoleValid());
		model.addAttribute("role", role);
		return "common/role/addOrEdit";
	}

	@RequestMapping("/{roleId}/edit")
	public String edit(@PathVariable String roleId, Model model) {
		SysRole role = mapper.selectByPrimaryKey(roleId);
		model.addAttribute("role", role);
		return "common/role/addOrEdit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public MsgModel save(SysRole role) {
		String status = "0";// 状态值，0：刷新本级树，1：刷新父级树
		if (this.isNull(role.getRoleId())) {
			// 添加操作
			role.setRoleId(this.getUUID());
			mapper.insertSelective(role);
		} else {
			// 修改操作
			mapper.updateByPrimaryKeySelective(role);
			status = "1";
		}
		return this.resultMsg(status, "保存成功！");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public MsgModel delete(String roleId, String rolePid) {
		SysRoleExample example = new SysRoleExample();
		example.createCriteria().andRolePidEqualTo(roleId);
		mapper.deleteByExample(example);// 删除子级数据
		mapper.deleteByPrimaryKey(roleId);// 删除当前数据
		return this.resultMsg("0", "删除成功！");
	}

	@RequestMapping(value = "/resourceSave", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public MsgModel resourceSave(String roleId, String menuStr) {
		List<String> menuIds = Arrays.asList(menuStr.split(","));
		RoleMenuRelMapper mapper = this.getMapper(RoleMenuRelMapper.class);
		// 先清除历史数据
		RoleMenuRelExample example = new RoleMenuRelExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		mapper.deleteByExample(example);

		// 添加
		for (String menuId : menuIds) {
			if (!this.isNull(menuId.trim())) {
				RoleMenuRel roleMenuRel = new RoleMenuRel();
				roleMenuRel.setRelId(this.getUUID());
				roleMenuRel.setRoleId(roleId);
				roleMenuRel.setMenuId(menuId);
				mapper.insertSelective(roleMenuRel);
			}
		}
		return this.resultMsg("资源保存成功！");
	}

	private List<RoleNode> getRoleNode(String pid) {
		List<RoleNode> roleList = delegateMapper.selectList(NAMESPACE + ".getRoleNode", pid);
		return roleList;
	}

}
