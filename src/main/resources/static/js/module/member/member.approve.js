/**
 * 会员中心-企业认证 Created by wzj on 2017/11/30.
 */

function JBSFrame_member_approve() {

	JBSFrame.call(this);

	// 初始化UI
	this.initUI = function() {
		// 主菜单栏
		// 当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(
				ComponentMemberSidebar, {
					focusNode : {
						name : "userInfo",
						text : "个人资料"
					}
				}), document.getElementById("component-sidebar"));

	}

	// 渲染表单元素
	this.renderFormElement = function() {
		// 注册地址，省份
		var $regaddress_1 = $("#component-regAddress-1");
		if ($regaddress_1.length > 0) {
			this.selectBox_provinces = ReactDOM.render(React.createElement(
					ComponentSelectBox, {
						data : JSON.parse($("#provincesJson").html()),
						inputName : $regaddress_1.attr("inputName"),
						//inputValue:JSON.parse($("#provincesJson").html())[0].value,
						validetta : $regaddress_1.data("validetta"),
						onChange : findCity
					}), $regaddress_1[0]);
		}
		// 注册，城市
		var $regaddress_2 = $("#component-regAddress-2");
		if ($regaddress_2.length > 0) {
			this.selectBox_city = ReactDOM.render(React.createElement(
					ComponentSelectBox, {
						data : [],
						inputName : $regaddress_2.attr("inputName"),
						validetta : $regaddress_2.data("validetta"),
						onChange : findDistrict
					}), $regaddress_2[0]);
		}
		// 注册，区县
		var $regaddress_3 = $("#component-regAddress-3");
		if ($regaddress_3.length > 0) {
			this.selectBox_district = ReactDOM.render(React.createElement(
					ComponentSelectBox, {
						data : [],
						inputName : $regaddress_3.attr("inputName"),
						validetta : $regaddress_3.data("validetta")
					}), $regaddress_3[0]);
		}
		// 通信地址
		var $contactAddress_1 = $("#component-contactAddress-1");
		if ($contactAddress_1.length > 0) {
			this.selectBox_ConProvince = ReactDOM.render(React.createElement(
					ComponentSelectBox, {
						data : JSON.parse($("#provincesJson").html()),
						inputName : $contactAddress_1.attr("inputName"),
						validetta : $contactAddress_1.data("validetta"),
						onChange : findCity_contact
					}), $contactAddress_1[0]);
		}
		var $contactAddress_2 = $("#component-contactAddress-2");
		if ($contactAddress_2.length > 0) {
			this.selectBox_ConCity = ReactDOM.render(React.createElement(
					ComponentSelectBox, {
						data : [],
						inputName : $contactAddress_2.attr("inputName"),
						validetta : $contactAddress_2.data("validetta"),
						onChange : findDistrict_contact
					}), $contactAddress_2[0]);
		}
		var $contactAddress_3 = $("#component-contactAddress-3");
		if ($contactAddress_3.length > 0) {
			this.selectBox_ConDistirct = ReactDOM.render(React.createElement(
					ComponentSelectBox, {
						data : [],
						inputName : $contactAddress_3.attr("inputName"),
						validetta : $contactAddress_3.data("validetta")
					}), $contactAddress_3[0]);
		}
		//证件正反面
		/*var $uploadImage_1 = $("#component-uploadImage-id1");
		if ($uploadImage_1.length > 0) {
			this.selectBox_ConDistirct = ReactDOM.render(React.createElement(
				ComponentUploadImage, {
					label:"上传人像面",
					inputId: $uploadImage_1.attr("inputId"),
					inputName : $uploadImage_1.attr("inputName"),
					ajax:true
				}), $uploadImage_1[0]);
		}
		var $uploadImage_2 = $("#component-uploadImage-id2");
		if ($uploadImage_2.length > 0) {
			this.selectBox_ConDistirct = ReactDOM.render(React.createElement(
				ComponentUploadImage, {
					label:"上传国徽面",
					inputName : $uploadImage_2.attr("inputName")
				}), $uploadImage_2[0]);
		}*/

		// 是否3证合一
		var $certificate = $("#component-certificate");
		if ($certificate.length > 0) {
			this.toggle_Split = ReactDOM.render(React.createElement(
					ComponentToggle, {
						inputName : $certificate.attr("inputName"),
					// onChange:showQDL//显示起订量
					}), $certificate[0]);
		}
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
					self.renderFormElement();// 渲染表单元素
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
var esteel_member_approve;
$(document).ready(function(e) {
	esteel_member_approve = new JBSFrame_member_approve();
	// 初始化UI
	esteel_member_approve.initUI();
	// 初始化路由
	esteel_member_approve.initRouter();
});

// 文件上传
function upload(elem) {
	var fileId =elem.id; 
	$.ajaxFileUpload({
		url: '/user/uploadFile',
		secureuri: false,
		fileElementId: elem.id,// file标签的id
		dataType: 'json',
		beforeSend: function (xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function (result) {
			//返回文件id
			if(result.data!=null){
				//图片回写
				if(fileId =="file1"||fileId =="file2"||fileId =="file3"||fileId =="file4"){
					reloadUploadImage(fileId,result);
				}
				//保存数据库的字符串
				var saveStr = result.data[0]+result.data[1];
				//赋值
				$("#"+fileId+"-text").val(saveStr);
			}else{
				alert(result.msg);
			}
		}
	});
}
//图片回写
function reloadUploadImage(fileId,result){
	$("#"+fileId+"-text").parent(".img-uploadPhoto").addClass("hasImage");
	$("#"+fileId+"-text").siblings("img").remove();
	//图片下载的地址 user/export/T1saYTByCT1R4cSCrK/.png/html
	var url = "/user/export/"+result.data[0]+"/"+result.data[1]+"/html;";
	var $img = $("<img/>").attr("src",url);
	$("#"+fileId+"-text").after($img);
}
// 表单验证
function chenckForm() {
	var status = true;
	
	var name = $("#companyName").val(); //企业全称
	var regaddr = $("#regAddress").val();//注册地址
	var conAddr = $("#conAddress").val(); //通信地址
	/*  注册地址  */
	var r1 = $("select[name='provincesr']").val();//注册地址
	var r2 = $("select[name='cityr']").val();//注册地址
	var r3 = $("select[name='dictristor']").val();//注册地址
	/*  联系地址  */
	var c1 = $("select[name='provincesc']").val();//通信地址
	var c2 = $("select[name='cityc']").val();//通信地址
	var c3 = $("select[name='dictristorc']").val();//通信地址
	
	var agent = $("#agentName").val();//法人姓名
	var cardid = $("#agentCardId").val(); //代理人身份证号
	var file1 = $("#file1").val(); //代理人身份证正面
	var file2 = $("#file2").val(); //代理人身份证号反面
	var legal = $("#legalName").val(); //法人姓名
	
	
	if (name == ""||name == null) {
		esteel_member_approve.insertErrorBubble("companyName", "企业全称不能为空");
		status = false;
	}
	if (regaddr == null || regaddr == "") {
		esteel_member_approve.insertErrorBubble("regAddress", "注册地址不能为空");
		status = false;
	}
	if (r1 == "" && r2 == "") {
		esteel_member_approve.insertErrorBubble("shshixianr", "请选择注册地址");
		status = false;
	}
	if (c1 == "" && c2 == "") {
		esteel_member_approve.insertErrorBubble("shshixianc", "请选择联系地址");
		status = false;
	}
	if (conAddr == null || conAddr == "") {
		esteel_member_approve.insertErrorBubble("regAddress", "通信地址不能为空");
		status = false;
	}
	if (agent == null || agent == "") {
		esteel_member_approve.insertErrorBubble("agentName", "代理人姓名不能为空");
		status = false;
	}
	//验证身份证号和姓名
	if (cardid == null || cardid == "") {
		esteel_member_approve.insertErrorBubble("agentCardId", "代理人身份证号码不能为空");
		status = false;
	}
	if (file1 == "" || file2 == "") {
		alert("请上传身份证附件正反面");
		status = false;
	}
	if (legal == null || legal == "") {
		esteel_member_approve.insertErrorBubble("legalName", "法人姓名不能为空");
		status = false;
	}
	
	//验证不通过中断执行
	if(!status){
		return;
	}
	
	//表单序列化
	var datas = $("#form-userInfo").serialize();
	//数据提交
	esteel_member_approve.ajaxRequest({
		url : "/company/attest",
		data : datas
	}, function(data,msg) {
		if(data!=null){
			//跳转成功页面
			window.location.href = "/member/userInfo";
		}else{
			alert(msg);
		}
	})
}




// 获取所有的城市下拉框
function findCity(node) {
	esteel_member_approve.ajaxRequest({
		url : "/company/findCity",
		data : {
			provinceId : node.value
		}
	}, function(_data) {
		esteel_member_approve.selectBox_city.setState({
			data : JSON.parse(_data[0])
		});
	})
}

// 获取所有的区县下拉框
function findDistrict(node) {
	esteel_member_approve.ajaxRequest({
		url : "/company/findDistrict",
		data : {
			cityId : node.value
		}
	}, function(_data) {
		esteel_member_approve.selectBox_district.setState({
			data : JSON.parse(_data[0])
		});
	})
}

// 获取所有的城市下拉框
function findCity_contact(node) {
	esteel_member_approve.ajaxRequest({
		url : "/company/findCity",
		data : {
			provinceId : node.value
		}
	}, function(_data) {
		esteel_member_approve.selectBox_ConCity.setState({
			data : JSON.parse(_data[0])
		});
	})
}

// 获取所有的区县下拉框
function findDistrict_contact(node) {
	esteel_member_approve.ajaxRequest({
		url : "/company/findDistrict",
		data : {
			cityId : node.value
		}
	}, function(_data) {
		esteel_member_approve.selectBox_ConDistirct.setState({
			data : JSON.parse(_data[0])
		});
	})
}