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
	$.ajaxFileUpload({
		url: '/user/uploadFile',
		secureuri: false,
		fileElementId: elem.id,// file标签的id
		dataType: 'json',
		beforeSend: function (xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function (data) {
			console.log(data)
			alert(123);
		}
	});
}
// 表单验证
function chenckForm() {
	// alert("123456");
	var name = $("#companyName").val();
	var add = $("#regAddress").val();
	var agent = $("#agentName").val();
	var cardid = $("#agentCardId").val();
	var file1 = $("#file1").val();
	var file2 = $("#file2").val();
	var legal = $("#legalName").val();
	if (name = "") {
		alert(name);
		return false;
	}
	if (add == null && add == "") {
		esteel_member_approve.insertErrorBubble("regAddress", "注册地址不能为空");
	}
	if (agent == null && agent == "") {
		esteel_member_approve.insertErrorBubble("agentName", "代理人姓名不能为空");
	}
	if (cardid == null && cardid == "") {
		esteel_member_approve.insertErrorBubble("agentCardId", "代理人身份证号码不能");
	}
	if (file1 == null && file1 == "") {
		esteel_member_approve.insertErrorBubble("file1", "代理人身份附件不能为空");
	}
	if (file2 == null && file2 == "") {
		esteel_member_approve.insertErrorBubble("file2", "代理人身份附件不能为空");
	}
	if (legal == null && legal == "") {
		esteel_member_approve.insertErrorBubble("legalName", "请先输入手机号");
	}

	return false;
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

function submitForm() {
	
	var vcsrf = $("#_csrf").val();
	
}