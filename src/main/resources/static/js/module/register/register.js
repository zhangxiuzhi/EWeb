/**
 * 注册 Created by wzj on 2017/11/29.
 */

function JBSFrame_register() {

	JBSFrame.call(this);

	// 初始化UI
	this.initUI = function () {

		// 拖动验证
		$('#dragValidate').dragValidate({
			finish: function () {
				$("#register-btn-getValidateCode").removeClass("disabled");
				$("#register-submit").removeClass("disabled");
			}
		});

		/*	$('#form-register').validetta({
		 validators : {

		 },
		 onValid : function(event) {
		 event.preventDefault();
		 },
		 onError : function(event) {
		 }
		 });*/

	}

	//错误的提示
	this.insertErrorBubble = function (elementId, errorText) {
		var $element = $("#" + elementId);
		this.insertBubble($element,errorText,false);
	}
	//正确的提示
	this.insertCorrectBubble = function (elementId, correctText) {
		var $element = $("#" + elementId);
		this.insertBubble($element,correctText,true);
	}
	//
	this.insertBubble = function($element, text, correct){
		var $inputGroup = $element.parent(".input-group");
		$inputGroup.next(".validetta-msg").remove();

		var pos, W = 0, H = 0;
		var $bubble = $("<div class='validetta-msg'></div>");
		if(correct == true){
			$bubble.addClass(" text-success")
			$bubble.html("<i class='fa fa-check'></i> " + text);
		}else{
			$bubble.addClass(" text-danger")
			$bubble.html("<i class='fa fa-close'></i> " + text);
		}


		W = $inputGroup.width();
		H = $inputGroup.height();
		pos = $element.position();
		H = $element[0].offsetHeight;
		$bubble.css({
			lineHeight: H + "px",
			left: W
		});
		$inputGroup.after($bubble);
	}

}

/*
 * //body load
 * --------------------------------------------------------------------
 */
var esteel_register;
$(document).ready(function(e) {
	esteel_register = new JBSFrame_register();
	// 初始化UI
	esteel_register.initUI();

});


// 确认注册协议
function confirmRegisterRule(ckb) {
	if (!ckb.checked) {
		$("#register-submit").addClass("disabled");
	} else {
		$("#register-submit").removeClass("disabled");
	}
}

// 发送验证码
function sendSms() {
	var phone = $("#mobile").val();
	if (phone.length == 0) {
		esteel_register.insertErrorBubble("mobile", "请先输入手机号");
		return;
	}
	esteel_register.ajaxRequest({
		url : "/register/sendSms",
		data : {
			mobile : phone
		}
	}, function(result) {
		alert("已发送");
		countDownSendSms();
	});
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

var status = true;
function checkNo(){
	var phone = $("#mobile").val();
	if (phone.length == 0) {
		status = false;
		esteel_register.insertErrorBubble("mobile", "手机号码不能空");
	} else {
		if (phone.length == 11) {
			// 正则验证
			if ((/^1[34578]\d{9}$/.test(phone))) {
				phone = $("#mobile").val();
				// 数据库验证
				esteel_register.ajaxRequest({
					url : "/register/checkNo",
					data : {
						mobile : phone
					}
				}, function(data,msg) {
					if(data!=null){
						status = false;
						esteel_register.insertErrorBubble("mobile", "该号码已被注册");
					}else{
						status= true;
						esteel_register.insertCorrectBubble("mobile", "该号码可用");
					}
				});
			} else {
				status = false;
				esteel_register.insertErrorBubble("mobile", "手机号码格式不正确");
			}
		} else {
			status = false;
			esteel_register.insertErrorBubble("mobile", "请输入11位手机号码");
		}
	}
}
// 注册
function register() {
	var phone = $("#mobile").val();
	var codes = $("#code").val();
	var pwd = $("#password").val();
	var vcsrf = $("#_csrf").val();
	// 手机号码验证
	// 验证码验证
	if (codes.length == 0) {
		esteel_register.insertErrorBubble("code", "验证码不能为空");
	}else if(codes.length !=6){
		esteel_register.insertErrorBubble("code", "请输入6位验证码");
	}
	// 密码验证
	var firm = $("#firmPwd").val();
	var len = $("#password").val().length;
	if (len == 0) {
		esteel_register.insertErrorBubble("password", "请输入密码");
	} else {
		if (pwd.length >= 6 && pwd.length <= 20) {

		} else {
			esteel_register.insertErrorBubble("password", "密码格式不正确");
			return;
		}
	}
	if (firm.length == 0) {
		esteel_register.insertErrorBubble("firmPwd", "请输入确认密码");
		return;
	} else {
		if (firm != pwd) {
			esteel_register.insertErrorBubble("firmPwd", "两次密码输入不一致");
			return;
		}
	}
	if(status=='false'){
		alert("请确保手机号正确且未被注册");
		return;
	}
	// 注册
	esteel_register.ajaxRequest({ url : "/register/register",
		data : {
			mobile : phone,
			code : codes,
			password : pwd
		}
	}, function(data,msg) { 
		if (data!=null) {
			// 跳转注册成功页面
			window.location.href = "/user/success";
		} else {
			alert("注册失败，请确保验证码和密码正确");
		}
	});
}
