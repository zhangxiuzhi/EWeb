/**
 * 会员中心-企业认证
 * Created by wzj on 2017/11/30.
 */

function JBSFrame_member_approve() {

	JBSFrame.call(this);


	//初始化UI
	this.initUI = function () {
		//主菜单栏
		//当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(ComponentMemberSidebar,{focusNode:{name:"userInfo",text:"个人资料"}}), document.getElementById("component-sidebar"));


	}

	//渲染表单元素
	this.renderFormElement = function(){
		//注册地址
		var $regaddress_1 = $("#component-regAddress-1");
		if($regaddress_1.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:[{value:'1',text:"1"},{value:'2',text:"2"}],
				inputName:$regaddress_1.attr("inputName"),
				validetta:$regaddress_1.data("validetta")
			}), $regaddress_1[0]);
		}
		var $regaddress_2 = $("#component-regAddress-2");
		if($regaddress_2.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:[{value:'1',text:"1"},{value:'2',text:"2"}],
				inputName:$regaddress_2.attr("inputName"),
				validetta:$regaddress_2.data("validetta")
			}), $regaddress_2[0]);
		}
		var $regaddress_3 = $("#component-regAddress-3");
		if($regaddress_3.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:[{value:'1',text:"1"},{value:'2',text:"2"}],
				inputName:$regaddress_3.attr("inputName"),
				validetta:$regaddress_3.data("validetta")
			}), $regaddress_3[0]);
		}
		//通信地址
		var $contactAddress_1 = $("#component-contactAddress-1");
		if($contactAddress_1.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:[{value:'1',text:"1"},{value:'2',text:"2"}],
				inputName:$contactAddress_1.attr("inputName"),
				validetta:$contactAddress_1.data("validetta")
			}), $contactAddress_1[0]);
		}
		var $contactAddress_2 = $("#component-contactAddress-2");
		if($contactAddress_2.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:[{value:'1',text:"1"},{value:'2',text:"2"}],
				inputName:$contactAddress_2.attr("inputName"),
				validetta:$contactAddress_2.data("validetta")
			}), $contactAddress_2[0]);
		}
		var $contactAddress_3 = $("#component-contactAddress-3");
		if($contactAddress_3.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:[{value:'1',text:"1"},{value:'2',text:"2"}],
				inputName:$contactAddress_3.attr("inputName"),
				validetta:$contactAddress_3.data("validetta")
			}), $contactAddress_3[0]);
		}
		//是否3证合一
		var $certificate = $("#component-certificate");
		if($certificate.length>0) {
			this.toggle_Split = ReactDOM.render(React.createElement(ComponentToggle, {
				inputName: $certificate.attr("inputName"),
				//onChange:showQDL//显示起订量
			}), $certificate[0]);
		}
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
		console.log(offerRoutes)
		var router = Router(offerRoutes);
		router.configure({
			on: self.selectTypeTab	//切换路由后设置高亮标签
		});
		router.init('/'+$("#router-linkNode >a.selected").attr("linkNode"));//初始化页面
	}

	this.loadRouter = function(){
		var path = window.location.hash.slice(2);
		$("#router-pageCotainer").load('/view/member/'+path+".html", function(){
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
var esteel_member_approve;
$(document).ready(function (e) {
	esteel_member_approve = new JBSFrame_member_approve();
	//初始化UI
	esteel_member_approve.initUI();
	//初始化路由
	esteel_member_approve.initRouter();
});

