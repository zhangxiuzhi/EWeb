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
		this.select_TradeCustomer = ReactDOM.render(React.createElement(ComponentEsteelMultiSelect,{
			inputName:$TradeCustomer.attr("inputName"),
			data:TradeCustomer_data
		}), $TradeCustomer[0]);

		//品名下拉
		var $ItemName = $("#component-selectBox-ItemName");
		if($ItemName.length>0){
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:JSON.parse($("#ironCommodityJson").html()),
				inputName:$ItemName.attr("inputName"),
				validetta:$ItemName.data("validetta"),
				onChange:changeIronAttributeLink
			}), $ItemName[0]);
		}
		//港口
		var $Port = $("#component-selectBox-Port")
		if($Port.length>0) {
			this.selectBox_Port = ReactDOM.render(React.createElement(ComponentSelectBox, {
				data:JSON.parse($("#portJson").html()),
				inputName: $Port.attr("inputName"),
				validetta:$Port.data("validetta")
			}), $Port[0]);
		}
		//指标类型
		var $kpiType = $("#component-radioBoxGroup-kpiType");
		if($kpiType.length>0) {
			this.radioBox_kpiType = ReactDOM.render(React.createElement(ComponentRadioBox, {
				data: JSON.parse($("#indicatorTypeJson").html()),
				value: "26",
				className: "TagStyle offerKpi",
				name:$kpiType.attr("inputName")
			}), $kpiType[0]);
		}
		ironAttributeLinkJson

		//是否拆分
		var $Split = $("#component-toggle-split");
		if($Split.length>0) {
			this.toggle_Split = ReactDOM.render(React.createElement(ComponentToggle, {
				inputName: $Split.attr("inputName"),
				onChange:showQDL//显示起订量
			}), $Split[0]);
		}
		//匿名
		var $Anonym = $("#component-toggle-anonym");
		if($Anonym.length>0) {
			this.toggle_anonym = ReactDOM.render(React.createElement(ComponentToggle, {inputName: $Anonym.attr("inputName")}), $Anonym[0]);
		}
		//一船俩货
		var $1ship2goods = $("#component-radioBoxGroup-1ship2goods");
		if($1ship2goods.length>0){
			this.toggle_1ship2goods = ReactDOM.render(React.createElement(ComponentToggle,{
				inputName:$1ship2goods.attr("inputName"),
				onChange:show2goods	//显示2个商品
			}), $1ship2goods[0]);
		}
		//商品1品名下拉
		var $ItemName1 = $("#component-selectBox-ItemName-1");
		if($ItemName1.length>0){
			this.selectBox_ItemName1 = ReactDOM.render(React.createElement(ComponentSelectBox,{
				inputName:$ItemName1.attr("inputName"),
				data:JSON.parse($("#ironCommodityJson").html()),
				onChange:changeIronAttributeLink_1
			}), $ItemName1[0]);
		}
		//商品2品名下拉
		var $ItemName2 = $("#component-selectBox-ItemName-2");
		if($ItemName2.length>0){
			this.selectBox_ItemName2 = ReactDOM.render(React.createElement(ComponentSelectBox,{
				inputName:$ItemName2.attr("inputName"),
				data:JSON.parse($("#ironCommodityJson").html()),
				onChange:changeIronAttributeLink_2
			}), $ItemName2[0]);
		}
		//报税区
		var $bondedAreas = $("#component-radioBoxGroup-bondedAreas");
		if($bondedAreas.length>0){
			this.toggle_bondedAreas = ReactDOM.render(React.createElement(ComponentToggle,{inputName:$bondedAreas.attr("inputName")}), $bondedAreas[0]);
		}


		this.renderDatetimepicker();
		this.renderNumberMask();
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

}


//验证报盘商品信息
function validateOfferInfo(){
	var valid = $("#form-offer [data-validetta],#form-offer select[data-validetta]").length;
	$("#form-offer .form-control[data-validetta],#form-offer select[data-validetta]").each(function(index,element){

		if(element.value == ""){	//
			insertErrorBubble($(element));
		}else{
			var $element = $(element);
			//报盘数量100倍数
			if($element.attr("id") == "offerNum"){
				var value = $element.val();
				var pos,H;
				if(value !="" && Number(value)%100>0) {
					esteel_addOffer.validatePricingNumber = false;   //验证点数量为100的倍数失败
					insertErrorBubble($(element),"数量必须为100的倍数");
				}else{
					esteel_addOffer.validatePricingNumber = true;
					$element.next(".validetta-bubble").remove();
					valid -=1;
				}
			}else{
				valid -=1;
			}
		}
	});

	//验证报盘数量是否为100倍数
	if(esteel_addOffer.validatePricingNumber==false){
		valid +=1;
	}
	//console.log("验证商品",valid)
	return valid == 0 ?true : false;
}

function insertErrorBubble($element,errorText){
	$element.next(".validetta-bubble").remove();

	var pos, W = 0, H = 0;
	var $bubble = $("<div class='validetta-bubble validetta-bubble--bottom'></div>");
	$bubble.html("此项为必填项");

	//组件下拉
	if($element.parents(".react-selectbox").length>0) {
		//品名
		if($element[0].name =="ItemName"){
			esteel_addOffer.selectBox_ItemName.addValidettaBubble();
		}
		//港口
		if($element[0].name =="Port"){
			esteel_addOffer.selectBox_Port.addValidettaBubble();
		}
	}
	else if($element.hasClass("uploadFile")) {
		pos = $element.parent(".btn.btn-file").position();
		H = $element.parent(".btn.btn-file")[0].offsetHeight;
		$bubble.css({
			top:pos.top + H + 0,
			left:pos.left + W + 15
		});
		$element.parent(".btn.btn-file").after($bubble);
	}else{
		pos = $element.position();
		H = $element[0].offsetHeight;
		$bubble.css({
			top:pos.top + H + 0,
			left:pos.left + W + 15
		});
		$element.after($bubble);
	}
	$element.on("change",function(e){
		if(e.target.value!=""){
			$element.next(".validetta-bubble").remove();
			$element.parent(".btn.btn-file").next(".validetta-bubble").remove();
		}
	});
	if($element.hasClass("datetimepicker")){
		$element.on("dp.change", function (evt) {
			$(evt.currentTarget).parent(".input-group").removeClass("validetta-error");
			$(evt.currentTarget).siblings(".validetta-bubble").remove();
		});
	}
	if($element.attr("id") == "offerNum"){
		$bubble.html(errorText);
		$element.after($bubble);
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
/*	if(validateOfferInfo()){
		esteel_addOffer.confirm(null,"该报盘将作为草稿保存到我的报盘记录",function(){
		});
		
		$("#form-offer").submit();
	}*/
	$("#form-offer")[0].submit();
}

//提交报盘
function submit_offer(){
	esteel_addOffer.confirm(null,"确定要发布吗",function(){

	});
}

//品名切换改变指标值
function changeIronAttributeLink(node){
	//典型值
	if($("input[name='indicatorTypeName']").val() == "26"){
		changeIronCommodityIndicator(node,'');
	}
}
//期货商品1联动指标
function changeIronAttributeLink_1(node){
	changeIronCommodityIndicator(node,'1');
}
//期货商品2联动指标
function changeIronAttributeLink_2(node) {
	changeIronCommodityIndicator(node,'2');
}
//品名联动
function changeIronCommodityIndicator(node,index){
	var ironAttr = JSON.parse($("#ironAttributeLinkJson").html());
	for(var attr in ironAttr){
		if(attr == node.label){
			var iron = ironAttr[attr];
			for(var i=0;i<iron.length;i++){
				$("#indicator"+index+"-"+iron[i].text).val(iron[i].value);
			}
		}
	}
}



//显示隐藏起订量
function showQDL(checked){
	if(checked){
		$("#offer-qdl-box").show();
	}else{
		$("#offer-qdl-box").hide();
	}
}

//显示两个商品
function show2goods(checked){
	if(checked){
		$("#offer-goods-2").show();
	}else{
		$("#offer-goods-2").hide();
	}
}

//显示浮动价
function showFloatPrice(evt){
	if(evt.selectedOptions[0].value == "float"){
		$("#offer-floatPrice-desc").show();
		$("#offer-floatPrice-desc").attr("data-validetta","required");
	}else{
		$("#offer-floatPrice-desc").hide();
		$("#offer-floatPrice-desc").removeAttr("data-validetta");
	}
}