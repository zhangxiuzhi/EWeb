/**
 * Created by wzj on 2017/10/31.
 *	我的报盘
 */


function JBSFrame_myOffer() {
	JBSFrame.call(this);

	//列表过滤条件
	this.filter = {
		offerStatus:"",
		commodityId:"",
		portId:""
	}
	this.sidebar = null;//侧栏菜单

	//初始化UI
	this.initUI = function () {
		//菜单选中报盘记录
		this.sidebar = ReactDOM.render(React.createElement(ComponentIronSidebar,{focusNode:{name:"myOffer",text:"报盘记录"}}), document.getElementById("component-sidebar"));

		//列表状态
		var $myOfferStatus = $("#component-myOffer-status");
		if ($myOfferStatus.length > 0) {
			this.table_status = ReactDOM.render(React.createElement(ComponentRadioBox, {
				data:JSON.parse($("#offerStatusJson").html()),
				className: "MiniTagStyle",
				value: "all",
				onChange: onTableStatusChange_status
			}), $myOfferStatus[0]);
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

//报盘状态条件过滤
function onTableStatusChange_status(data){
	steel_myOffer.filter.offerStatus = data.value;
	
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


function reload_table(){
	esteel_myOffer.table.reloadTable({
		offerStatus:esteel_myOffer.filter.offerStatus,
		commodityId:esteel_myOffer.filter.commodityId,
		portId:esteel_myOffer.filter.portId
	});
}