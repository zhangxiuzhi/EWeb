/**
 * 我的报盘,查看详情
 * Created by wzj on 2017/6/7.
 */

function JBSFrame_myOfferView(){

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




		var checkModeWay = ["汽车车板","火车车板","二程船舱","底堆场"];
		$("#offerEditView-checkModeWay").html(checkModeWay[$("#offerEditView-checkModeWay").data("value")]);

		var checkNum = ["过磅","港航交接","轨道衡","皮带秤"];
		$("#offerEditView-checkNum").html(checkNum[$("#offerEditView-checkNum").data("value")]);

		var checkGoodStandard = ["装港指标","CIQ指标"];
		$("#offerEditView-checkGoodStandard").html(checkGoodStandard[$("#offerEditView-checkGoodStandard").data("value")]);


	}

}



/*
 //body load
 --------------------------------------------------------------------*/
var esteel_myOfferView;
$(document).ready(function(e) {
	esteel_myOfferView = new JBSFrame_myOfferView();
	//初始化UI
	esteel_myOfferView.initUI();
});