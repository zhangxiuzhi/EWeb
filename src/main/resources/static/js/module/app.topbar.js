/**
 *
 * 顶部链接
 * Created by wzj on 2017/5/3.
 */

function JBSFrame_appBar() {
	JBSFrame.call(this);



	//显示顶部用户信息和链接
	this.show_topbar_info = function (state, customerName) {

		document.getElementById("topbar-login-userName").innerHTML = customerName;//显示已登录用户信息
		if (state == "hadlogin") {
			$(".topbar-link li.unlogin").css("display", "none");	//隐藏未登录时信息
			$(".topbar-link li.logon").css("display", "block");//显示已登录用户信息
		}
		if (state == "unlogin") {
			$(".topbar-link li.unlogin").css("display", "");	//显示未登录时信息
			$(".topbar-link li.logon").css("display", "none");//隐藏已登录用户信息
		}

	}

	//退出
	this.esteel_logout = function () {
		this.confirm(null, "确定要退出系统吗？", function () {
			window.localStorage.removeItem("esteel-header-navmenu-submen-module-href");
			window.location.href = '/web/logout';
		});
	}
}

/*
 //body load
 --------------------------------------------------------------------*/
var esteel_appBar;
$(document).ready(function(){
	esteel_appBar = new JBSFrame_appBar();


});

//退出
function esteel_logout(){
	esteel_appBar.esteel_logout();
}