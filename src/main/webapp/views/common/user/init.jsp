<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp" %>
<%@ include file="/common/include_common.jsp" %>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>用户管理</title>
</head>
<body>
<div id="main">
	<div id="toolbar">
		<a class="waves-effect waves-button" href="javascript:createAction();" ><i class="zmdi zmdi-plus"></i> 新增用户</a>
		<a class="waves-effect waves-button" href="javascript:updateAction();" ><i class="zmdi zmdi-edit"></i> 编辑用户</a>
		<a class="waves-effect waves-button" href="javascript:deleteAction();" ><i class="zmdi zmdi-close"></i> 删除用户</a>
	</div>
	<table id="table"></table>
</div>
<script type="text/javascript">
var $table = $('#table');
$(function() {
	$(document).on('focus', 'input[type="text"]', function() {
		$(this).parent().find('label').addClass('active');
	}).on('blur', 'input[type="text"]', function() {
		if ($(this).val() == '') {
			$(this).parent().find('label').removeClass('active');
		}
	});
	$table.bsTable({
		url: '${pageContext.request.contextPath}/common/user/list',
		idField: 'userCode',// 指定主键列
		search: true,
		columns: [
			{field: 'state', checkbox: true},
			{field: 'userCode', title: '用户名', align: 'center'},
			{field: 'userName', title: '姓名', align: 'center'},
			{field: 'userAddress', title: '地址', align: 'center'},
			{field: 'userEmail', title: '邮箱', align: 'center'},
			{field: 'userPhone', title: '电话', align: 'center'},
			{field: 'userBirthday', title: '生日', align: 'center'},
			{field: 'userJoindate', title: '注册时间', align: 'center'},
			{field: 'userPhoto', title: '照片', align: 'center'},
			{field: 'userType', title: '用户类型', align: 'center'},
			{field: 'userValid', title: '是否有效', align: 'center'},
			{field: 'action', title: '操作', align: 'center', formatter: 'actionFormatter', events: 'actionEvents', clickToSelect: false}
		]
	});
});
function actionFormatter(value, row, index) {
    return [
        '<a class="edit ml10" href="javascript:void(0)" data-toggle="tooltip" title="编辑"><i class="glyphicon glyphicon-edit"></i></a>　',
        '<a class="remove ml10" href="javascript:void(0)" data-toggle="tooltip" title="删除"><i class="glyphicon glyphicon-remove"></i></a>'
    ].join('');
}

window.actionEvents = {
    'click .edit': function (e, value, row, index) {
        alert('You click edit icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    },
    'click .remove': function (e, value, row, index) {
        alert('You click remove icon, row: ' + JSON.stringify(row));
        console.log(value, row, index);
    }
};
// 删除
function deleteAction() {
	var rows = $table.bootstrapTable('getSelections');
	if (rows.length == 0) {
		$.confirm({
			title: false,
			content: '请至少选择一条记录！',
			autoClose: 'cancel|3000',
			backgroundDismiss: true,
			buttons: {
				cancel: {
					text: '取消',
					btnClass: 'waves-effect waves-button'
				}
			}
		});
	} else {
		$.confirm({
			type: 'red',
			animationSpeed: 300,
			title: false,
			content: '确认删除该系统吗？',
			buttons: {
				confirm: {
					text: '确认',
					btnClass: 'waves-effect waves-button',
					action: function () {
						var ids = new Array();
						for (var i in rows) {
							ids.push(rows[i].systemId);
						}
						$.alert('删除：id=' + ids.join("-"));
					}
				},
				cancel: {
					text: '取消',
					btnClass: 'waves-effect waves-button'
				}
			}
		});
	}
}
</script>
</body>
</html>