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

		//获取用户手机号码
		esteel_safe_updatePassword.ajaxRequest({
			url : "/user/getMobile",
			data : {}
		}, function(data, msg) {
			document.getElementById('phone').innerHTML = msg;
		});
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
			if(target == "#updatePwd-step-3" && !updatePwd()){
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

//获取验证码
function getCode(){
	var phone = document.getElementById('phone').innerHTML;
	esteel_safe_updatePassword.ajaxRequest({
		url : "/register/sendSms",
		data : {
			mobile : phone
		}
	}, function(data, msg) {
		alert(msg);
		//倒计时
		countDownSendSms();
	});
	
}
//验证登录用户身份
function validate(){
	var result = false;
	var csrf = $("#csrf").val()
	var phone = document.getElementById('phone').innerHTML;
	var code  = $("#code1").val();
	var passwd  = $("#code2").val();
	var len = code.length;
	//验证
	if(code==''){
		esteel_safe_updatePassword.insertErrorBubble("code1","验证码不能为空");
		return false;
	}else{
		esteel_safe_updatePassword.insertCorrectBubble("code1","");
	}
	if(len!=6){
		esteel_safe_updatePassword.insertErrorBubble("code1","请输入6位有效验证码");
		return false;
	}else{
		esteel_safe_updatePassword.insertCorrectBubble("code1","");
	}
	if(passwd==''){
		esteel_safe_updatePassword.insertErrorBubble("code2","密码不能为空");
		return false;
	}else{
		esteel_safe_updatePassword.insertCorrectBubble("code2","");
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
		async:false,
		data : {
			mobile : phone,
			code : code,
			password : passwd
		}
	}, function(data, msg) {
		esteel_safe_updatePassword.removeBubble("code1");
		esteel_safe_updatePassword.removeBubble("code2");
		if(data!=null){
			alert(msg);
			result = false;
		}else{
			result = true;
		}
	});
	return result;
}
//修改密码
function updatePwd(){
	var pwdO = $("#oldPwd").val();
	var pwdN  = $("#newPwd").val();
	if(pwdO==''||pwdN==''){
		alert("密码不能为空");
		return false;
	}
	if(pwdN.length<6&&pwdN.length>20){
		alert("请输入6-20之间有效数字和字母组合");
		return false;
	}
	if(pwdN != pwdO){
		alert("两次输入的密码不一致");
		return false;
	}
	esteel_safe_updatePassword.ajaxRequest({
		url : "/user/resetPwd",
		async:false,
		data : {
			passwordA : pwdO,
			passwordB : pwdN
		}
	}, function(data, msg) {
		if(data!=null){
			alert(msg);
			result = false;
		}else{
			result = true;
		}
	});
	return result;
}



//验证码倒计时
function countDownSendSms(){
	$("#register-btn-getValidateCode").addClass("disabled");
	var s = 60;
	var clock = setInterval(function(){
		if(s ==0){
			clearInterval(clock);
			$("#register-btn-getValidateCode").removeClass("disabled").text("获取验证码");
			return false;
		}
		s--;
		$("#register-btn-getValidateCode").text("重发验证码("+s+")");
	},1000)

}















