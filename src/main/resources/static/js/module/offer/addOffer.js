/**
 * Created by wzj on 2017/10/31.
 *	发布报盘
 */


function JBSFrame_addOffer() {
	JBSFrame.call(this);

	this.sidebar = null;//侧栏菜单
	this.select_ItemName = null;//品名

	//初始化UI
	this.initUI = function () {

		//主菜单栏
		//当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(ComponentSidebar,{focusNode:{name:"addOffer",text:"发布报盘"}}), document.getElementById("component-sidebar"));

		//品名下拉
		//this.select_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{}), document.getElementById("component-selectBox-ItemName"));
	}

	//初始化路由
	this.initRouter = function () {

		//页面路由，在页面设置
		var offerRoutes = {}
		$("#router-linkNode >a").each(function(idx,elem){
			var rr = elem.getAttribute("linkNode")
			offerRoutes[rr] = self.loadRouter
		})
		var router = Router(offerRoutes);
		router.configure({
			on: self.selectTypeTab	//切换路由后设置高亮标签
		});
		router.init('/'+$("#router-linkNode >a.selected").attr("linkNode"));//初始化页面
	}

	this.loadRouter = function(){
		var path = window.location.hash.slice(2);
		$("#router-pageCotainer").load('/view/offer/add/'+path+".html");	//加载静态文件
	}

	//切换路由后设置高亮标签
	this.selectTypeTab = function(){
		var path = window.location.hash.slice(2);
		$("#router-linkNode >a").removeClass("selected");
		$("#router-linkNode >a[linkNode='"+path+"']").addClass("selected")

	}


	var self = this;
}

/*
 //body load
 --------------------------------------------------------------------*/
var esteel_addOffer;
$(document).ready(function (e) {
	esteel_addOffer = new JBSFrame_addOffer();
	//初始化UI
	esteel_addOffer.initUI();
	//初始化路由
	esteel_addOffer.initRouter();
});

