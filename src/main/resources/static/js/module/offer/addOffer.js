/**
 * Created by wzj on 2017/10/31.
 *	发布报盘
 */

//指标类型
var data_kpiType = [
	{id: "kpiType-dx", text: "典型值", value: "dx",name:"kpiType"},
	{id: "kpiType-zg", text: "装港值", value: "zg",name:"kpiType"},
	{id: "kpiType-xg", text: "卸港值", value: "xg",name:"kpiType"},
	{id: "kpiType-bz", text: "保证值", value: "bz",name:"kpiType"}
]



function JBSFrame_addOffer() {
	JBSFrame.call(this);

	this.sidebar = null;//侧栏菜单

	//初始化UI
	this.initUI = function () {

		//主菜单栏
		//当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(ComponentSidebar,{focusNode:{name:"addOffer",text:"发布报盘"}}), document.getElementById("component-sidebar"));

	}

	//渲染表单元素
	this.renderFormElement = function(){
		//品名下拉
		var $ItemName = $("#component-selectBox-ItemName");
		this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{inputName:$ItemName.attr("inputName")}), $ItemName[0]);
		//港口
		var $Port = $("#component-selectBox-Port")
		this.selectBox_Port = ReactDOM.render(React.createElement(ComponentSelectBox,{inputName:$Port.attr("inputName")}), $Port[0]);
		//指标类型
		var $kpiType = $("#component-radioBoxGroup-kpiType");
		this.radioBox_kpiType = ReactDOM.render(React.createElement(ComponentRadioBox,{data:data_kpiType, value:"dx", className:"TagStyle offerKpi"}), $kpiType[0]);
		//是否拆分
		var $Split = $("#component-toggle-split");
		this.toggle_Split = ReactDOM.render(React.createElement(ComponentToggle,{inputName:$Split.attr("inputName")}), $Split[0]);
		//匿名
		var $Anonym = $("#component-toggle-anonym");
		this.toggle_anonym = ReactDOM.render(React.createElement(ComponentToggle,{inputName:$Anonym.attr("inputName")}), $Anonym[0]);
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
		$("#router-pageCotainer").load('/view/offer/add/'+path+".html", function(){
			console.log(document.getElementById("component-selectBox-ItemName"))
			self.renderFormElement();//渲染表单元素
		});	//加载静态文件
	}

	//切换路由后设置高亮标签
	this.selectTypeTab = function(){
		var path = window.location.hash.slice(2);
		$("#router-linkNode >a").removeClass("selected");
		$("#router-linkNode >a[linkNode='"+path+"']").addClass("selected");
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

//保存报盘
function save_offer(){
	esteel_addOffer.confirm(null,"该报盘将作为草稿保存到我的报盘记录",function(){

	});
}

//提交报盘
function submit_offer(){
	esteel_addOffer.confirm(null,"确定要发布吗",function(){

	});
}