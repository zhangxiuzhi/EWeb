/**
 * 注册 Created by wzj on 2017/11/29.
 */

function JBSFrame_register() {

	JBSFrame.call(this);

	// 初始化UI
	this.initUI = function() {

		// 拖动验证
		$('#dragValidate').dragValidate({
			finish : function() {
				$("#register-btn-getValidateCode").removeClass("disabled");
				$("#register-submit").removeClass("disabled");
			}
		});

		$('#form-register').validetta({
			validators: {

			},
			onValid : function( event ) {
				event.preventDefault();
			},
			onError : function( event ){
			}
		});

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
		//alert("请输入手机号");
		esteel_register.insertErrorBubble("mobile","请先输入手机号");
		return;
	}
	$.post("http://localhost:8888/user/sendSms", {mobile :phone}, function(result) {
		if (result.success == true) {
			alert("已发送");
		}
	});
}

// 注册
function register() {
	var phone = $("#mobile").val();
	var codes = $("#code").val();
	var pwd = $("#password").val(); 
	//手机号码验证
	if (phone.length == 0) {
		//alert("请输入手机号");
		esteel_register.insertErrorBubble("mobile","手机号码不能空");
	} else {
		if (phone.length == 11) {
			// 正则验证
			if ((/^1[34578]\d{9}$/.test(phone))) {
				var phone = $("#mobile").val();
				// 数据库验证
				$.post("/user/checkNo", {
					mobile : phone
				}, function(result) {
					if (result.success == true) {
						esteel_register.insertErrorBubble("mobile","该号码已被注册");
					} 
				});
			} else {
				esteel_register.insertErrorBubble("mobile","手机号码格式不正确");
			}
		} else {
			esteel_register.insertErrorBubble("mobile","请输入11位手机号码");
			
		}
	}
	//验证码验证
	if(codes.length==0){
		esteel_register.insertErrorBubble("code","验证码不能为空");
	}
	//密码验证
	var firm = $("#firmPwd").val();
	var len = $("#password").val().length;
	if (len==0) {
		esteel_register.insertErrorBubble("password","请输入密码");
	}else{
		if(pwd.length>=6 && pwd.length<=20){
			
		}else{
			esteel_register.insertErrorBubble("password","密码格式不正确");
			return;
		}
	}
	if (firm.length==0) {
		esteel_register.insertErrorBubble("firmPwd","请输入确认密码");
		return;
	}else{
		if(firm!=pwd){
			esteel_register.insertErrorBubble("firmPwd","两次密码输入不一致");
			return;
		}
	}
	//注册
	$.post("/user/register", {
		mobile :phone,
		code : codes,
		password : pwd
	}, function(data) {
		if (data.success == true) {
			//跳转注册成功页面
			window.location.href="/user/success";
		} else {
			alert(data.msg);
			return;
			// alert("发送失败");
		}
	});
	
}
