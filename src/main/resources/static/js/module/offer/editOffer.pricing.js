/**
 * Created by wzj on 2017/12/24.
 *    更新报盘
 */
function JBSFrame_OfferEdit_Pricing() {
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

        this.renderDatetimepicker();
        this.renderNumberMask();
    }
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
                    esteel_offerEdit_Pricing.validatePricingNumber = false;   //验证点数量为100的倍数失败
                    insertErrorBubble($(element),"数量必须为100的倍数");
                }else{
                    esteel_offerEdit_Pricing.validatePricingNumber = true;
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
    if(esteel_offerEdit_Pricing.validatePricingNumber==false){
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
            esteel_offerEdit_Pricing.selectBox_ItemName.addValidettaBubble();
        }
        //港口
        if($element[0].name =="Port"){
            esteel_offerEdit_Pricing.selectBox_Port.addValidettaBubble();
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
var esteel_offerEdit_Pricing;
$(document).ready(function (e) {
	esteel_offerEdit_Pricing = new JBSFrame_OfferEdit_Pricing();
    //初始化UI
	esteel_offerEdit_Pricing.initUI();
	//渲染表单元素
	esteel_offerEdit_Pricing.renderFormElement();
});

//保存报盘
function save_offer(){
    if(validateOfferInfo()){
        esteel_offerEdit_Pricing.confirm(null,"该报盘将作为草稿保存到我的报盘记录",function(){
        	var _url = "/offer/validatedInStockOffer";
			if ($("#tradeMode").val() == 'pricing') {
				_url = "/offer/validatedPricingOffer";
			} else if ($("#tradeMode").val() == 'futures') {
				_url = "/offer/validatedFuturesOffer";
			}
			
        	 esteel_offerEdit_Pricing.ajaxRequest({
     	    	url:_url,
     	        data:$('#form-offer').serialize()
     	    },  function (result) {
     	    	if (result.success) {
     	    		$("#form-offer")[0].submit();
     	    	} else {
     	    		alert(result.msg);
     	    	}
     	    });
        });
    }
}

//提交报盘
function submit_offer(){
	if(validateOfferInfo()){
		esteel_offerEdit_Pricing.confirm(null,"确定要发布吗",function(){
			var _url = "/offer/validatedInStockOffer";
			if ($("#tradeMode").val() == 'pricing') {
				_url = "/offer/validatedPricingOffer";
			} else if ($("#tradeMode").val() == 'futures') {
				_url = "/offer/validatedFuturesOffer";
			}
			
			esteel_offerEdit_Pricing.ajaxRequest({
     	    	url:_url,
     	        data:$('#form-offer').serialize()
     	    },  function (result) {
     	    	if (result.success) {
     	    		$("#offerStatus").val("publish");
     	    		$("#form-offer")[0].submit();
     	    	} else {
     	    		alert(result.msg);
     	    	}
     	    });
	    });
    }
}

//远期期货商品1联动指标
function changeIronAttributeLink_1(node){
    changeIronCommodityIndicator1(node,'1');
}
//远期期货商品1 指标类型选择，改变指标值
function changeIndicatorValue1(value,label){
    if(value == "26" && label =="典型值"){
        //品名切换改变指标值
        changeIronCommodityIndicator1(esteel_addOffer.selectBox_ItemName1.state.node,'');
    }else{
        //清空指标值
        $(".offer-kip-table input.form-control").val("");
    }
}
//远期期货商品1 品名联动
function changeIronCommodityIndicator1(node,index){
    var ironAttr = JSON.parse($("#ironAttributeLinkJson").html());
    for(var attr in ironAttr){
        if(attr == node.label){
            var iron = ironAttr[attr];
            for(var i=0;i<iron.length;i++){
                $("#indicator"+index+"-"+iron[i].text+"-1").val(iron[i].value);
            }
        }
    }
}

//期货商品2联动指标
function changeIronAttributeLink_2(node) {
    changeIronCommodityIndicator2(node,'2');
}
//期货商品2 指标类型选择，改变指标值
function changeIndicatorValue2(value,label){
    if(value == "26" && label =="典型值"){
        //品名切换改变指标值
        changeIronCommodityIndicator2(esteel_addOffer.selectBox_ItemName2.state.node,'');
    }else{
        //清空指标值
        $(".offer-kip-table input.form-control").val("");
    }
}
//期货商品2 品名联动
function changeIronCommodityIndicator2(node,index){
    var ironAttr = JSON.parse($("#ironAttributeLinkJson").html());
    for(var attr in ironAttr){
        if(attr == node.label){
            var iron = ironAttr[attr];
            for(var i=0;i<iron.length;i++){
                $("#indicator"+index+"-"+iron[i].text+"-2").val(iron[i].value);
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