/**
 * 我的报盘
 * Created by wzj on 2017/6/7.
 */

function JBSFrame_myOffer(){

	JBSFrame.call(this);

	//列表
	this.list = null;
	//列表状态筛选
	this.list_state = null;

	//初始化UI
	this.initUI = function(){

		//顶部菜单栏
		//当前选中交易大厅
		//ReactDOM.render(<HeaderMenu  focusItem="我的报盘"/>,document.getElementById("component-headerMenu"));
		ReactDOM.render(React.createElement(HeaderMenu, { focusItem: "我的报盘" }), document.getElementById("component-headerMenu"));

		//列表
		//this.list = ReactDOM.render(<ListTable url="offer/myOfferList" />,document.getElementById("component-table"));
		this.list = ReactDOM.render(React.createElement(ListTable, { url: "offer/myOfferList" }), document.getElementById("component-table"));


        //加载品名列表
		this.load_tbPricingCommodityList();

		//加载港口列表
		this.load_portList();

		//加载状态列表
		this.load_stateList();

		//点价开始时间
		$("#offer-pricingStartDate").on("dp.change",function(evt){
			$("#offer-pricingEndDate").datetimepicker("minDate",evt.date);
		});
		//点价结束时间
		$("#offer-pricingEndDate").on("dp.change",function(evt){
			$("#offer-pricingStartDate").datetimepicker("maxDate",evt.date);
		});

		//搜索过滤
		$("#btn-searchFilter").click(function(){
			var searchData = [];
			var state = null;
			var serialize = $("#form-filter").serialize();
			this.list.ajaxRequestData(state,serialize);
		}.bind(this));
		//重置过滤
		$("#btn-resetFilter").click(function(){
			$("#form-filter")[0].reset();
			this.list.ajaxRequestData(null,null);
		}.bind(this));
	}


	//加载港口列表
	this.load_portList = function(){
		esteel_myOffer.ajaxRequest({
			url:"pricing/pricingPort.do"
		},function(data,msg){
			//港口
			var $port = $("#component-shipmentportKey");
			//ReactDOM.render(<DropdownList data={data} class="form-group" formLabel={$port.attr("label")} formName={$port.attr("name")} rightAddonIcon="fa-plus"/>,$port[0]);
			ReactDOM.render(React.createElement(DropdownList, { data: data, "class": "form-group", formLabel: $port.attr("label"), formName: $port.attr("name"), rightAddonIcon: "fa-plus" }), $port[0]);

		})
	}

	//加载状态列表
	this.load_stateList = function(){
		//港口
		var $port = $("#component-offerStatus");
		var data = [
            {value: 0, text: "草稿"},
            {value: 1, text: "已发布"},
            {value: 2, text: "已成交"},
            {value: 3, text: "点价中"},
            {value: 4, text: "已下架"},
            {value: 5, text: "点价完成"}
		]
		//this.list_state = ReactDOM.render(<DropdownList data={data} class="form-group" formLabel={$port.attr("label")} formName={$port.attr("name")} rightAddonIcon="fa-plus"/>,$port[0]);
		this.list_state = ReactDOM.render(React.createElement(DropdownList, { data: data, "class": "form-group", formLabel: $port.attr("label"), formName: $port.attr("name"), rightAddonIcon: "fa-plus" }), $port[0]);
	}

	//加载品名列表
	this.load_tbPricingCommodityList = function() {
		esteel_myOffer.ajaxRequest({
			url: "pricing/pricingCommodity.do"
		}, function (data, msg) {
			//品名
			$("#component-tbPricingCommodity").autocomplete({
				source: function (request, response) {
					var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
					response($.grep(data, function (item) {
						return matcher.test(item.text) ||
							matcher.test(item.value);
					}));
				},
				select: function (event, ui) {
					$("#component-tbPricingCommodity").val(ui.item.text);
					$("input[name='commodityKey']").val(ui.item.value);
					return false;
				}
			}).data("ui-autocomplete")._renderItem = function (ul, item) {
				return $("<li>")
				.data("item.autocomplete", item)
				.append($("<a></a>").text(item.text))
				.appendTo(ul);
			};
		}.bind(this));
	}
}



/*
 //body load
 --------------------------------------------------------------------*/
var esteel_myOffer;
$(document).ready(function(e) {
	esteel_myOffer = new JBSFrame_myOffer();
	//初始化UI
	esteel_myOffer.initUI();
});

//更改报盘状态
function changeState(offerId,status) {
	var msg = "";
	//发布
	if(status == 1){
		msg = "确认发布？";
	}
	//撤回
	if(status == 0){
		msg = "确认撤回？";
	}
	//下架
	if(status == 4){
		msg = "确认下架？";
	}
	esteel_myOffer.confirm(null,msg,function(){
		esteel_myOffer.ajaxRequest({
			url: "offer/changStatus",
			data:{
				offerId:offerId,
				status:status
			}
		}, function (data, msg) {
			var serialize = $("#form-filter").serialize();
			esteel_myOffer.list.ajaxRequestData(null, serialize);
		});
	});

}
//删除报盘
function deleteOffer(offerId,url){
	esteel_myOffer.confirm(null,"是否删除当前报盘",function(){
		esteel_myOffer.ajaxRequest({
			url: url
		}, function (data, msg) {
			alert("删除成功")
			var serialize = $("#form-filter").serialize();
			esteel_myOffer.list.ajaxRequestData(null, serialize);
		});
	});

}