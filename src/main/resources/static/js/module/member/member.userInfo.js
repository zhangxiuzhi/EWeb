/**
 * 会员中心 Created by wzj on 2017/11/30.
 */

function JBSFrame_member_userInfo() {

	JBSFrame.call(this);

	// 初始化UI
	this.initUI = function() {
		// 主菜单栏
		// 当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(ComponentMemberSidebar, {
			focusNode : {
				name : "userInfo",
				text : "个人资料"
			}
		}), document.getElementById("component-sidebar"));

		//用户性别
		var $gender = $("#component-gender");
		this.grade =  ReactDOM.render(React.createElement(ComponentRadioBox, {
			data : [
				{text: "男", value: "man",name:"user-grade"},
				{ text: "女", value: "female",name:"user-grade"}
			],
			name:$gender.attr("inputName"),
			value:"man"
		}), $gender[0]);

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
var esteel_member_userInfo;
$(document).ready(function(e) {
	esteel_member_userInfo = new JBSFrame_member_userInfo();
	// 初始化UI
	esteel_member_userInfo.initUI();
	// 初始化路由
	//esteel_member_userInfo.initRouter();
});

// 修改帐号信息，用户名验证userId
function checkForm() {
	var username = $("#username").val();
	var userid = $("#userId").val();
	esteel_member_userInfo.ajaxRequest({
		url : "/member/membername",
		data : {
			userId : userid,
			memberName : username
		}
	}, function(data, msg) {
		
	});
}

//企业子账号申请
function apply(){
	var companyName = $("#tradeName").val();
	var username = $("#applyName").val();
	esteel_member_userInfo.ajaxRequest({
		url : "/member/apply",
		data : {
			companyName : companyName,
			username : username
		}
	}, function(data, msg) {
		 $("#change").html(msg);
	});
	
	
	
}
