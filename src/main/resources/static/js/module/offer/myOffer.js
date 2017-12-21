/**
 * Created by wzj on 2017/10/31.
 *	我的报盘
 */


function JBSFrame_myOffer() {
	JBSFrame.call(this);

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
				onChange: onTableStatusChange
			}), $myOfferStatus[0]);
		}

		//品名
		var $myOfferItemName = $("#component-myOffer-itemName");
		if ($myOfferItemName.length > 0) {
			this.selectBox_ItemName = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:JSON.parse($("#ironCommodityJson").html()),
				inputName:$myOfferItemName.attr("inputName"),
				inputValue:$myOfferItemName.attr("inputValue"),
				onChange:onTableStatusChange
			}), $myOfferItemName[0]);
		}

		//港口
		var $myOfferPort = $("#component-myOffer-port");
		if ($myOfferPort.length > 0) {
			this.selectBox_port = ReactDOM.render(React.createElement(ComponentSelectBox,{
				data:JSON.parse($("#portJson").html()),
				inputName:$myOfferPort.attr("inputName"),
				inputValue:$myOfferPort.attr("inputValue"),
				onChange:onTableStatusChange
			}), $myOfferPort[0]);
		}

		//列表
		this.table = ReactDOM.render(React.createElement(MyOfferTable,{

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

//表格条件过滤
function onTableStatusChange(){

}