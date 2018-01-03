/**
 * Created by wzj on 2017/10/31.
 *    发布报盘
 */
function JBSFrame_addOffer() {
    JBSFrame.call(this);
 
    this.sidebar = null;//侧栏菜单
 
    //初始化UI
    this.initUI = function () {
 
        //主菜单栏
        //当前选中发布报盘
        this.sidebar = ReactDOM.render(React.createElement(ComponentIronSidebar,{focusNode:{name:"addOffer",text:"发布报盘"}}), document.getElementById("component-sidebar"));
 
    }
 
    //渲染表单元素
    this.renderFormElement = function(){
        //白名单
        var $TradeCustomer = $("#component-TradeCustomer");
        var TradeCustomer_data = {
            orgList:JSON.parse($("#companyWhitelistJson").html()),
            curList:[]
        }
        if($TradeCustomer.length>0){
            this.select_TradeCustomer = ReactDOM.render(React.createElement(ComponentEsteelMultiSelect,{
                inputName:$TradeCustomer.attr("inputName"),
                data:TradeCustomer_data
            }), $TradeCustomer[0]);
        }

        //品名下拉
        var $ItemName = $("#component-selectBox-ItemName");
        if($ItemName.length>0){
            this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
                data:JSON.parse($("#ironCommodityJson").html()),
                inputName:$ItemName.attr("inputName"),
                inputValue:$ItemName.attr("inputValue"),
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
                inputValue:$Port.attr("inputValue"),
                validetta:$Port.data("validetta")
            }), $Port[0]);
        }
      //点价交易港口
        var $Port = $("#component-selectBox-Port-Pricing")
        if($Port.length>0) {
            this.selectBox_Port = ReactDOM.render(React.createElement(ComponentSelectBox, {
                data:JSON.parse($("#pricingPortJson").html()),
                inputName: $Port.attr("inputName"),
                inputValue:$Port.attr("inputValue"),
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
                inputName:$kpiType.attr("inputName"),
                onChange:changeIndicatorValue //指标类型选择，改变指标值
            }), $kpiType[0]);
        }

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
                onChange:show2goods    //显示2个商品
            }), $1ship2goods[0]);
        }
        //商品1品名下拉
        var $ItemName1 = $("#component-selectBox-ItemName-1");
        if($ItemName1.length>0){
            this.selectBox_ItemName1 = ReactDOM.render(React.createElement(ComponentSelectBox,{
            	data:JSON.parse($("#ironCommodityJson").html()),
                inputName:$ItemName1.attr("inputName"),
                inputValue:$ItemName1.attr("inputValue"),
                validetta:$ItemName1.data("validetta"),
                onChange:changeIronAttributeLink_1 //期货商品1联动指标
            }), $ItemName1[0]);
        }
        //商品1指标类型
        var $kpiType1 = $("#component-radioBoxGroup-kpiType-1");
        if($kpiType1.length>0) {
            this.radioBox_kpiType1 = ReactDOM.render(React.createElement(ComponentRadioBox, {
                data: JSON.parse($("#indicatorTypeJson").html()),
                value: "26",
                className: "TagStyle offerKpi",
                inputName:$kpiType1.attr("inputName"),
                onChange:changeIndicatorValue1 //指标类型选择，改变指标值
            }), $kpiType1[0]);
        }
        //商品2品名下拉
        var $ItemName2 = $("#component-selectBox-ItemName-2");
        if($ItemName2.length>0){
            this.selectBox_ItemName2 = ReactDOM.render(React.createElement(ComponentSelectBox,{
            	data:JSON.parse($("#ironCommodityJson").html()),
                inputName:$ItemName2.attr("inputName"),
                inputValue:$ItemName2.attr("inputValue"),
                validetta:$ItemName2.data("validetta"),
                onChange:changeIronAttributeLink_2 //期货商品2联动指标
            }), $ItemName2[0]);
        }
      //商品2指标类型
        var $kpiType2 = $("#component-radioBoxGroup-kpiType-2");
        if($kpiType2.length>0) {
            this.radioBox_kpiType2 = ReactDOM.render(React.createElement(ComponentRadioBox, {
                data: JSON.parse($("#indicatorTypeJson").html()),
                value: "26",
                className: "TagStyle offerKpi",
                inputName:$kpiType2.attr("inputName"),
                onChange:changeIndicatorValue2 //指标类型选择，改变指标值
            }), $kpiType2[0]);
        }
        //装货港
        var loadingPort = JSON.parse($("#loadingPortJson").html());
        for(var i=0;i<loadingPort.length;i++){
            $opt = $("<option></option>").text(loadingPort[i].text).val(loadingPort[i].value);
            $("#select-loadingPort").append($opt);
        }

        //目的港
        var dischargePort = JSON.parse($("#portJson").html());
        for(var i=0;i<dischargePort.length;i++){
            $opt = $("<option></option>").text(dischargePort[i].text).val(dischargePort[i].value);
            $("#select-dischargePort").append($opt);
        }

        //报税区
        var $bondedAreas = $("#component-radioBoxGroup-bondedAreas");
        if($bondedAreas.length>0){
            this.toggle_bondedAreas = ReactDOM.render(React.createElement(ComponentToggle,{
                inputName:$bondedAreas.attr("inputName"),
                onChange:changeBondedArea //指标类型选择，改变指标值
            }), $bondedAreas[0]);
        }

        //报税区港口
        var bondedAreaPort = JSON.parse($("#bondedAreaPortJson").html());
        for(var i=0;i<bondedAreaPort.length;i++){
            $opt = $("<option></option>").text(bondedAreaPort[i].text).val(bondedAreaPort[i].value);
            $("#select-bondedAreasPort").append($opt);
        }
        
        //价格模式
        var priceModel = JSON.parse($("#priceModelJson").html());
        for(var i=0;i<priceModel.length;i++){
            $opt = $("<option></option>").text(priceModel[i].text).val(priceModel[i].value);
            $("#offer-priceModel").append($opt);
            $("#offer-priceModel").val("1");
        }
        
        //价格术语
        var priceTerm = JSON.parse($("#priceTermJson").html());
        for(var i=0;i<priceTerm.length;i++){
            $opt = $("<option></option>").text(priceTerm[i].text).val(priceTerm[i].value);
            $("#select-priceTerm").append($opt);
        }

        //价格术语 港口
        for(var i=0;i<bondedAreaPort.length;i++){
            $opt = $("<option></option>").text(bondedAreaPort[i].text).val(bondedAreaPort[i].value);
            $("#select-priceTermPort").append($opt);
        }

        //交货港口
        for(var i=0;i<dischargePort.length;i++){
            $opt = $("<option></option>").text(dischargePort[i].text).val(dischargePort[i].value);
            $("#offer-rules-port1").append($opt);
        }

        //计价方式
        var pricingMethod = JSON.parse($("#pricingMethodJson").html());
        for(var i=0;i<pricingMethod.length;i++){
            $opt = $("<option></option>").text(pricingMethod[i].text).val(pricingMethod[i].value);
            $("#select-Pricing_method").append($opt);
        }

        //交货数量标准港口
        for(var i=0;i<dischargePort.length;i++){
            $opt = $("<option></option>").text(dischargePort[i].text).val(dischargePort[i].value);
            $("#offer-rules-port2").append($opt);
        }

        //计量方式
        var measureMethod = JSON.parse($("#measureMethodJson").html());
        for(var i=0;i<measureMethod.length;i++){
            var $opt = $("<option></option>").text(measureMethod[i].text).val(measureMethod[i].value);
            $("#select-measure_method").append($opt);
        }

        //连铁合约
        var measureMethod = JSON.parse($("#ironContractJson").html());
        for(var i=0;i<measureMethod.length;i++){
            var $opt = $("<option></option>").text(measureMethod[i].text).val(measureMethod[i].value);
            $("#select-ironContract").append($opt);
        }
        
        //交易者类型
        var traderType = JSON.parse($("#traderTypeJson").html());
        for(var i=0;i<traderType.length;i++){
            var $opt = $("<option></option>").text(traderType[i].text).val(traderType[i].value);
            $("#select-trader_type").append($opt);

        }
        
        for(var i=0;i<traderType.length;i++){
            var $opt = $("<option></option>").text(traderType[i].text).val(traderType[i].value);
            $("#agency_fee_bearer").append($opt);
        }

        for(var i=0;i<traderType.length;i++){
            var $opt = $("<option></option>").text(traderType[i].text).val(traderType[i].value);
            $("#transport_costs_bearer").append($opt);
        }
        for(var i=0;i<traderType.length;i++){
            var $opt = $("<option></option>").text(traderType[i].text).val(traderType[i].value);
            $("#port_construction_fee_bearer").append($opt);
        }
        for(var i=0;i<traderType.length;i++){
            var $opt = $("<option></option>").text(traderType[i].text).val(traderType[i].value);
            $("#second_vessel_fee_bearer").append($opt);
        }
        for(var i=0;i<traderType.length;i++){
            var $opt = $("<option></option>").text(traderType[i].text).val(traderType[i].value);
            $("#weighing_fee_bearer").append($opt);
        }
        for(var i=0;i<traderType.length;i++){
            var $opt = $("<option></option>").text(traderType[i].text).val(traderType[i].value);
            $("#overdue_storage_fee_bearer").append($opt);
        }

        //结算方式切换
        $("input[type=radio][name=settlement_method]").click(function(){
            clearOtherSettlement_method(this.value)
        });
        //方式0
        $("input#clear_within_several_working_daysArr-0").focus(function(){
            $("#settlement_method-0").prop("checked",true);
            clearOtherSettlement_method(0);
        })
        //方式1
        $("input[name=after_sign_several_working_days]").focus(function(){
            $("#settlement_method-1").prop("checked",true);
            clearOtherSettlement_method(1);
        });
        $("input[name=contract_funds_percentage]").focus(function(){
            $("#settlement_method-1").prop("checked",true);
            clearOtherSettlement_method(1);
        });
        $("input#clear_within_several_working_daysArr-1").focus(function(){
            $("#settlement_method-1").prop("checked",true);
            clearOtherSettlement_method(1);
        });
        //清空其他结算方式填写
        function clearOtherSettlement_method(value){
            //方式0,清空方式1填写
            if(value == "0"){
                $("input[name=after_sign_several_working_days]").val("");
                $("input[name=contract_funds_percentage]").val("");
                $("input#clear_within_several_working_daysArr-1").val("");
            }else{
                //方式1,清空方式0填写
                $("input#clear_within_several_working_daysArr-0").val("");
            }
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
            on: self.selectTypeTab    //切换路由后设置高亮标签
        });
        router.init('/'+$("#router-linkNode >a.selected").attr("linkNode"));//初始化页面
    }
 
    this.loadRouter = function(){
        var path = window.location.hash.slice(2);
        $("#router-pageCotainer").load('/view/offer/add/'+path+".html", function(){
            self.renderFormElement();//渲染表单元素
        });    //加载静态文件
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
    $("#form-offer [data-validetta],#form-offer select[data-validetta]").each(function(index,element){

        if(element.value == ""){
            insertErrorBubble($(element));
        }else{
/*            var $element = $(element);
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
*/
        	 valid -=1;
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

    var pos, W = 0, H = 0;
    var $bubble = $("<div class='validetta-bubble validetta-bubble--bottom'></div>");
    $bubble.html("此项为必填项");

    //组件下拉
    if($element.parents(".react-selectbox").length>0) {
        //品名
        if($element[0].name =="commodityId"){
            esteel_addOffer.selectBox_ItemName.insertErrorBubble("此项为必填项");
        }
        //港口
        if($element[0].name =="portId"){
           esteel_addOffer.selectBox_Port.insertErrorBubble("此项为必填项");
        }
    }
    else if($element.hasClass("uploadFile")) {
        pos = $element.parent(".btn.btn-file").position();
        H = $element.parent(".btn.btn-file")[0].offsetHeight;
        $bubble.css({
            top:pos.top + H + 0,
            left:pos.left + W + 15
        });
        $element.parent(".btn.btn-file").find(".validetta-bubble").remove();
        $element.parent(".btn.btn-file").after($bubble);
    }else{
        pos = $element.position();
        H = $element[0].offsetHeight;
        $bubble.css({
            top:pos.top + H + 0,
            left:pos.left + W + 15
        });
        $element.next(".validetta-bubble").remove();
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
    if(validateOfferInfo()){
        esteel_addOffer.confirm(null,"该报盘将作为草稿保存到我的报盘记录",function(){
        	var validatedUrl = "/offer/iron/validatedInStockOffer";
        	if ($("#tradeMode").val() == 'pricing') {
        		validatedUrl = "/offer/iron/validatedPricingOffer";
        	} else if ($("#tradeMode").val() == 'futures') {
        		validatedUrl = "/offer/iron/validatedFuturesOffer";
        	}
        	
        	var saveUrl = "/offer/iron/saveInStockOffer";
			if ($("#tradeMode").val() == 'pricing') {
				saveUrl = "/offer/iron/savePricingOffer";
			} else if ($("#tradeMode").val() == 'futures') {
				saveUrl = "/offer/iron/saveFuturesOffer";
			}
			
			saveOffer(validatedUrl, saveUrl);
        });
    }
}

//提交报盘
function submit_offer(){
	if(validateOfferInfo()){
		esteel_addOffer.confirm(null,"确定要发布吗",function(){
	        esteel_addOffer.confirm(null,"该报盘将作为草稿保存到我的报盘记录",function(){
	        	var validatedUrl = "/offer/iron/validatedInStockOffer";
	        	if ($("#tradeMode").val() == 'pricing') {
	        		validatedUrl = "/offer/iron/validatedPricingOffer";
	        	} else if ($("#tradeMode").val() == 'futures') {
	        		validatedUrl = "/offer/iron/validatedFuturesOffer";
	        	}
	        	
	        	var saveUrl = "/offer/iron/saveInStockOffer";
				if ($("#tradeMode").val() == 'pricing') {
					saveUrl = "/offer/iron/savePricingOffer";
				} else if ($("#tradeMode").val() == 'futures') {
					saveUrl = "/offer/iron/saveFuturesOffer";
				}
				
				publishOffer(validatedUrl, saveUrl);
	        });
	    });
    }
}

function saveOffer(validatedUrl, saveUrl) {
	esteel_addOffer.ajaxRequest({
	    	url:validatedUrl,
	        data:$('#form-offer').serialize()
	    }, function(data, msg) {
	    	if (msg == 'success') {
	        	 esteel_addOffer.ajaxRequest({
	     	    	url:saveUrl,
	     	        data:$('#form-offer').serialize()
	     	    }, function (data, msg) {
	     	    	alert(msg);
	     	    	if (msg == 'success') {
	     	    		window.location.href = "/offer/iron/myList";
	     	    	}
	     	    });
	    		
	    	} else {
	    		alert(msg);
	    	}
	    });
}

function publishOffer(validatedUrl, saveUrl) {
	esteel_addOffer.ajaxRequest({
	    	url:validatedUrl,
	        data:$('#form-offer').serialize()
	    }, function(data, msg) {
	    	if (msg == 'success') {
	    		$("#offerStatus").val("publish");
	    		
	        	 esteel_addOffer.ajaxRequest({
	     	    	url:saveUrl,
	     	        data:$('#form-offer').serialize()
	     	    }, function (data, msg) {
	     	    	alert(msg);
	     	    	if (msg == 'success') {
	     	    		window.location.href = "/offer/iron/myList";
	     	    	}
	     	    });
	    		
	    	} else {
	    		alert(msg);
	    	}
	    });
}

//品名切换改变指标值
function changeIronAttributeLink(node){
    //典型值
    if($("input[name='indicatorTypeId']").val() == "26"){
        changeIronCommodityIndicator(node,'');
    }else{
        //清空指标值
        $(".offer-kip-table input.form-control").val("");
    }
    if(node.value !=""){
        selectBox_ItemName.remove
    }
}
//品名联动
function changeIronCommodityIndicator(node,index){
    var ironAttr = JSON.parse($("#ironAttributeLinkJson").html());
    for(var attr in ironAttr){
        if(attr == node.label){
            var iron = ironAttr[attr];
            for(var i=0;i<iron.length;i++){
                $("#indicator"+index+"-"+iron[i].text).val(iron[i].value);
                if (iron[i].text == 'GRAIN') {
                    $("#sizeIndicators").val(iron[i].value);    //设置粒度值
                }
            }
        }
    }
}

//指标类型选择，改变指标值
function changeIndicatorValue(value,label){
    if(value == "26" && label =="典型值"){
        //品名切换改变指标值
        changeIronCommodityIndicator(esteel_addOffer.selectBox_ItemName.state.node,'');
    }else{
        //清空指标值
        $(".offer-kip-table input.form-control").val("");
    }
}
/* 远期现货 品名指标联动  start***/
//远期期货商品1联动指标
function changeIronAttributeLink_1(node){
	// 典型值
	if($("input[name='indicatorTypeIdArr']").val() == "26"){
		changeIronCommodityIndicatorIndex(node,'1');
	}else{
		// 清空指标值
		$(".offer-kip-table input.form-control").val("");
	}
}
//远期期货商品1 指标类型选择，改变指标值
function changeIndicatorValue1(value,label){
  if(value == "26" && label =="典型值"){
      //品名切换改变指标值
	  changeIronCommodityIndicatorIndex(esteel_addOffer.selectBox_ItemName1.state.node,'1');
  }else{
      //清空指标值
      $("#indicator-table-1 input.form-control").val("");
  }
}

//期货商品2联动指标
function changeIronAttributeLink_2(node) {
	// 典型值
	if($("input[name='indicatorTypeIdArr']").val() == "26"){
		changeIronCommodityIndicatorIndex(node,'2');
	}else{
		// 清空指标值
		$(".offer-kip-table-1 input.form-control").val("");
	}
}
//期货商品2 指标类型选择，改变指标值
function changeIndicatorValue2(value,label){
  if(value == "26" && label =="典型值"){
      //品名切换改变指标值
	  changeIronCommodityIndicatorIndex(esteel_addOffer.selectBox_ItemName2.state.node,'2');
  }else{
      //清空指标值
      $("#indicator-table-2 input.form-control").val("");
  }
}
//远期期货商品 品名联动
function changeIronCommodityIndicatorIndex(node,index){
  var ironAttr = JSON.parse($("#ironAttributeLinkJson").html());
  for(var attr in ironAttr){
      if(attr == node.label){
          var iron = ironAttr[attr];
          for(var i=0;i<iron.length;i++){
              $("#indicator-"+iron[i].text+"-"+index).val(iron[i].value);
              if (iron[i].text == 'GRAIN') {
                	$("sizeIndicators-"+index).val(iron[i].value);
                }
          }
      }
  }
}
/* 远期现货 品名指标联动  end***/

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
        $("#offer-fixedPrice-goods-2").show();
    }else{
        $("#offer-goods-2").hide();
        $("#offer-fixedPrice-goods-2").hide();
    }
}

//显示浮动价
function showFloatPrice(evt){
	// 价格模式 0:固定价, 1:浮动价
    if(evt.selectedOptions[0].value == "1"){
        $("#offer-fixedPrice-box").hide();
        $("#offer-floatPrice-box").show();
        $("#offer-floatPrice-desc").attr("data-validetta","required");
    }else{
        $("#offer-fixedPrice-box").show();
        $("#offer-floatPrice-box").hide();
        $("#offer-floatPrice-desc").removeAttr("data-validetta");
    }
}

//保税区,选“是”不显示“价格术语”、“目的港”、“运输状态”
function changeBondedArea(checked){
    if(checked){
    	 $("#offer-bondedAreasPort-box").show();
    	 
        $("#offer-priceTerm-box").hide();
        $("#offer-transportStatus-box").hide();
    }else{
    	$("#offer-bondedAreasPort-box").hide();
    	 
        $("#offer-priceTerm-box").show();
        $("#offer-transportStatus-box").show();
    }
}

//文件上传
function upload(elem) {
	var fileId =elem.id; 
	$.ajaxFileUpload({
		url: '/offer/iron/uploadFile',
		secureuri: false,
		fileElementId: elem.id,// file标签的id
		dataType: 'json',
		beforeSend: function (xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function (result) {
			if (result.success) {
				//返回文件id
				if(result.data!=null){
					//保存数据库的字符串
					var saveStr = result.data[0]+result.data[1];
					//赋值
					$("#"+fileId+"Path").val(saveStr);
				}
			} else {
				alert(result.msg);
			}
		}
	});
}