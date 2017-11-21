/**
 *
 * 交易规则
 * Created by wzj on 2017/8/28.
 */

function JBSFrame_appRule() {
	JBSFrame.call(this);

	this.initUI = function(){
		//顶部菜单栏
		ReactDOM.render(React.createElement(HeaderMenu, { focusItem: "" }), document.getElementById("component-headerMenu"));
	}

}

/*
 //body load
 --------------------------------------------------------------------*/
var esteel_appRule;
$(document).ready(function(){
	esteel_appRule = new JBSFrame_appRule();
	esteel_appRule.initUI();
});

