/**
 * 会员中心-子账号管理 Created by wzj on 2017/12/8.
 */

function JBSFrame_member_subAccount() {

	JBSFrame.call(this);

	// 初始化UI
	this.initUI = function() {
		// 主菜单栏
		// 当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(
				ComponentMemberSidebar, {
					focusNode : {
						name : "subAccount",
						text : "子账号管理"
					}
				}), document.getElementById("component-sidebar"));

	}

	// 渲染表单元素
	this.renderFormElement = function() {

		// 表格
		this.table = ReactDOM.render(React.createElement(SubAccountList),
				document.getElementById("component-table-subAccount"));

	}

	/*---------------------------------------------------------------------------------------------------------------------------*/

	// 初始化路由
	this.initRouter = function() {

		// 页面路由，在页面设置
		var offerRoutes = {}
		$("#component-sidebar .nav-list a").each(function(idx, elem) {
			var href = elem.getAttribute("href");
			var rr = elem.getAttribute("name");
			offerRoutes[rr] = self.loadRouter
		})
		$("#router-linkNode a").each(function(idx, elem) {
			var rr = elem.getAttribute("linkNode")
			offerRoutes[rr] = self.loadRouter
		})
		console.log(offerRoutes)
		var router = Router(offerRoutes);
		router.configure({
			on : self.selectTypeTab
		// 切换路由后设置高亮标签
		});
		router.init('/' + $("#router-linkNode >a.selected").attr("linkNode"));// 初始化页面
	}

	this.loadRouter = function() {
		var path = window.location.hash.slice(2);
		$("#router-pageCotainer").load('/view/member/' + path + ".html",
				function() {
					self.renderFormElement();// 渲染表单元素
				}); // 加载静态文件
	}

	// 切换路由后设置高亮标签
	this.selectTypeTab = function() {
		var path = window.location.hash.slice(2);
		$("#router-linkNode >a").removeClass("selected");
		$("#router-linkNode >a[linkNode='" + path + "']").addClass("selected");
	}

	/*---------------------------------------------------------------------------------------------------------------------------*/
	var self = this;

}

/*
 * //body load
 * --------------------------------------------------------------------
 */
var esteel_member_account;
$(document).ready(function(e) {
	esteel_member_account = new JBSFrame_member_subAccount();
	// 初始化UI
	esteel_member_account.initUI();
	// 初始化路由
	esteel_member_account.initRouter();
});

// 添加子账号
function add_subAccount() {
	$("#subaccoumt-add-modal").modal("hide");
}

// 更新子账号
function update_subAccount() {
	var userId = $("#subAccount-Id").val();
	var userName = $("#subAccount-name").val();
	var dept = $("#subAccount-department").val();
	var position = $("#subAccount-job").val();

	if (userName == "") {
		esteel_member_account.insertErrorBubble("subAccount-name", "通信地址不能为空");
		return;
	}
	esteel_member_account.ajaxRequest({
		url : "/company/updMember",
		data : {
			userId : userId,
			userName : userName,
			dept : dept,
			position : position
		}
	}, function(data, msg) {
		if (msg == "1") {
			esteel_member_account.table.reloadTable();
			$("#subaccoumt-edit-modal").modal("hide");
		}
	});
}

// 移除企业子账号提示框
function romove_subAccountEditModal(_link) {
	var userId = $(_link).data("id");
	esteel_member_account.confirm("操作", "确定要移除该账号吗？",function(){
		romoveAcc(userId)
	});
	// esteel_member_account.confirm("","");
}
// 移除子账号操作
function romoveAcc(userId) {
	var statuse = true;
	esteel_member_account.ajaxRequest({
		url : "/company/romoveMember",
		data : {
			userId : userId,
		}
	}, function(data, msg) {
		if (msg == "1") {
			alert(123);
			esteel_member_account.table.reloadTable();
		} else {
			alert(456);
		}
	});
}
// 添加子账号验证
function firmName() {
	var name = $("#name").val();
	if (name == "") {
		alert("请输入子账号真实姓名");
	}
}
//用户姓名验证
var status = true;
function chenkUerName() {
	var status;
	var name = $("#name").val();
	var phone = $("#mobile").val();
	if (name == "") {
		esteel_member_account.insertErrorBubble("name", "请输入子账号真实姓名");
		status = false;
	}
}
//手机验证
function checkMobile() {
	var name = $("#name").val();
	var phone = $("#mobile").val();
	if (phone.length == 0) {
		status = false;
		esteel_member_account.insertErrorBubble("mobile", "请输入手机号");
		return;
	} else {
		if (phone.length == 11) {
			// 正则验证
			if ((/^1[34578]\d{9}$/.test(phone))) {
				phone = $("#mobile").val();
				// 数据库验证
				esteel_member_account.ajaxRequest({
					url : "/register/checkNo",
					data : {
						mobile : phone
					}
				}, function(data, msg) {
					if (data != null) {
						status = false;
						esteel_member_account.insertErrorBubble("mobile",
								"该手机号已注册，请联系该账号申请成为您的子账号");
						// alert("该手机号已注册，请联系该账号申请成为您的子账号");
					} else {
						status = true;
					}
				});
			} else {
				status = false;
				esteel_member_account.insertErrorBubble("mobile", "请输入正确的手机号");
				return;
			}
		} else {
			esteel_member_account.insertErrorBubble("mobile", "请输入正确的手机号");
			status = false;
		}
	}
}
// 添加子账号
function add_subAccount() {
	var name = $("#name").val();
	var phone = $("#mobile").val();
	// 保存并发送短信
	if (status == "true") {
		esteel_member_account.ajaxRequest({
			url : "/company/addMember",
			data : {
				mobile : phone,
				userName : name
			}
		}, function(data, msg) {
			if (data != null) {
				status = false;
				alert("该手机号已注册，请联系该账号申请成为您的子账号");
			} else {
				alert("已发送激活密码");
				esteel_member_account.table.reloadTable();
				$("#subaccoumt-add-modal").modal("hide");
				
			}
		});
	} else {
		alert("请填写正确信息");
	}
}

// 重新发送激活短信给未激活子账号
function reSubAccountAdd(_link) {
	var userId = $(_link).data("id");
	esteel_member_account.ajaxRequest({
		url : "/company/reAddUser",
		data : {
			userId : userId
		}
	}, function(data, msg) {
		if(msg=="1"){
			esteel_member_account.table.reloadTable();
			alert("已重新发送激活密码");
		}else{
			alert(msg);
		}
	});
}
