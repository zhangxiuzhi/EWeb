/**
 * Created by wzj on 2017/10/31.
 *	我的报盘
 */


function JBSFrame_myOffer() {
	JBSFrame.call(this);

	//列表过滤条件
	this.filter = {
		tradeMode:"-1",//类型
		createUser:"-1",//发布账号
		offerStatus:"-1",//状态
		commodityId:"-1",//品名
		portId:"-1",//港口
		offerCode:""//编号
	}
	this.sidebar = null;//侧栏菜单

	//初始化UI
	this.initUI = function () {
		//菜单选中报盘记录
		this.sidebar = ReactDOM.render(React.createElement(ComponentIronSidebar,{focusNode:{name:"myOffer",text:"报盘记录"}}), document.getElementById("component-sidebar"));

		//类型选择
		$("#router-linkNode >").click(function(){
			self.filter.tradeMode = $(this).attr("linkNode");
			reload_table();
		});

		//列表状态
		var $myOfferStatus = $("#component-myOffer-status");
		if ($myOfferStatus.length > 0) {
			this.table_status = ReactDOM.render(React.createElement(ComponentRadioBox, {
				data:JSON.parse($("#offerStatusJson").html()),
				className: "MiniTagStyle",
				value: -1,
				onChange: onTableStatusChange_status
			}), $myOfferStatus[0]);
		}

		//账号
		var $myOfferCreateUser = $("#component-myOffer-createUser");
		if ($myOfferCreateUser.length > 0) {
			this.selectBox_Account = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:JSON.parse($("#userJson").html()),
				inputName:$myOfferCreateUser.attr("inputName"),
				inputValue:$myOfferCreateUser.attr("inputValue"),
				onChange:onTableStatusChange_createUser
			}), $myOfferCreateUser[0]);
		}

		//品名
		var $myOfferItemName = $("#component-myOffer-itemName");
		if ($myOfferItemName.length > 0) {
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:JSON.parse($("#ironCommodityJson").html()),
				inputName:$myOfferItemName.attr("inputName"),
				inputValue:$myOfferItemName.attr("inputValue"),
				onChange:onTableStatusChange_commodity
			}), $myOfferItemName[0]);
		}

		//港口
		var $myOfferPort = $("#component-myOffer-port");
		if ($myOfferPort.length > 0) {
			this.selectBox_port = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:JSON.parse($("#portJson").html()),
				inputName:$myOfferPort.attr("inputName"),
				inputValue:$myOfferPort.attr("inputValue"),
				onChange:onTableStatusChange_port
			}), $myOfferPort[0]);
		}

		//默认类型和状态
		this.filter.tradeMode = "1";
		this.filter.offerStatus = -1;
		//列表
		this.table = ReactDOM.render(React.createElement(MyOfferTable,{
			searchData:this.filter
		}), document.getElementById("component-myOffer-table"));
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
});

//报盘发布账号条件过滤
function onTableStatusChange_createUser(data){
	esteel_myOffer.filter.createUser = data.value;
	reload_table();
}
//报盘状态条件过滤
function onTableStatusChange_status(data){
	esteel_myOffer.filter.offerStatus = data.value;
	reload_table();
}

//品名条件过滤
function onTableStatusChange_commodity(data){
	esteel_myOffer.filter.commodityId = data.value;
	reload_table();
}

//港口条件过滤
function onTableStatusChange_port(data){
	esteel_myOffer.filter.portId = data.value;
	reload_table();
}
//编号条件过滤
function onTableStatusChange_id(){
	esteel_myOffer.filter.offerCode = $("#myoffer-filter-id").val();
	reload_table();
}

//重新加载列表
function reload_table(){
	esteel_myOffer.table.reloadTable({
		tradeMode:"1",
		createUser:esteel_myOffer.filter.createUser,
		offerStatus:esteel_myOffer.filter.offerStatus,
		commodityId:esteel_myOffer.filter.commodityId,
		portId:esteel_myOffer.filter.portId,
		offerCode:esteel_myOffer.filter.offerCode
	});
}

function onTablePutShelves(offerCode) {
	var _url = "/offer/iron/putShelves/"+offerCode;
	esteel_myOffer.ajaxRequest({
	    	url:_url,
	        data:{}
	    },  function (result) {
	    	alert(result.msg);
	    });
}
function onTableOffShelves(offerCode) {
	var _url = "/offer/iron/offShelves/"+offerCode;
	esteel_myOffer.ajaxRequest({
	    	url:_url,
	        data:{}
	    },  function (result) {
	    	alert(result.msg);
	    });
}
function onTableDelete(_offerCode) {
	var _url = "/offer/iron/delete/"+offerCode;
	esteel_myOffer.ajaxRequest({
	    	url:_url,
	        data:{}
	    },  function (result) {
	    	alert(result.msg);
	    });
}