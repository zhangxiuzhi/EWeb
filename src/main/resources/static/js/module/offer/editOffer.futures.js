/**
 * Created by wzj on 2017/12/24. 更新报盘
 */
function JBSFrame_OfferEdit_Futures() {
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
            curList:JSON.parse($("#counterpartyJson").html())
        }
        if($TradeCustomer.length>0){
            this.select_TradeCustomer = ReactDOM.render(React.createElement(ComponentEsteelMultiSelect,{
                inputName:$TradeCustomer.attr("inputName"),
                data:TradeCustomer_data
            }), $TradeCustomer[0]);
        }
        
        var offer = JSON.parse($("#offerJson").html());
        var offerAttachList = JSON.parse($("#offerAttachListJson").html());
        
        //一船俩货
        var $1ship2goods = $("#component-radioBoxGroup-1ship2goods");
        if($1ship2goods.length>0){
            this.toggle_1ship2goods = ReactDOM.render(React.createElement(ComponentToggle,{
                inputName:$1ship2goods.attr("inputName"),
                inputValue:offer.isMultiCargo,
                onChange:show2goods    //显示2个商品
            }), $1ship2goods[0]);
        }
        
        show2goods(offer.isMultiCargo == '1')
        
        //是否拆分
        var $Split = $("#component-toggle-split");
        if($Split.length>0) {
            this.toggle_Split = ReactDOM.render(React.createElement(ComponentToggle, {
                inputName: $Split.attr("inputName"),
                inputValue: offer.isSplit,
                onChange:showQDL//显示起订量
            }), $Split[0]);
        }
        
        showQDL(offer.isSplit == '1');
        
        //匿名
        var $Anonym = $("#component-toggle-anonym");
        if($Anonym.length>0) {
            this.toggle_anonym = ReactDOM.render(React.createElement(ComponentToggle, {
            	inputName: $Anonym.attr("inputName"),
                inputValue: offer.isAnonymous,
            }), $Anonym[0]);
        }
        
        //商品1品名下拉
        var $ItemName1 = $("#component-selectBox-ItemName-1");
        if($ItemName1.length>0){
            this.selectBox_ItemName1 = ReactDOM.render(React.createElement(ComponentSelectBox,{
            	data:JSON.parse($("#ironCommodityJson").html()),
                inputName:$ItemName1.attr("inputName"),
                inputValue:offerAttachList[0].commodityId,
                validetta:$ItemName1.data("validetta"),
                onChange:changeIronAttributeLink_1 //期货商品1联动指标
            }), $ItemName1[0]);
        }
        //商品1指标类型
        var $kpiType1 = $("#component-radioBoxGroup-kpiType-1");
        if($kpiType1.length>0) {
            this.radioBox_kpiType1 = ReactDOM.render(React.createElement(ComponentRadioBox, {
                data: JSON.parse($("#indicatorTypeJson").html()),
                value: offerAttachList[0].indicatorTypeId,
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
                inputValue:offer.isMultiCargo=='0'?$ItemName2.attr("inputValue"):offerAttachList[1].commodityId,
                validetta:$ItemName2.data("validetta"),
                onChange:changeIronAttributeLink_2 //期货商品2联动指标
            }), $ItemName2[0]);
        }
        //商品2指标类型
        var $kpiType2 = $("#component-radioBoxGroup-kpiType-2");
        if($kpiType2.length>0) {
            this.radioBox_kpiType2 = ReactDOM.render(React.createElement(ComponentRadioBox, {
                data: JSON.parse($("#indicatorTypeJson").html()),
                value: offer.isMultiCargo=='0'?"26":offerAttachList[1].indicatorTypeId,
                className: "TagStyle offerKpi",
                inputName:$kpiType2.attr("inputName"),
                onChange:changeIndicatorValue2 //指标类型选择，改变指标值
            }), $kpiType2[0]);
        }
        
        //报税区
        var $bondedAreas = $("#component-radioBoxGroup-bondedAreas");
        if($bondedAreas.length>0){
            this.toggle_bondedAreas = ReactDOM.render(React.createElement(ComponentToggle,{
                inputName:$bondedAreas.attr("inputName"),
                inputValue:offerAttachList[0].isBondedArea,
                onChange:changeBondedArea //指标类型选择，改变指标值
            }), $bondedAreas[0]);
        }
        
        changeBondedArea(offerAttachList[0].isBondedArea == '1');

        //价格模式
        var priceModel = JSON.parse($("#priceModelJson").html());
        for(var i=0;i<priceModel.length;i++){
            $opt = $("<option></option>").text(priceModel[i].text).val(priceModel[i].value);
            $("#offer-priceModel").append($opt);
        }
        
        $("#offer-priceModel").val(offerAttachList[0].priceModel);
        showFloatPrice($("#offer-priceModel"))
        
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
                    esteel_offerEdit_Futures.validatePricingNumber = false;   //验证点数量为100的倍数失败
                    insertErrorBubble($(element),"数量必须为100的倍数");
                }else{
                    esteel_offerEdit_Futures.validatePricingNumber = true;
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
    if(esteel_offerEdit_Futures.validatePricingNumber==false){
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
            esteel_offerEdit_Futures.selectBox_ItemName.addValidettaBubble();
        }
        //港口
        if($element[0].name =="Port"){
            esteel_offerEdit_Futures.selectBox_Port.addValidettaBubble();
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
var esteel_offerEdit_Futures;
$(document).ready(function (e) {
	esteel_offerEdit_Futures = new JBSFrame_OfferEdit_Futures();
    //初始化UI
	esteel_offerEdit_Futures.initUI();
	//渲染表单元素
	esteel_offerEdit_Futures.renderFormElement();
});

//保存报盘
function save_offer(){
    if(validateOfferInfo()){
        esteel_offerEdit_Futures.confirm(null,"该报盘将作为草稿保存到我的报盘记录",function(){
        	var _url = "/offer/iron/validatedFuturesOffer";
			
        	 esteel_offerEdit_Futures.ajaxRequest({
     	    	url:_url,
     	        data:$('#form-offer').serialize()
     	    },  function (data, msg) {
     	    	if (msg == 'success') {
     	    		
     	    		var _url = "/offer/iron/updateFuturesOffer";
     				
     				esteel_offerEdit_Futures.ajaxRequest({
     	     	    	url:_url,
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
        });
    }
}

//提交报盘
function submit_offer(){
	if(validateOfferInfo()){
		esteel_offerEdit_Futures.confirm(null,"确定要发布吗",function(){
			var _url = "/offer/iron/validatedFuturesOffer";
			
			esteel_offerEdit_Futures.ajaxRequest({
     	    	url:_url,
     	        data:$('#form-offer').serialize()
     	    },  function (data, msg) {
     	    	if (msg == 'success') {
     	    		$("#offerStatus").val("publish");
     	    		
     	    		var _url = "/offer/iron/updateFuturesOffer";
     				
     				esteel_offerEdit_Futures.ajaxRequest({
     	     	    	url:_url,
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
	    });
    }
}

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
	  changeIronCommodityIndicatorIndex(esteel_offerEdit_Futures.selectBox_ItemName1.state.node,'1');
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
	  changeIronCommodityIndicatorIndex(esteel_offerEdit_Futures.selectBox_ItemName2.state.node,'2');
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
	if($(evt.selector).val() == "1"){
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