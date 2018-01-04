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
		$("#register-btn-getValidateCode").addClass("disabled");
		countDownSendSms();
	});
}
//验证码倒计时
function countDownSendSms(){
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
//手机验证
var status = true;
function checkNo(){
	var phone = $("#mobile").val();
	if (phone.length == 0) {
		status = false;
		esteel_register.insertErrorBubble("mobile", "请输入手机号");
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
						status = true;
						esteel_register.insertErrorBubble("mobile", "");
					}else{
						status= false;
						alert("该号码还未注册，请您先注册");
						esteel_register.insertErrorBubble("mobile", "该号码还未注册，请您先注册");
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
function checkCode(){
	var codes = $("#code").val();
	if(codes==""){
		esteel_register.insertErrorBubble("code", "请输入验证码");
		status = false;
	}
	if(codes.length!=6){
		esteel_register.insertErrorBubble("code", "请输入6为验证码");
		status = false;
	}else{
		esteel_register.insertErrorBubble("code", "");
	}
	
}
//验证身份
function submitDate() {
	if(status=="false"){
		return;
	}
	var phone = $("#mobile").val();
	var codes = $("#code").val();
	// 注册
	esteel_register.ajaxRequest({ url : "/register/findPwd",
		data : {
			mobile : phone,
			code : codes,
		}
	}, function(data,msg) { 
		if (msg=="1") {
			// 跳转注册成功页面
			window.location.href = "/register/resetPwd";
		} else {
			alert("注册失败，请确保验证码和密码正确");
		}
	});
}
