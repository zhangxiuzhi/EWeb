/**
 * 会员中心-账号安全-修改验证邮箱 Created by wzj on 2017/12/5.
 */

function JBSFrame_safe_verifyMail() {

	JBSFrame.call(this);

	// 初始化UI
	this.initUI = function() {
		// 主菜单栏
		// 当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(
				ComponentMemberSidebar, {
					focusNode : {
						name : "accountSafe",
						text : "账号安全"
					}
				}), document.getElementById("component-sidebar"));

	}

	// 渲染表单元素
	this.renderFormElement = function() {

		// 渲染步骤
		this.renderStep();

		//获取用户手机号码
		esteel_safe_verifyMail.ajaxRequest({
			url : "/user/getMobile",
			data : {}
		}, function(data, msg) {
			document.getElementById('phone').innerHTML = msg;
		});

	}

	// 渲染步骤
	this.renderStep = function() {

		var navListItems = $('#updateMail-panel li'), wellsBtn = $(".updateForm-step-content a.step-btn");
		$('.updateForm-step-content').hide();

		wellsBtn.click(function(e) {
			e.preventDefault();
			var target = $(this).attr('href');
			var $item;
			for (var i = 0; i < navListItems.length; i++) {
				if ($(navListItems[i]).data("href") == target) {
					$item = $(navListItems[i])
				}
			}

			/*--------------*/
			// step 1 验证
			if (target == "#updateMail-step-2" && !checkIddity()) {
				return false;
			}
			// step 2 验证
			if (target == "#updateMail-step-3" && !checkMail()) {
				return false;
			}
			/*--------------*/

			// 显示当前步骤
			navListItems.removeClass('active');
			$item.addClass('active');
			// 完成上个步骤
			$item.prev().addClass("done");
			// 显示当前步骤面板
			$(".updateForm-step-content").hide();
			$(target).show();
		});
		// 默认显示第一个步骤
		$('#updateMail-step-1').show();

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
		var router = Router(offerRoutes);
		router.configure({
			on : self.selectTypeTab
		// 切换路由后设置高亮标签
		});
		router.init('/' + $("#router-linkNode >a.selected").attr("linkNode"));// 初始化页面
	}

	this.loadRouter = function() {
		var path = window.location.hash.slice(2);
		$("#router-pageCotainer").load('/view/safe/' + path + ".html",
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
var esteel_safe_verifyMail;
$(document).ready(function(e) {
	esteel_safe_verifyMail = new JBSFrame_safe_verifyMail();
	// 初始化UI
	esteel_safe_verifyMail.initUI();
	// 初始化路由
	esteel_safe_verifyMail.initRouter();

});

// 获取验证码
function getCode() {
	var phone = document.getElementById('phone').innerHTML;
	esteel_safe_verifyMail.ajaxRequest({
		url : "/register/sendSms",
		data : {
			mobile : phone
		}
	}, function(data, msg) {
		alert(msg);
	});
}
// 验证用户信息
function checkIddity() {
	var result = false;
	var phone = document.getElementById('phone').innerHTML;
	var code = $("#code").val();
	if (code == '') {
		alert("请输入验证码");
		return false;
	}
	if (code.length != 6) {
		alert("请输入6位验证码");
		return false;
	}
	esteel_safe_verifyMail.ajaxRequest({
		url : "/user/checkIdentity",
		async : false,
		data : {
			mobile : phone,
			code : code,
			isNull : 1
		}
	}, function(data, msg) {
		if (data != null) {
			result = true;
		} else {
			alert(msg);
			result = false;
		}
	});
	return result;
}
// 验证邮箱
function checkMail() {
	var result = false;
	//邮箱正则
	var reg =  /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var email = $("#email").val();
	if (email == '') {
		alert("请输入新邮箱地址");
		return false;
	}
	if(reg.test(email)){
	}else{
		alert("邮箱格式错误");
		return false;
	}
	//提交发送邮件
	esteel_safe_verifyMail.ajaxRequest({
		url : "/user/sendMail",
		async : false,
		data : {
			mail : email,
		}
	}, function(data, msg) {
		if (data != null) {
			document.getElementById('step3').innerHTML = data;
			document.getElementById('title').innerHTML = msg;
			result = true;
		} else {
			result = false;
		}
	});
	return result;

}
