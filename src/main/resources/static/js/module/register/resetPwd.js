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
var status = true;
//密码
function checkPwd(){
	var A = $("#password").val();
	if (A.length == 0) {
		esteel_register.insertErrorBubble("password", "请输入密码");
		status = false;
	} else {
		if (A.length >= 6 && A.length <= 20) {
			
			
		} else {
			esteel_register.insertErrorBubble("password", "密码格式不正确");
			status = false;
		}
	}
	
}
//重置密码
function confirmPwd(){
	var A = $("#password").val();
	var B = $("#firmPwd").val();
	if (B.length == 0) {
		esteel_register.insertErrorBubble("firmPwd", "请输入确认密码");
		status = false;
	} else {
		if(status != "true"){
			esteel_register.insertErrorBubble("password", "密码格式不正确");
			status = false;
		}else{
			if(A!=B){
				esteel_register.insertErrorBubble("firmPwd", "两次密码输入不一致");
				status = false;
			}
		}
	}
}

// 注册
function resetPwd() {
	var A = $("#password").val();
	var B = $("#firmPwd").val();
	if(status != "true"){
		return;
	}
	// 注册
	esteel_register.ajaxRequest({ 
		url : "/register/resetPwd",
		data : {
			resetPwd : A,
			firmPwd : B
		}
	}, function(data,msg) { 
		if (data!=null) {
			if(data[0]==1){
				esteel_register.insertErrorBubble("firmPwd", "两次密码输入不一致");
			}
			if(data[0]==2){
				alert("身份验证已失效");
				window.location.href = "/register/findPwd";
			}
		}else{
			alert("修改成功");
			// 跳转注册成功页面
			window.location.href = "http://10.0.1.214:9900/auth/ulogin";
		} 
		
	});
}
