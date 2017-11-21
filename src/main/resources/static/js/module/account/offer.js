/**
 * 报盘
 * Created by wzj on 2017/6/7.
 */

function JBSFrame_offer(){

	JBSFrame.call(this);

	this.offerWizard=null;
	this.select_transactions = "";//交易对象
	this.startDate=null;
	this.endDate=null;
	this.dealDateStart=null;
	this.dealDateEnd=null;
	//验证点价数量，必须为100的倍数
	this.validatePricingNumber = true;

	//报盘步骤验证交易规则，是否跳过
	this.stepSkipOfferRules = false;

	//初始化UI
	this.initUI = function() {

		//顶部菜单栏
		//当前选中交易大厅
		//ReactDOM.render( < HeaderMenu focusItem = "报盘" / >, document.getElementById("component-headerMenu"));
		ReactDOM.render(React.createElement(HeaderMenu, { focusItem: "报盘" }), document.getElementById("component-headerMenu"));

		setTimeout(function(){
			$(".pricing-offer-form").css("opacity",1)
		},200)

		//步骤
		this.offerWizard = $('#offer-rootwizard').bootstrapWizard({
			onTabClick: function(tab, navigation, index) {
				return false;
			},
			onTabShow:function(tab, navigation, index){
				//第一布显示发布按钮
				if(index+1==1){
					$("#offer-rootwizard .pager.wizard  li.finish").removeClass("hidden");
					$("#offer-rootwizard .pager.wizard  li.next a.rules").show();
					$("#offer-rootwizard .pager.wizard  li.next a.next").hide();
					$("#offer-rootwizard .pager.wizard  li.finish a.btn").html('<i class="fa fa-paper-plane-o"></i> 直接发布');

				}
				//第二布讲
				if(index+1==2){
					$("#offer-rootwizard .pager.wizard  li.next a.rules").hide();
					$("#offer-rootwizard .pager.wizard  li.next a.next").show();
					$("#offer-rootwizard .pager.wizard  li.finish a.btn").html('<i class="fa fa-paper-plane-o"></i> 发布');
				}

			},
			onNext: function(tab, navigation, index) {
				//不选中同意交易规则，无法下一步
				if(!$("#offer-pricingRules").prop("checked")){
					alertError("您还未接受点钢网点价交易规则和异议处理")
					return false;
				}
				//验证商品
				if(index+1 == 2 ) {
					if (validateOfferInfo() == true) {
						$(tab).toggleClass("pass");
						return true
					}else{
						return false;
					}
				}
				//验证规则
				if(index+1 == 3){
					//验证
					if(validateOfferRulesForm() == true){
							$(tab).toggleClass("pass");
							//展示添加编辑完预览效果
							show_offerEditView();
							return true;
					}else{
							return false;
					}

				}

			},
			onPrevious:function(tab, navigation, index){
				//$(tab).toggleClass("pass");
				if(index==1 || index == 0){
					$(tab).prev("li").removeClass("pass");
				}
			},
			onFinish:function(tab, navigation, index){
				if(index-1 == 1 && $("#offer-rootwizard .pager.wizard  li.previous").hasClass("disabled")){
					//不选中同意交易规则，无法下一步
					if(!$("#offer-pricingRules").prop("checked")){
						alertError("您还未接受点钢网点价交易规则和异议处理")
						return false;
					}
					//验证商品
					if (validateOfferInfo() == false) {
						return false;
					}else{
						var confirmMessage = "确定保存和发布当前报盘?";
						var $ipts = $("#offer-wizard-rules .form-control");//规则内表单
						var validateEmpty = $ipts.length;	//规则内表单数量
						for(var i=0;i<$ipts.length;i++){
							if($($ipts[i]).val()==""){	//如果表单未填
								validateEmpty-=1;
							}
						}
						//当所有表单都未填写
						if(validateEmpty>0){
							confirmMessage = "若选择发布，则您已经填写的交货结算条款将会消失。确定要发布吗?";
						}
						esteel_offer.confirm3(confirmMessage, "保存","发布","取消", function () {
							$("#offer-wizard-rules .form-control").val("");
							$("#offer-wizard-rules select").val("");
							$("[name=checkModePort]").val("");
							$("[name=checkNumPort]").val("");
							save_offer();
						},function(){
							$("#offer-wizard-rules .form-control").val("");
							$("#offer-wizard-rules select").val("");
							$("[name=checkModePort]").val("");
							$("[name=checkNumPort]").val("");
							push_offer();
						});
						return false;
					}

				}
				if(index+1 == 3) {
					if (validateOfferRulesForm() == false) {
						return false;
					}
					esteel_offer.confirm3("确定保存和发布当前报盘?", "保存", "发布", "取消", function () {
						save_offer();
					}, function () {
						push_offer();
					});
				}

			}
		});



		//验证
		/*
		$('#form-offer').validetta({
			realTime: true,
			bubblePosition:'bottom',
			onValid : function( event ){
				event.preventDefault();
				if(esteel_offer.stepValidate.step1 && esteel_offer.stepValidate.step2){
					$('#form-offer')[0].submit();	//提交
					return true;
				}else{
					esteel_offer.stepValidate.step1 = true;		//商品信息验证通过
					esteel_offer.stepValidate.step2 = validateOfferRulesForm();		//交易规则验证通过
					$("#offer-rootwizard").bootstrapWizard("next");
					return false;
				}

			},
			onError:function(event){
				event.preventDefault();
				esteel_offer.stepValidate.step1 = false;
				return false;
			}
		},{
			required  : '此项为必填项.'
		});
		*/


		//金额控制
		this.renderMoneyMask();
		//数量控制
		this.renderNumberMask();
		//不能为负数
		$("#offerNum").change(function(e) {
			var newValue = this.value;
			newValue = newValue.replace("-","");
			this.value = newValue;
		});


		//生成时间控件
		this.renderDatetimepicker();

		//加载交易对象
		load_objectList();
		//加载品名列表
		load_tbPricingCommodityList();
		//加载港口列表
		load_portList();
		//加载产地列表
		load_placeList();
		//合约
		load_priceIndex();
		//升贴水
		init_premiumsDiscountsSwitch();
		//溢短装
		//init_moreorlessSwitch();
		//交货方式,港口
		var port1 = $("#offer-rules-port1").val();
		$("#offer-rules-port1 option[value='"+port1+"']").attr("selected",true);
		//交货方式
		var checkModeWay = $("[name=checkModeWay]").attr("value");
		$("[name=checkModeWay] option[value='"+checkModeWay+"']").attr("selected",true);
		//交货数量标准,港口
		var port2 = $("#offer-rules-port2").val();
		$("#offer-rules-port2 option[value='"+port2+"']").attr("selected",true);

		//交货数量标准
		var checkNum = $("[name=checkNum]").attr("value");
		$("[name=checkNum] option[value='"+checkNum+"']").attr("selected",true);
		//货物规格标准
		var checkGoodStandard = $("[name=checkGoodStandard]").attr("value");
		$("[name=checkGoodStandard] option[value='"+checkGoodStandard+"']").attr("selected",true);


		//点价开始时间

		$("#offer-pricingStartDate").datetimepicker({
			locale: 'zh-cn',
			minDate: moment(),
			icons:datetimepicker_icon,
			tooltips:datetimepicker_tooltips,
			format: "YYYY-MM-DD",//日期的格式
			showClose:false,
			daysOfWeekDisabled: [0,6]
		});
		if($("#offer-pricingStartDate").siblings(".input-group-btn").length>0){
			var btn = $("#offer-pricingStartDate").siblings(".input-group-btn");
			btn.on("click",function(){
				$("#offer-pricingStartDate").datetimepicker('show');
			});
		}
		this.startDate = $("#offer-pricingStartDate").on("dp.change", function (evt) {
			//从明天开始
			//var nextDay = GetDateStr2(new Date(),1);
			var today = new Date();
			//$(evt.currentTarget).datetimepicker("minDate",new Date(today));
			//$("#offer-pricingEndDate").datetimepicker("date",evt.date);
			//console.log("startData",evt)
		});
		$("#offer-pricingStartDate").on("dp.error", function (evt) {
			//console.log("error",evt)
			//var nextDay = GetDateStr2(new Date(),1);
			//var today = new Date();
			$(evt.currentTarget).datetimepicker("date",new Date());
		});
		//点价结束时间
		$("#offer-pricingEndDate").datetimepicker({
			locale: 'zh-cn',
			minDate: $("#offer-pricingStartDate").datetimepicker("date"),
			icons:datetimepicker_icon,
			tooltips:datetimepicker_tooltips,
			format: "YYYY-MM-DD",//日期的格式
			showClose:false,
			daysOfWeekDisabled: [0,6]
		});
		if($("#offer-pricingEndDate").siblings(".input-group-btn").length>0){
			var btn = $("#offer-pricingEndDate").siblings(".input-group-btn");
			btn.on("click",function(){
				$("#offer-pricingEndDate").datetimepicker('show');
			});
		}
		this.endDate = $("#offer-pricingEndDate").on("dp.change", function (evt) {
			//如果点价开始时间未选
			if($(this.startDate).datetimepicker("date") == null){
				//从明天开始
				//var tomorrow = GetDateStr(1);
				//var today = new Date();
				//$(evt.currentTarget).datetimepicker("minDate", new Date(today));
			}else{
				//$(evt.currentTarget).datetimepicker("minDate", $(this.startDate).datetimepicker("date"));
			}
			/*
			//如果点价结束时间==交货期开始
			var ed = esteel_offer.formatDate(new Date($(this.endDate).datetimepicker("date")));
			var dds = esteel_offer.formatDate(new Date($(this.dealDateStart).datetimepicker("date")));
			if( ed==dds ){
				var d = GetDateStr2(ed,1);
				$(this.dealDateStart).datetimepicker("date",d);//交货期开始时间=点价结束时间+1
				$(this.dealDateEnd).datetimepicker("date",d);//交货期结束时间=点价结束时间+1
			}
			*/
		}.bind(this));
		$("#offer-pricingEndDate").on("dp.error", function (evt) {
			//console.log("error",evt)
			//var nextDay = GetDateStr2(new Date(),1);
			//var today = new Date();
			$(evt.currentTarget).datetimepicker("date",new Date());
		});

		//交货期开始
		this.dealDateStart = $("#offer-dealDateStart").on("dp.change", function (evt) {
			/*
			//如果点价结束时间未选
			if($(this.endDate).datetimepicker("date") == null){
				//从明天开始
				var tomorrow = GetDateStr(1);
				$(evt.currentTarget).datetimepicker("minDate", new Date(tomorrow));
			}else{
				$(evt.currentTarget).datetimepicker("minDate", $(this.endDate).datetimepicker("date"));
			}
			//如果点价结束时间==交货期开始
			var ed = esteel_offer.formatDate(new Date($(this.endDate).datetimepicker("date")));
			var dds = esteel_offer.formatDate(new Date($(this.dealDateStart).datetimepicker("date")));
			if( ed==dds ){
				var d = GetDateStr2(ed,1);
				$(this.dealDateStart).datetimepicker("date",d);//交货期开始时间=点价结束时间+1
				$(this.dealDateEnd).datetimepicker("date",d);//交货期结束时间=点价结束时间+1
			}
			*/
			//如果交货期开始>交货期结束
			/*dds = $(evt.currentTarget).datetimepicker("date");
			var dde = $(this.dealDateEnd).datetimepicker("date");
			var d = GetDateStr2(dde,1);
			if(dds > dde){
				$(this.dealDateEnd).datetimepicker("date",d);//交货期结束时间=交货期开始时间+1
			}else{
				$(evt.currentTarget).datetimepicker("minDate", d);
			}*/
		}.bind(this));
		//交货期结束
		this.dealDateEnd = $("#offer-dealDateEnd").on("dp.change", function (evt) {
/*
			//如果交货期开始未选
			if($(this.dealDateStart).datetimepicker("date") == null){
				//从明天开始
				//var tomorrow = GetDateStr(1);
				//$(evt.currentTarget).datetimepicker("minDate", new Date(tomorrow));
				var dde = $("#offer-dealDateEnd").val();
				$(this.dealDateStart).datetimepicker("maxDate",dde);
			}else{
				//$(evt.currentTarget).datetimepicker("minDate", $(this.dealDateStart).datetimepicker("date"));

				//从交货期开始的第二天开始
				var dds = $(this.dealDateStart).datetimepicker("date");
				var dde = GetDateStr2(dds,1);
				$(evt.currentTarget).datetimepicker("minDate", dde);
			}
*/
		}.bind(this));

		//有效期
		$("#offer-expiryDate").datetimepicker("minDate",GetDateTimeStr(0));//有效期从明天开始算起

	}

	//加载品名列表
	function load_tbPricingCommodityList(){
		esteel_offer.ajaxRequest({
			url:"pricing/pricingCommodity"
		},function(data,msg){
			//品名
			var $commodity = $("#component-tbPricingCommodity");
			//var select_tbPricingCommodity = ReactDOM.render(<InputGroupSelect data={data} multiple="false" class="form-group" formLabel={$commodity.attr("label")} formName={$commodity.attr("name")} validetta={$commodity.data("validetta")}/>,$commodity[0]);
			var select_tbPricingCommodity =ReactDOM.render(React.createElement(InputGroupSelect, { data:data, multiple:"false", class:"form-group", formLabel:$commodity.attr("label"), formName:$commodity.attr("name"), validetta:$commodity.data("validetta") }), $commodity[0]);


			//编辑
			if($(".pricing-offer-form").hasClass("editForm")){
				select_tbPricingCommodity.setSelectValue($("#commodityKey").val())
			}
		})
	}

	//加载交易对象列表
	function load_objectList(){
		esteel_offer.ajaxRequest({
			url:"pricing/whiteCustomer"
		},function(data,msg){
			//交易对象
			//data.push({text: "所有交易对象", value: "-1"})
			var $transactions = $("#component-transactions");
			var validetta = $transactions.attr("validetta");
			//esteel_offer.select_transactions = ReactDOM.render(<InputGroupSelect data={data} multiple="true" validetta={validetta} class="form-group autoHeight" formLabel={$transactions.attr("label")} formName={$transactions.attr("name")} onSelect={validateSelectSuccess}/>,$transactions[0]);
			esteel_offer.select_transactions = ReactDOM.render(React.createElement(InputGroupSelect, { data:data, multiple:"true", validetta:validetta, class:"form-group", formLabel:$transactions.attr("label"), formName:$transactions.attr("name"),
				noResults:"当前无可交易对象，请前往会员中心添加白名单成员。",
				onSelect:validateSelectSuccess
				,customRenderFinishCallBack:_customRenderFinishCallBack
			}), $transactions[0]);

			//编辑
			if($(".pricing-offer-form").hasClass("editForm")){

				//如果选中对象数量=列表中所有对象数量
				if($("select[name=transactions] option").length == $("#transactions .tag").length){
					selectAllTransactions($(".btn-selectAllTransactions"));
				}else{
					var values=[];
					$("#transactions .tag").each(function(index,elem){
						values.push($(elem).html());
					});
					esteel_offer.select_transactions.setSelectValue(values);
				}
			}
		})
	}
	//加载港口列表
	function load_portList(){
		esteel_offer.ajaxRequest({
			url:"pricing/pricingPort"
		},function(data,msg){
			//港口
			var $shipmentportKey = $("#component-shipmentportKey");
			//var select_shipmentport = ReactDOM.render(<InputGroupSelect data={data} multiple="false" class="form-group" formLabel={$shipmentportKey.attr("label")} formName={$shipmentportKey.attr("name")} />,$shipmentportKey[0]);
			var select_shipmentport =ReactDOM.render(React.createElement(InputGroupSelect, { data:data, multiple:"false,", class:"form-group", formLabel:$shipmentportKey.attr("label"), formName:$shipmentportKey.attr("name") }), $shipmentportKey[0]);

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
				search: function( event, ui ) {
					console.log("search")
				}
			}).data("ui-autocomplete")._renderItem = function (ul, item) {
				return $("<li>")
				.data("item.autocomplete", item)
				.append($("<a></a>").text(item.text))
				.appendTo(ul);
			};

			//编辑
			if($(".pricing-offer-form").hasClass("editForm")){
				select_shipmentport.setSelectValue($("#shipmentportKey").val())
				for(var i=0;i<data.length;i++){
					if(data[i].value == $("[name=checkModePort]").val()){
						$("#offer-rules-port1").val(data[i].text);
					}
					if(data[i].value == $("[name=checkNumPort]").val()){
						$("#offer-rules-port2").val(data[i].text);
					}
				}

			}
		})
	}
	//加载产地列表
	function load_placeList(){
		esteel_offer.ajaxRequest({
			url:"pricing/pricingPlace"
		},function(data,msg){
			//data= [ {text: "澳大利亚", value: 2000001}, {text: "巴西", value: 2000002}];
			//产地
			var $placeKey = $("#component-placeKey");
			//var select_place = ReactDOM.render(<InputGroupSelect data={data} multiple="false" class="form-group" formLabel={$placeKey.attr("label")} formName={$placeKey.attr("name")} />,$placeKey[0]);
			var select_place =ReactDOM.render(React.createElement(InputGroupSelect, { data:data, multiple:"false,", class:"form-group", formLabel:$placeKey.attr("label"), formName:$placeKey.attr("name") }), $placeKey[0]);

			//编辑
			if($(".pricing-offer-form").hasClass("editForm")){
				select_place.setSelectValue($("#placeKey").val())
			}
		})
	}
	//加载合约列表
	function load_priceIndex(){
		esteel_offer.ajaxRequest({
			url:"settlementprice/findAllfield"
		},function(data,msg){
			//合约
			var d =[];
			for(var i=0;i<data.length;i++){
				d.push({text:data[i].text,value:data[i].text})
			}
			var $priceIndex = $("#component-priceIndex");
			//var select_index = ReactDOM.render(<InputGroupSelect data={d} multiple="false" class="form-group" formLabel={$priceIndex.attr("label")} formName={$priceIndex.attr("name")} />,$priceIndex[0]);
			var select_index =ReactDOM.render(React.createElement(InputGroupSelect, { data:d, multiple:"false,", class:"form-group", formLabel:$priceIndex.attr("label"), formName:$priceIndex.attr("name") }), $priceIndex[0]);

			//编辑
			if($(".pricing-offer-form").hasClass("editForm")){
				select_index.setSelectValue($("#priceIndex").val())
			}
		})
	}
	//升贴水
	function init_premiumsDiscountsSwitch(){

		/*
		$("#premiumsDiscounts").inputmask({
				alias: "currency",
				prefix: ""
			});*/
		//obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
		$("#premiumsDiscounts").change(function(evt) {
			var obj = evt.currentTarget;

			obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
			obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数
			if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
				obj.value= parseFloat(obj.value);
			}
			obj.value = "+"+obj.value;
		});


		$("#premiumsDiscounts-switch").click(function(){
			$("#premiumsDiscounts").val(switchPlus($("#premiumsDiscounts")));
		});
		/*$("#premiumsDiscounts").keyup(function(){
			var reg = /^(\-|\+?)\d+(\.\d+)?$/;
			if(!reg.test(this.value)){
				this.value="";
			}
		});
		$("#premiumsDiscounts").on("change",function(evt){
			evt.currentTarget.value = "+"+(evt.currentTarget.value.replace("+",""));
		});*/
	}

	//溢短装
	function init_moreorlessSwitch(){
		$("#moreorless-switch").click(function(){
			$("#moreorless").val(switchPlus($("#moreorless")));
		});
		$("#moreorless").keyup(function(){
			var reg = /^(\-|\+?)\d+(\.\d+)?$/;
			if(!reg.test(this.value)){
				this.value="";
			}
		});
		$("#moreorless").on("change",function(evt){
			evt.currentTarget.value = "+"+(evt.currentTarget.value.replace("+",""));
		});
	}


}



/*
 //body load
 --------------------------------------------------------------------*/

var esteel_offer;
$(document).ready(function(e) {
	esteel_offer = new JBSFrame_offer();
	//初始化UI
	esteel_offer.initUI()
});

//重置报盘
function resetOffer(){
	$("#form-offer")[0].reset();
}

//切换正负数
function switchPlus($input){

	if($input.val()==""){
		return "";
	}
	var val="";
	if($input.val().substring(0,1) == "+") {
		val = $input.val().replace("+", "-");
	}
	else if($input.val().substring(0,1) == "-"){
		val = $input.val().replace("-","+");
	}else{
		val = "+"+$input.val();
	}
	return val;
}

//验证交易对象,时间选择
function validateSelectSuccess(target){
	if($(target).find("option:selected").length>0 || $(target).val()!=""){
		$(target).parent(".input-group").removeClass("validetta-error");
		$(target).siblings(".validetta-bubble").remove();
	}
}

function push_offer(){
	$("#offer-offerStatus").val(1);	//状态：发布
	//$("#offer-submit").click();
	$("#form-offer")[0].submit()
}
function save_offer(){
	$("#offer-offerStatus").val(0);	//状态：发布
	//如果跳过规则，则清空规则表单
	//$("#offer-submit").click();
	$("#form-offer")[0].submit();
}



//验证报盘商品信息
function validateOfferInfo(){
	var valid = $("#offer-wizard-info [data-validetta],#offer-wizard-info select[data-validetta]").length;
	$("#offer-wizard-info .form-control[data-validetta],#offer-wizard-info select[data-validetta]").each(function(index,element){
		if(element.value == ""){	///交易对象
			insertErrorBubble($(element));
		}else{
			var $element = $(element);
			//报盘数量100倍数
			if($element.attr("id") == "offerNum"){
				var value = $element.val();
				var pos,H;
				if(value !="" && Number(value)%100>0) {
					esteel_offer.validatePricingNumber = false;   //验证点数量为100的倍数失败
					insertErrorBubble($(element),"数量必须为100的倍数");
				}else{
					esteel_offer.validatePricingNumber = true;
					$element.next(".validetta-bubble").remove();
					valid -=1;
				}
			}else{
				valid -=1;
			}
		}
	});

//验证报盘数量是否为100倍数
	if(esteel_offer.validatePricingNumber==false){
		valid +=1;
	}
	//console.log("验证商品",valid)
	return valid == 0 ?true : false;
}

//验证交易规则表单
function validateOfferRulesForm(){
	var valid = $("#offer-wizard-rules .form-control[data-validetta],#offer-wizard-rules select[data-validetta]").length;
	$("#offer-wizard-rules .form-control[data-validetta],#offer-wizard-rules select[data-validetta]").each(function(index,element){
		if(element.value == ""){
			insertErrorBubble($(element));
		}else{
			valid -=1;
		}

		/*if( $(element).attr("type") =="file" && $(element).attr("value") =="") {
			insertErrorBubble($(element));
		}else{
			valid -=1;
		}*/
	});

	//console.log("验证规则",valid)

	return valid == 0 ?true : false;
}

function insertErrorBubble($element,errorText){
	$element.next(".validetta-bubble").remove();

	var pos, W = 0, H = 0;
	var $bubble = $("<div class='validetta-bubble validetta-bubble--bottom'></div>");
	$bubble.html("此项为必填项");

	if($element.hasClass("uploadFile")) {
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


//展示添加编辑完预览效果
function show_offerEditView(){

	//交易对象
	$("#offerEditView-transactions").empty();
	$("#component-transactions option:selected").each(function(index,opt){
		$("#offerEditView-transactions").append("<div class='tag'>"+opt.text+"</div>");
	});
	//品名
	$("#offerEditView-commodityKey").empty();
	$("#component-tbPricingCommodity option:selected").each(function(index,opt){
		$("#offerEditView-commodityKey").append("<div class='tag'>"+opt.text+"</div>");
	});
	//产地
	$("#offerEditView-placeKey").empty();
	$("#component-placeKey option:selected").each(function(index,opt){
		$("#offerEditView-placeKey").append("<div class='tag'>"+opt.text+"</div>");
	});
	//港口
	$("#offerEditView-shipmentportKey").empty();
	$("#component-shipmentportKey option:selected").each(function(index,opt){
		$("#offerEditView-shipmentportKey").append("<div class='tag'>"+opt.text+"</div>");
	});
	//数量
	$("#offerEditView-offerNum").empty();
	$("#offerEditView-offerNum").html($("#offerNum").val());
	//溢短装
	$("#offerEditView-moreorless").empty();
	$("#offerEditView-moreorless").html($("#moreorless").val());
	//合约
	$("#offerEditView-priceIndex").empty();
	$("#component-priceIndex option:selected").each(function(index,opt){
		$("#offerEditView-priceIndex").append("<div class='tag'>"+opt.text+"</div>");
	});
	//升贴水
	$("#offerEditView-premiumsDiscounts").empty();
	$("#offerEditView-premiumsDiscounts").html($("#premiumsDiscounts").val());
	//点价期start
	$("#offerEditView-offerDateStart").empty();
	$("#offerEditView-offerDateStart").html($("#offer-pricingStartDate").val());
	$("#offerEditView-offerDateEnd").empty();
	$("#offerEditView-offerDateEnd").html($("#offer-pricingEndDate").val());
	//交货期
	$("#offerEditView-dealDateStart").empty();
	$("#offerEditView-dealDateStart").html($("#offer-dealDateStart").val());
	$("#offerEditView-dealDateEnd").empty();
	$("#offerEditView-dealDateEnd").html($("#offer-dealDateEnd").val());
	//有效期
	$("#offerEditView-expiryDate").empty();
	$("#offerEditView-expiryDate").html($("#offer-expiryDate").val());
	//备注
	$("#offerEditView-offerDescription").empty();
	$("#offerEditView-offerDescription").html($("#offer-offerDescription").val());

	//规则内容
	$("#offerEditView-balanceDayAfter").empty();
	$("#offerEditView-balanceDayAfter").html($("[name=balanceDayAfter]").val());
	//
	$("#offerEditView-balanceDayBefore").empty();
	$("#offerEditView-balanceDayBefore").html($("[name=balanceDayBefore]").val());
	//
	$("#offerEditView-rules-port1").empty();
	$("#offerEditView-rules-port1").html($("#offer-rules-port1").val());
	//
	$("#offerEditView-checkModeWay").empty();
	$("#offerEditView-checkModeWay").html($("[name=checkModeWay] option:selected").text());
	//
	$("#offerEditView-rules-port2").empty();
	$("#offerEditView-rules-port2").html($("#offer-rules-port2").val());
	//
	$("#offerEditView-checkNum").empty();
	$("#offerEditView-checkNum").html($("[name=checkNum] option:selected").text())
	//
	$("#offerEditView-checkGoodStandard").empty();
	$("#offerEditView-checkGoodStandard").html($("[name=checkGoodStandard] option:selected").text());
	//
	$("#offerEditView-checkCostA").empty();
	$("#offerEditView-checkCostA").html($("[name=checkCostA]").val());
	//
	$("#offerEditView-checkCostB").empty();
	$("#offerEditView-checkCostB").html($("[name=checkCostB]").val());
	//
	$("#offerEditView-checkCostC").empty();
	$("#offerEditView-checkCostC").html($("[name=checkCostC]").val());
	//
	$("#offerEditView-checkCostD").empty();
	$("#offerEditView-checkCostD").html($("[name=checkCostD]").val());
	//
	$("#offerEditView-checkCostE").empty();
	$("#offerEditView-checkCostE").html($("[name=checkCostE]").val());
	//
	$("#offerEditView-checkCostDay").empty();
	$("#offerEditView-checkCostDay").html($("[name=checkCostDay]").val());
	//
	$("#offerEditView-checkCostF").empty();
	$("#offerEditView-checkCostF").html($("[name=checkCostF]").val());
	//
	$("#offerEditView-checkInvoiceDetail").empty();
	$("#offerEditView-checkInvoiceDetail").html($("[name=checkInvoiceDetail]").val());
	//
	$("#offerEditView-checkDescriptionOther").empty();
	$("#offerEditView-checkDescriptionOther").html($("[name=checkDescriptionOther]").val());
	
	//保证金比例
	$("#offerEditView-marginLevel").empty();
	$("#offerEditView-marginLevel").html($("[name=marginLevel]").val());
	
}

//打开交易规则
function openPricingRules(){
	var html = document.getElementById("pricingRulesFile").innerHTML;
	esteel_offer.confirmMessage(null,html,null,"关闭")
}

//渲染完组件后自定义回调
function _customRenderFinishCallBack(){
	//添加全选按钮
	var $selectAll = $("<button type='button' class='btn btn-default btn-selectAllTransactions' onclick='selectAllTransactions(this)'><i class='fa fa-square-o'></i> 全选</button>");
	$("#component-transactions>.form-group.component-InputGroupSelect>.input-group>.input-group-btn").append($selectAll);
	//动态调整宽度
	updateSelectWidth($selectAll);
}
//全选交易对象
function selectAllTransactions(allBtn){

	if($("select[name=transactions] option").length ==0){
		alertError("当前无可交易对象，请前往会员中心添加白名单成员");
		return false;
	}
	//
	if($("#component-transactions>.form-group.component-InputGroupSelect .select2-selection__rendered .select2-selection__choice.allObject").length==0){
		$("#component-transactions>.form-group.component-InputGroupSelect .select2-selection__rendered .select2-selection__choice.allObject").remove();
		//去除所有对象
		esteel_offer.select_transactions.setSelectValue();
		//添加所有对象tag
		$("#component-transactions>.form-group.component-InputGroupSelect .select2-selection__rendered").append("<li class='select2-selection__choice allObject'>所有交易对象</li>");
		$("select[name=transactions] option").prop("selected",true);
		//禁用多选
		$("#component-transactions>.form-group.component-InputGroupSelect>.input-group>.select2-container").addClass("disabled");
		$(allBtn).siblings(".btn").addClass("disabled");
		$(allBtn).html("<i class='fa fa-check-square-o'></i> 全选");
		//验证
		validateSelectSuccess($("select[name=transactions]")[0]);
	}else{
		//去除所有对象
		esteel_offer.select_transactions.setSelectValue();
		$("select[name=transactions] option").prop("selected",false);
		//启用多选
		$("#component-transactions>.form-group.component-InputGroupSelect>.input-group>.select2-container").removeClass("disabled");
		$(allBtn).siblings(".btn").removeClass("disabled");
		$(allBtn).html("<i class='fa fa-square-o'></i> 全选");
	}
	//动态调整宽度
	updateSelectWidth($(allBtn));
}
function updateSelectWidth($selectAll){
	var s2cw = $("#component-transactions>.form-group.component-InputGroupSelect>.input-group>select[name=transactions]")[0].style.width.split("px")[0];
	var saw = $selectAll.css("width").split("px")[0];
	$("#component-transactions>.form-group.component-InputGroupSelect>.input-group>.select2-container").css("width",Number(s2cw)-Number(saw));
}

//跳过交易验证
function skipOfferRules(){
	esteel_offer.stepSkipOfferRules = true;
}