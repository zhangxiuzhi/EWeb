/**
 * Created by wzj on 2017/10/31.
 *	我的报盘
 */


function JBSFrame_myOffer() {
	JBSFrame.call(this);

	this.sidebar = null;//侧栏菜单

	//初始化UI
	this.initUI = function () {

		this.sidebar = ReactDOM.render(React.createElement(ComponentSidebar,{focusNode:{name:"addOffer",text:"报盘记录"}}), document.getElementById("component-sidebar"));
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
var esteel_myOffer;
$(document).ready(function (e) {
	esteel_myOffer = new JBSFrame_myOffer();
	//初始化UI
	esteel_myOffer.initUI();
	//初始化路由
	esteel_myOffer.initRouter();
});

