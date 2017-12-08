/**
 * 会员中心-子账号管理
 * Created by wzj on 2017/12/8.
 */

function JBSFrame_member_subAccount() {

	JBSFrame.call(this);


	//初始化UI
	this.initUI = function () {
		//主菜单栏
		//当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(ComponentMemberSidebar,{focusNode:{name:"subAccount",text:"子账号管理"}}), document.getElementById("component-sidebar"));


	}

	//渲染表单元素
	this.renderFormElement = function(){

		//表格
		this.table = ReactDOM.render(React.createElement(SubAccountList), document.getElementById("component-table-subAccount"));

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
var esteel_member_account;
$(document).ready(function (e) {
	esteel_member_account = new JBSFrame_member_subAccount();
	//初始化UI
	esteel_member_account.initUI();
	//初始化路由
	esteel_member_account.initRouter();
});

//添加子账号
function add_subAccount(){
	$("#subaccoumt-add-modal").modal("hide");
}

//更新子账号
function update_subAccount(){
	$("#subaccoumt-edit-modal").modal("hide");
}
