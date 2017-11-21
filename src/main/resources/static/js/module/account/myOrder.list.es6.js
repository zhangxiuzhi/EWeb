/**
 *
 * //我的采购列表
 *
 * Created by wzj on 2017/6/19.
 */



/*--------------------------------------------------------------------------------------------*/
//采购状态
var stateOperationButtonArray = [
	{id: 99,
		name: "详情",
		url: systemPath + "offer/offerDetail"}
];

class ListTable extends React.Component {

	constructor(props) {
		super(props);

		this.thcols = [
			{dataField:"operationDate",dataName:"下单时间",width:200},
			{dataField:"commodityName",dataName:"品名",width:200},
			{dataField:"pricingNum",dataName:"数量",width:130},
			{dataField:"priceIndex",dataName:"合约",width:130},
			{dataField:"pricingTime",dataName:"点价/交货期"},
			{dataField:"pricingStatus",dataName:"状态",width:160},
			{dataField:"pricingStatus",dataName:"操作",width:160}
		]

		this.row0 = [''];
		this.row1 = [
			{dataField:"operationDate",dataFormat:operationDateFormat},
			{dataField:"commodityName"},
			{dataField:"offerNum",dataFormat:offerNumFormat},
			{dataField:"priceIndex",dataFormat:priceIndexFormat},
			{dataField:"offerDateStart",dataFormat:offerDateFormat},
			{dataField:"orderStatus",dataFormat:orderStatusFormat},
			{dataField:"orderStatus",dataFormat:orderOperationFormat}
		];
		this.row2 = [
			{dataField:"pricingPrice",dataFormat:pricingEmptyFormat},
			{dataField:"commodityName",dataFormat:pricingEmptyFormat},
			{dataField:"pricingNum",dataFormat:pricingNumFormat},
			{dataField:"pricingPrice",dataFormat:pricingPriceFormat},
			{dataField:"pricingTime",dataFormat:pricingTimeDateFormat},
			{dataField:"pricingStatus",dataFormat:pricingStatusFormat},
			{dataField:"pricingStatus",dataFormat:pricingEmptyFormat}
		]
		this.row3 = [
			{dataField:"pricedNum",dataFormat:pricingEmptyFormat2},
			{dataField:"pricedNum",dataFormat:pricingEmptyFormat2},
			{dataField:"pricedNum",dataFormat:pricedNumFormat_sum},
			{dataField:"pricedNum",dataFormat:averagePriceFormat_sum},
			{dataField:"pricedSumPrice",dataFormat:pricedSumPriceForm_sum},
			{dataField:"pricedNum",dataFormat:pricingEmptyFormat2},
			{dataField:"pricedNum",dataFormat:pricingEmptyFormat2}
		]

	}

	/*******************************************************************/
	ajaxRequestData(state,searchData){
		this.refs.jtable.ajaxRequestData(state,searchData);
	}

	//DOM加载完成
	componentDidMount() {

	}
	/*******************************************************************/
	render() {
		var datas = [];
		var options = {
			url:this.props.url,
			thcols:this.thcols,
			status:this.props.status,
			searchData:"",
			page:1,
			sizePerPage:5,
			nodata:{text:"您还没有下单记录"},
			class:"table-multiple",
			loadingText:{dataFormat:listLoadingIconFormat},	//自定义列表加载动画
			customRenderFinishCallBack:_customRenderFinishCallBack	//自定义渲染完成回调
		}
		return React.createElement(
			JTable,
			{ options: options, datas: datas, ref: "jtable" },
			React.createElement(JTr, { className: "sep-row", keys: this.row0, colspan: this.row2.length }),
			React.createElement(JTr, { className: "tr-th", keys: this.row1 }),
			React.createElement(JTr, { className: "child-row", keys: this.row2})
			,React.createElement(JTr, { className: "tr-summary", keys: this.row3})
		);
	}
}

//下单时间
function operationDateFormat(cell,row){
	return esteel_myOrder.formatDateTime(cell);
}
//数量
function offerNumFormat(cell,row){
	return "<span class='text-success'>"+row.offerNum+"</span> <sub class='text-gray-low'>湿吨</sub>";
}
//合约
function priceIndexFormat(cell,row){
	var preDis = row.premiumsDiscounts;
	if (parseInt(preDis) >= 0) {
		return '<span class="text-danger">' + row.priceIndex + "</span>+" + preDis;
	}
	return '<span class="text-danger">' + row.priceIndex + "</span>" + preDis;
}
//点价期
function offerDateFormat(cell,row){
	var d1 = "<div class='text-gray-low'>点价期</div>";
	if(row.offerStatus != 2 && row.offerStatus != 3){
		d1 = "<div class='text-gray-low'>交货期</div>";
	}
	return d1+esteel_myOrder.formatDate(cell)+" / "+esteel_myOrder.formatDate(row.offerDateEnd);
}
//状态
function orderStatusFormat(cell,row){
	var text = "";
	switch(cell){
		case 0:
			text = "待点价";
			break;
		case 1:
			text = "点价中";
			break;
		case 2:
			text = "待交货";
			break;
		case 3:
			text = "已完成";
			break;
	}
	var obj = {
		offerId: row.offerId
	};
	//详情按钮
	var info = "<br/><a target='_blank' class='text-info' href='javascript:void(0)' onclick=winOpen(getStatusOperationButtonUrl(99).url," + JSON.stringify(obj) + ")>详情</a>"
	return "<span class='status-tag'>"+text+"</span>"+info;
}
//操作
function orderOperationFormat(cell,row,object){
	var info="";

	//在点价期内
	if (esteel_myOrder.formatDate(row.offerDateStart) <= esteel_myOrder.formatDate(new Date()) && esteel_myOrder.formatDate(new Date()) <= esteel_myOrder.formatDate(row.offerDateEnd)){
		//点价数量<报盘数量
		if(row.pricedNum<row.offerNum){
			//点价按钮
			info += "<button type='button' class='btn btn-ghost btn-primary btn-xs' onclick=orderPricing("+row.buyId+")>点价</button>";
		}
	}
	//点价中默认展开
	var icon_export ="<a href='javascript:void(0)' class='pricingList-toggle pull-right text-info margin-right-20' onclick='collapsePriceList(this)'><i class='fa fa-angle-down fs-16 '></i> 展开</a>",
		icon_collapse = "<a href='javascript:void(0)' class='pricingList-toggle pull-right text-info margin-right-20' onclick='collapsePriceList(this)'><i class='fa fa-angle-up fs-16 '></i> 收拢</a>";
	/*if(row.orderStatus == 3){
		info += icon_collapse
	}else if(row.orderStatus > 3){
		//非点价中和待点价状态，默认收拢
		info += icon_export
	}*/

	return info+icon_export;
}

/****************************************************
点价
 *****************************/
//格式化点价价格
function pricingPriceFormat(cell,row){
	return "<span class='text-danger'>"+cell+"</span> <sub class='text-gray-low'>元</sub>";
}
//格式化点价数量
function pricingNumFormat(cell,row){
	return "<span class='text-success'>"+row.pricingNum+"</span> <sub class='text-gray-low'>湿吨</sub>";
}
//格式化点价时间
function pricingTimeDateFormat(cell,row){
	return esteel_myOrder.formatDateTime(row.pricingTime)
}

//格式化点价时间
function pricingStatusFormat(cell,row) {
	return getPricingStatus(row.pricingStatus);
}

function getPricingStatus(cell){
	if (cell==0){
		return "<span class='text-gray'>待确认</span>";
	}
	if (cell==1){
		return "<span class='text-success'>卖家确认</span>";
	}
	if (cell==2){
		return "<span class='text-success'>平台确认</span>";
	}
	if (cell==3){
		return "<span class='text-warning'>卖家拒绝</span>";
	}
	if (cell==4){
		return "<span class='text-warning'>平台拒绝</span>";
	}
}

//空格处理
function pricingEmptyFormat(){
	return "--";
}
function pricingEmptyFormat2(){
	return "";
}

//展开收拢点价列表
function collapsePriceList(btn){
	if($(btn).children("i.fa").hasClass("fa-angle-up")){
		$(btn).html("<i class='fa fa-angle-down fs-16 '></i> 展开");
	}else{
		$(btn).html("<i class='fa fa-angle-up fs-16 '></i> 收拢");
	}
	$(btn).parents("tr.tr-th").siblings("tr.child-row").toggle();
	$(btn).parents("tr.tr-th").siblings("tr.tr-summary").toggle();
	$(btn).parents("tbody").toggleClass("collapse-childRow");
}

//统计
//平均单价
function averagePriceFormat_sum(cell,row){
	var pricedPrice = (parseFloat(row.pricedSumPrice)/parseFloat(row.pricedNum)).toFixed(2);
	if(isNaN(pricedPrice)){
		pricedPrice=0;
	}
	return "<b>平均单价</b><br/><span class='text-danger'>"+pricedPrice+"</span> <sub class='text-gray-low'>元</sub>";
}
//已点价数量
function pricedNumFormat_sum(cell,row){
	return "<b>已点价数量</b><br/><span class='text-success'>"+cell+"</span> <sub class='text-gray-low'>湿吨</sub>";
}
//总金额
function pricedSumPriceForm_sum(cell,row){
	return "<b>总金额</b><br/><span class='text-danger'>"+cell+"</span> <sub class='text-gray-low'>元</sub>";
}



//根据操作描述获取按钮url地址
function getStatusOperationButtonUrl(desc) {
	var sob = stateOperationButtonArray;
	var name,
		url = "";
	for (var i in sob) {
		if (desc == sob[i].id) {
			url = sob[i].url;
			name = sob[i].name;
		}
	}
	return {name: name, url: url};
}


//自定义渲染完成回调
function _customRenderFinishCallBack(){
	if($(".status-tag").length>0){
		$(".status-tag").each(function(index,tag){
			if(tag.innerHTML == "点价中"){
				var $btn = $(tag).parents("tr.tr-th").find("a.pricingList-toggle");
				//展开收拢点价列表
				collapsePriceList($btn);
			}
		})
	}
}