/**
 * 会员中心-账号安全-修改密码
 * Created by wzj on 2017/11/30.
 */

function JBSFrame_safe_updatePassword() {

	JBSFrame.call(this);


	//初始化UI
	this.initUI = function () {
		//主菜单栏
		//当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(ComponentMemberSidebar,{focusNode:{name:"accountSafe",text:"账号安全"}}), document.getElementById("component-sidebar"));


	}

	//渲染表单元素
	this.renderFormElement = function(){

		//渲染步骤
		this.renderStep();
	}

	//渲染步骤
	this.renderStep = function(){

		var navListItems = $('#updatePwd-panel li'),
			wellsBtn = $(".updateForm-step-content a.step-btn");
		$('.updateForm-step-content').hide();

		wellsBtn.click(function(e){
			e.preventDefault();
			var target = $(this).attr('href');
			var $item;
			for(var i=0;i<navListItems.length;i++){
				if($(navListItems[i]).data("href") == target){
					$item = $(navListItems[i])
				}
			}
			/*--------------*/
			//step 1 验证
			if(target == "#updatePwd-step-2" && !validate()){
				return false;
			}
			//step 2 验证
			if(target == "#updatePwd-step-3" && !validate()){
				return false;
			}
			
			/*--------------*/
			
			//显示当前步骤
			navListItems.removeClass('active');
			$item.addClass('active');
			//完成上个步骤
			$item.prev().addClass("done");
			//显示当前步骤面板
			
			$(".updateForm-step-content").hide();
			$(target).show();
		});
		//默认显示第一个步骤
		$('#updatePwd-step-1').show();

	}

	/*---------------------------------------------------------------------------------------------------------------------------*/

	//初始化路由
	this.initRouter = function () {

		//页面路由，在页面设置
		var offerRoutes = {}
		$("#component-sidebar .nav-list a").each(function(idx,elem){
			var href = elem.getAttribute("href");
			var rr = elem.getAttribute("name");
			offerRoutes[rr] = self.loadRouter
		})
		$("#router-linkNode a").each(function(idx,elem){
			var rr = elem.getAttribute("linkNode")
			offerRoutes[rr] = self.loadRouter
		})
		var router = Router(offerRoutes);
		router.configure({
			on: self.selectTypeTab	//切换路由后设置高亮标签
		});
		router.init('/'+$("#router-linkNode >a.selected").attr("linkNode"));//初始化页面
	}

	this.loadRouter = function(){
		var path = window.location.hash.slice(2);
		$("#router-pageCotainer").load('/view/safe/'+path+".html", function(){
			self.renderFormElement();//渲染表单元素
		});	//加载静态文件
	}

	//切换路由后设置高亮标签
	this.selectTypeTab = function(){
		var path = window.location.hash.slice(2);
		$("#router-linkNode >a").removeClass("selected");
		$("#router-linkNode >a[linkNode='"+path+"']").addClass("selected");
	}

	/*---------------------------------------------------------------------------------------------------------------------------*/
	var self = this;

}


/*
 //body load
 --------------------------------------------------------------------*/
var esteel_safe_updatePassword;
$(document).ready(function (e) {
	esteel_safe_updatePassword = new JBSFrame_safe_updatePassword();
	//初始化UI
	esteel_safe_updatePassword.initUI();
	//初始化路由
	esteel_safe_updatePassword.initRouter();
});



//页面加载获取登录用户手机号码
$(document).ready(function(){
	//var csrf = $("#csrf").val();{_csrf:csrf},
	$.get("/user/getMobile",function(data){
		document.getElementById('phone').innerHTML=data;
	});
	
});
//获取验证码
function getCode(){
	var phone = document.getElementById('phone').innerHTML;
	esteel_safe_updatePassword.ajaxRequest({
		url : "/user/sendSms",
		data : {
			mobile : phone
		}
	}, function(data, msg) {
		alert(msg);
	});
	
}
//验证登录用户身份
function validate(){
	var csrf = $("#csrf").val()
	var phone = document.getElementById('phone').innerHTML;
	var code  = $("#code1").val();
	var passwd  = $("#code2").val();
	var len = code.length;
	//验证
	if(code==''){
		alert("验证码不能为空!");
		return false;
	}
	if(len!=6){
		alert("请输入6位有效验证码");
		return false;
	}
	if(passwd==''){
		alert("密码不能为空");
		return false;
	}
	/*$.get("/user/checkIdentity",{
		mobile : phone,
		code : code,
		_csrf:csrf
	},function(data){
		if(data.msg=='验证码错误'||data.msg=='验证码已失效'){
			return false;
		}
		//return true;
	});*/
	//校验验证码
	esteel_safe_updatePassword.ajaxRequest({
		url : "/user/checkIdentityPwd",
		data : {
			mobile : phone,
			code : code,
			password : passwd
		}
	}, function(data, msg) {
	});
	return true;
}
//修改密码
function updatePwd(){
	var pwdO = $("#oldPwd").val();
	var pwdN  = $("#oldPwd").val();
	if(pwdO==null||pwdO==null){
		alert("密码不能为空");
		return false;
	}
	if(pwdN.length<6&&pwdN.length>20){
		alert("请输入6-20之间有效数字和字母组合");
		return false;
	}
	if(!pwdN.equal(pwdO)){
		alert("");
		return false;
	}
	esteel_safe_updatePassword.ajaxRequest({
		url : "/user/updtPwd",
		data : {
			passwordA : pwdO,
			passwordB : pwdN
		}
	}, function(data, msg) {
	});
	return true;
	
	
}


















