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
// 手机验证
function checkMobile(mobile) {
	if (mobile.length == 0) {
		alert("请输入手机号");
	} else {
		if (mobile.length == 11) {
			// 正则验证
			if ((/^1[34578]\d{9}$/.test(mobile))) {
				var phone = $("#mobile").val();
				// 数据库验证
				$.post("http://localhost:8888/user/checkNo", {
					mobile : phone
				}, function(result) {
					if (result.success == true) {
						$("#phoneStatus").val(false);
						alert("该号码已被注册");
					} else {
						$("#phoneStatus").val(true);
						// $("#mobile").parent().append($('<span>').text(√).css("color","green"));
					}
				});
			} else {
				$("#phoneStatus").val(false);
				alert("手机号码有误，请重填");
			}
		} else {
			$("#phoneStatus").val(false);
			alert("请输入正确的手机号");
		}
	}
}

// 发送验证码
function sendSms() {
	
	var phone = $("#mobile").val();
	$.post("http://localhost:8888/user/sendSms", {mobile :phone}, function(result) {
		if (result.success == true) {
			alert("已发送");
		} else {
			alert("发送失败");
		}
	});
}
// 验证密码
function firmLength() {
	var length = $("#password").val().length;
	if (length >= 6 && length <= 20) {

	} else if(length==0){
		$("#pwd").val(false);
		alert("请输入密码");return;
	}else{
		$("#pwd").val(false);
		alert("密码格式不正确");
	}
}
// 验证密码
function confirm() {
	var pwd = $("#password").val();
	var firm = $("#firmPwd").val();
	var len = $("#password").val().length;
	if (firm.length == 0) {
		$("#pwd").val(false);
		alert("确认密码不能为空");return;
	}
	if (!firm == pwd) {
		$("#pwd").val(false);
		alert("两次密码输入不一致");
	} else {
		if (len >= 6 && len <= 20) {
			$("#pwd").val(true);
		} else {
			$("#pwd").val(false);
		}
	}
}
// 注册
function register() {
	var pass = $("#pwd").val();
	var status = $("#phoneStatus").val();
	
	var phone = $("#mobile").val();
	var codes = $("#code").val();
	var pwd = $("#firmPwd").val();
	if(codes==""){
		alert("验证码不能为空");
		return;
	}
	if(pass&&status){
		
		// alert(phone+"---"+codes+pwd)
		$.post("http://localhost:8888/user/register", {
			mobile :phone,
			code : codes,
			password : pwd
		}, function(data) {
			if (data.success == true) {
				//跳转注册成功页面
				window.location.href="http://localhost:8888/user/success";
			} else {
				alert(data.msg);
				// alert("发送失败");
			}
		});
	}else{
		if(phone==""){
			alert("号码不能为空");
		}
		if(pwd==""){
			alert("密码不能为空");
		}
	}
}
