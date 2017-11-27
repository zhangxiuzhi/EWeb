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
		//白名单
		var $TradeCustomer = $("#component-TradeCustomer");
		var TradeCustomer_data = {
			orgList:[],
			curList:[]
		}
		this.select_TradeCustomer = ReactDOM.render(React.createElement(ComponentEsteelMultiSelect,{inputName:$TradeCustomer.attr("inputName"),data:TradeCustomer_data}), $TradeCustomer[0]);

		//品名下拉
		var $ItemName = $("#component-selectBox-ItemName");
		if($ItemName.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{inputName:$ItemName.attr("inputName")}), $ItemName[0]);
		}
		//港口
		var $Port = $("#component-selectBox-Port")
		if($Port.length>0) {
			this.selectBox_Port = ReactDOM.render(React.createElement(ComponentSelectBox, {inputName: $Port.attr("inputName")}), $Port[0]);
		}
		//指标类型
		var $kpiType = $("#component-radioBoxGroup-kpiType");
		if($kpiType.length>0) {
			this.radioBox_kpiType = ReactDOM.render(React.createElement(ComponentRadioBox, {
				data: data_kpiType,
				value: "dx",
				className: "TagStyle offerKpi"
			}), $kpiType[0]);
		}
		//是否拆分
		var $Split = $("#component-toggle-split");
		if($Split.length>0) {
			this.toggle_Split = ReactDOM.render(React.createElement(ComponentToggle, {inputName: $Split.attr("inputName")}), $Split[0]);
		}
		//匿名
		var $Anonym = $("#component-toggle-anonym");
		if($Anonym.length>0) {
			this.toggle_anonym = ReactDOM.render(React.createElement(ComponentToggle, {inputName: $Anonym.attr("inputName")}), $Anonym[0]);
		}
		//一船俩货
		var $1ship2goods = $("#component-radioBoxGroup-1ship2goods");
		if($1ship2goods.length>0){
			this.toggle_1ship2goods = ReactDOM.render(React.createElement(ComponentToggle,{inputName:$1ship2goods.attr("inputName")}), $1ship2goods[0]);
		}
		//商品1品名下拉
		var $ItemName1 = $("#component-selectBox-ItemName-1");
		if($ItemName1.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{inputName:$ItemName1.attr("inputName")}), $ItemName1[0]);
		}
		//商品2品名下拉
		var $ItemName2 = $("#component-selectBox-ItemName-2");
		if($ItemName2.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{inputName:$ItemName2.attr("inputName")}), $ItemName2[0]);
		}
		//报税区
		var $bondedAreas = $("#component-radioBoxGroup-bondedAreas");
		if($bondedAreas.length>0){
			this.toggle_bondedAreas = ReactDOM.render(React.createElement(ComponentToggle,{inputName:$bondedAreas.attr("inputName")}), $bondedAreas[0]);
		}

		//加载品名
		load_tbPricingCommodityList();
		//加载港口
		load_portList();
	}


	/*---------------------------------------------------------------------------------------------------------------------------*/

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


	var self = this

	/*---------------------------------------------------------------------------------------------------------------------------*/
	//加载品名
	function load_tbPricingCommodityList(){

	}

	//加载港口列表
	function load_portList() {
		esteel_addOffer.ajaxRequest({
			url: "pricing/pricingPort"
		}, function (data, msg) {
			//港口
			var $shipmentportKey = $("#component-shipmentportKey");
			var select_shipmentport = ReactDOM.render(React.createElement(InputGroupSelect, {
				data: data,
				multiple: "false,",
				class: "form-group",
				formLabel: $shipmentportKey.attr("label"),
				formName: $shipmentportKey.attr("name")
			}), $shipmentportKey[0]);

			//交易规则-交货方式港口
			$("#offer-rules-port1").autocomplete({
				source: function (request, response) {
					var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
					response($.grep(data, function (item) {
						return matcher.test(item.text) ||
							matcher.test(item.value);
					}));
				},
				select: function (event, ui) {
					$("#offer-rules-port1").val(ui.item.text);
					$("input[name='checkModePort']").val(ui.item.value);
					return false;
				}
			}).data("ui-autocomplete")._renderItem = function (ul, item) {
				return $("<li>")
				.data("item.autocomplete", item)
				.append($("<a></a>").text(item.text))
				.appendTo(ul);
			}


			//交易规则-交货数量标准港口
			$("#offer-rules-port2").autocomplete({
				source: function (request, response) {
					var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
					response($.grep(data, function (item) {
						return matcher.test(item.text) ||
							matcher.test(item.value);
					}));
				},
				select: function (event, ui) {
					$("#offer-rules-port2").val(ui.item.text);
					$("input[name='checkNumPort']").val(ui.item.value);
					return false;
				},
				search: function (event, ui) {
					console.log("search")
				}
			}).data("ui-autocomplete")._renderItem = function (ul, item) {
				return $("<li>")
				.data("item.autocomplete", item)
				.append($("<a></a>").text(item.text))
				.appendTo(ul);
			};

		})
	}
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



