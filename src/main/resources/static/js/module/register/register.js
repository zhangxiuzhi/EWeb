/**
 * 注册
 * Created by wzj on 2017/11/29.
 */


function JBSFrame_register() {

	JBSFrame.call(this);


	//初始化UI
	this.initUI = function () {

		//拖动验证
		$('#dragValidate').dragValidate({
			finish:function(){
				$("#register-btn-getValidateCode").removeClass("disabled");
				$("#register-submit").removeClass("disabled");
			}
		});
	}

}


/*
 //body load
 --------------------------------------------------------------------*/
var esteel_register;
$(document).ready(function (e) {
	esteel_register = new JBSFrame_register();
	//初始化UI
	esteel_register.initUI();

});

//确认注册协议
function confirmRegisterRule(ckb){
	if(!ckb.checked){
		$("#register-submit").addClass("disabled");
	}else{
		$("#register-submit").removeClass("disabled");
	}
}