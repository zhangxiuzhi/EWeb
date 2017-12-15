/**
 *
 * 框架类
 * Created by wzj on 2017/4/10.
 */
var jbsframe;
$(document).ready(function(){
	jbsframe = new JBSFrame();
	jbsframe.tooltip();
	jbsframe.navbarToggle();//打开移动端的菜单

	//jbsframe.hoverNotificationMsgScroll();//鼠标移动到消息栏时出现滚动条，滚动消息
});
function alert(message){
	jbsframe.alert(message);
}
function alertError(message){
	jbsframe.alertError(message);
}
function notificationMsg(title, message, callback, callback_cancel){
	jbsframe.notificationMsg(title, message,callback, callback_cancel);
}

Growl.prototype.content = function() {
	return "<div class='" + this.settings.namespace + "-close'>" + this.settings.close + "</div>\n<div class='" + this.settings.namespace + "-message'><span class='fa fa-info-circle'></span>" + this.settings.message + "</div>";
};

var datetimepicker_icon = {
	time: 'fa fa-clock-o',
	date: 'fa fa-calendar',
	up: 'fa fa-chevron-up',
	down: 'fa fa-chevron-down',
	previous: 'fa fa-chevron-left',
	next: 'fa fa-chevron-right',
	today: 'glyphicon glyphicon-screenshot',
	clear: 'fa fa-trash',
	close: 'fa fa-remove'
}
var datetimepicker_tooltips = {
	today: '今天',
	clear: '清除选择',
	close: '关闭',
	selectMonth: '选择月份',
	prevMonth: '上个月',
	nextMonth: '下个月',
	selectYear: '选择年份',
	prevYear: '上一年',
	nextYear: '下一年',
	selectDecade: 'Select Decade',
	prevDecade: 'Previous Decade',
	nextDecade: 'Next Decade',
	prevCentury: 'Previous Century',
	nextCentury: 'Next Century',
	pickHour: '选择小时',
	incrementHour: '增加一小时',
	decrementHour: '减少一小时',
	pickMinute: '选择分钟',
	incrementMinute: '增加一分钟',
	decrementMinute: '减少一分钟',
	pickSecond: '选择秒',
	incrementSecond: '增加一秒',
	decrementSecond: '减少一秒',
	togglePeriod: 'Toggle Period',
	selectTime: '选择时间'
}
function JBSFrame() {
	var self = this;

	//移动端菜单展示
	this.navbarToggle = function(){
		$(".navbar-toggle").on("click",function(){
			this.openNavbar4mobile();
		}.bind(this));
		this.navbarOverlay();//移动端菜单展示的遮照控制
	}

	//移动端菜单展示的遮照控制
	this.navbarOverlay = function(){
		$(".navbar-overlay").on("click",function(){
			this.openNavbar4mobile();
		}.bind(this));
	}

	//打开移动端的菜单
	this.openNavbar4mobile = function(){
		if($("body").hasClass("navbar-left-cover")){
			$("body").removeClass("navbar-left-cover");
		}else{
			$("body").addClass("navbar-left-cover");
		}
	}

	//tooltip
	this.tooltip = function(){
		$(".jTooltip").data("toggle","tooltip");
		$(".jTooltip").attr("title","Some tooltip text!");
	}

	//格式化时间
	this.formatDateTime = function(timestamp){
		return new Date(Number(timestamp)).pattern("yyyy-MM-dd HH:mm:ss");
	}
	this.formatDate = function(timestamp){
		return new Date(Number(timestamp)).pattern("yyyy-MM-dd");
	}

	//剩余时间
	this.remainingTime = function(end){
		var newTime = new Date(end) - new Date().getTime();
		//计算天数
		var days=Math.floor(newTime/(24*3600*1000));
		//计算天数后剩余的毫秒数
		var leave1=newTime%(24*3600*1000);
		var hours=Math.floor(leave1/(3600*1000));
		//计算小时数后剩余的毫秒数
		var leave2=leave1%(3600*1000);
		var minutes=Math.floor(leave2/(60*1000));
		//计算分钟数后剩余的毫秒数
		var leave3=leave2%(60*1000);
		var seconds=Math.round(leave3/1000);

		if(days ==0){
			return "还剩"+hours+"小时"+minutes+"分钟";
		}else{
			return "还剩"+days+"天"+hours+"小时"+minutes+"分钟";
		}
	}

	//所有含有datetimepicker元素生成时间控件
	this.renderDatetimepicker =  function(){
		$(".datetimepicker").each(function(index,element){
			var _format = $(element).hasClass("notime")? 'YYYY-MM-DD':'YYYY-MM-DD HH:mm:ss';
			_format = $(element).hasClass("noday")? 'YYYY-MM':'YYYY-MM-DD';

			$(element).datetimepicker({
				locale: 'zh-cn',
				format: _format,//日期的格式
				showClose:false,
				icons: {
					time: 'fa fa-clock-o',
					date: 'fa fa-calendar',
					up: 'fa fa-chevron-up',
					down: 'fa fa-chevron-down',
					previous: 'fa fa-chevron-left',
					next: 'fa fa-chevron-right',
					today: 'glyphicon glyphicon-screenshot',
					clear: 'fa fa-trash',
					close: 'fa fa-remove'
				},
				tooltips: {
					today: '今天',
					clear: '清除选择',
					close: '关闭',
					selectMonth: '选择月份',
					prevMonth: '上个月',
					nextMonth: '下个月',
					selectYear: '选择年份',
					prevYear: '上一年',
					nextYear: '下一年',
					selectDecade: 'Select Decade',
					prevDecade: 'Previous Decade',
					nextDecade: 'Next Decade',
					prevCentury: 'Previous Century',
					nextCentury: 'Next Century',
					pickHour: '选择小时',
					incrementHour: '增加一小时',
					decrementHour: '减少一小时',
					pickMinute: '选择分钟',
					incrementMinute: '增加一分钟',
					decrementMinute: '减少一分钟',
					pickSecond: '选择秒',
					incrementSecond: '增加一秒',
					decrementSecond: '减少一秒',
					togglePeriod: 'Toggle Period',
					selectTime: '选择时间'
				}
			});
			//如果有日历按钮
			if($(element).siblings(".input-group-btn").length>0){
				var btn = $(element).siblings(".input-group-btn");
				btn.on("click",function(){
					$(element).datetimepicker('show');
				});
			}
		});
	}

	//钱币控制
	// 保留小数点后两位，小数点前每三位数字之前增加逗号分隔
	this.renderMoneyMask = function(){
		$(".mask-money").each(function(index,element) {
			//$(element).mask('000,000,000.00', {reverse: false});
			/*$(element).inputmask({
				alias: "currency"
			});*/
			$(element).inputmask({
				'alias': 'numeric',
				'groupSeparator': ',',
				'autoGroup': true,
				'digits': 2,
				'digitsOptional': false,
				'prefix': ' ',
				'placeholder': '0'
			});
			if($(element).hasClass("mask-money-special")){
				$(element).change(function(e) {
					var value = this.value;
					value = value.replace("-","");
					var v1 = value.split(".")[0];
					var v2 = value.split(".")[1];
					value = v1.split(",");
					var nvalue = 0;
					for(var i=0;i<value.length;i++){
						nvalue = nvalue+value[i];
					}
					if(nvalue.length>4){
						nvalue = nvalue.substr(-4)
					}
					this.value = nvalue+"."+v2;
				});
			}
		});
		//$(element).val($(element).val().replace(/[\-\d]|[\-\d\.\d]/g,''));
	}

	//数量控制
	this.renderNumberMask = function(){
		$(".mask-number").inputmask("decimal", {groupSeparator:"",autoGroup: true});
	}


	//系统提示框
	this.alert = function(msg,callback_confirm){
		if(window.document.body.scrollTop == 0){
			$.growl({ message: msg,location:"top top115",size:"large",style:"default",
				delayOnHover:false,fixed:false});
		}else{
			$.growl({ message: msg,location:"top",size:"large",style:"default",
				delayOnHover:false,fixed:false});
		}
		//确定事件
		function onConfirm(){
			if(callback_confirm){
				callback_confirm();
			}
		}
		//浏览器滚动条控制消息位置
		$(window).scroll(function(){
			if(window.document.body.scrollTop == 0){
				$("#growls.top.error").addClass("top115");
			}else{
				$("#growls.top.error").removeClass("top115");
			}
		});
	}
	this.alertError = function(msg){
		if(window.document.body.scrollTop == 0){
			$.growl({ message: msg,location:"top top115 error",size:"large",style:"error",
				delayOnHover:false,fixed:false});
		}else{
			$.growl({ message: msg,location:"top error",size:"large",style:"error",
				delayOnHover:false,fixed:false});
		}
		//浏览器滚动条控制消息位置
		$(window).scroll(function(){
			if(window.document.body.scrollTop == 0){
				$("#growls.top.error").addClass("top115");
			}else{
				$("#growls.top.error").removeClass("top115");
			}
		});
	}

	//右下角消息提示
	this.notificationMsg = function(title,msg,callback,callback_cancel){
		var notification = $.notify({
			title: title,
			message:msg,
			//icon:"<img src='"+systemPath+"images/point.png'/>",
			icon:"<i class='fa fa-envelope-o'></i>",
			//yes: "<i class='fa fa-check-circle'></i> 确定",
			yes: "<i class='fa fa-angle-right'></i> 前往查看"
			//,no:"<i class='fa fa-minus-circle rotate45'></i> 取消"
		}, {
			style: 'notification2',//callback_cancel==null? 'notification2' : 'notification',
			autoHide: true,
			autoHideDelay: 10000,
			clickToHide: false,
			gap:20
		});

		//右下角通知关闭
		$(".notifyjs-notification-base, .notifyjs-notification2-base").each(function(index,elem){
			$(elem).find(".no").on("click",function(){
				$(this).trigger('notify-hide');
				setTimeout(function(){
					if(callback_cancel){callback_cancel(notification);}
				},800)
			});
		});
		//右下角通知关闭
		$(window).keydown(function(e){
			if(e.keyCode == 27){	//键盘ESC
				$(".notifyjs-notification-base .close").trigger('notify-hide');
				$(".notifyjs-notification2-base .close").trigger('notify-hide');
			}
		});
		$(".notifyjs-notification-base, .notifyjs-notification2-base").each(function(index,elem) {
			$(elem).find(".close").on("click", function () {
				$(this).trigger('notify-hide');
			});
		});
		//右下角通知确定
		$(".notifyjs-notification-base, .notifyjs-notification2-base").each(function(index,elem) {
			$(elem).find(".yes").on("click", function () {
				$(this).trigger('notify-hide');
				setTimeout(function() {
					if (callback) {callback(notification);}
				},800);
			});
		});
	}

	//系统确认框
	this.confirm = function(title,message,callback_confirm){
		$("#container-modal").remove();
		$("body").append('<div id="container-modal"></div>');

		//ReactDOM.render(<Modal title={title} confirm="确定" cancel="取消" onConfirm={onConfirm}>{message}</Modal>,document.getElementById("container-modal"));

		ReactDOM.render(React.createElement(
			Modal,
			{ title: title, confirm: "确定", cancel: "取消", onConfirm: onConfirm },
			message
		), document.getElementById("container-modal"));

		//确定事件
		function onConfirm(){
			if(callback_confirm){
				callback_confirm();
			}
		}
	}
	//系统确认框
	this.confirm3 = function(message,confirmBtn1,confirmBtn2,cancelBtn,callback1,callback2,callback_cancel){
		$("#container-modal").remove();
		$("body").append('<div id="container-modal"></div>');

		//ReactDOM.render(<Modal multipleBtn={true} title={null} confirm1={confirmBtn1} confirm2={confirmBtn2} cancel={cancelBtn} onConfirm1={onConfirm1} onConfirm2={onConfirm2}>{message}</Modal>,document.getElementById("container-modal"));

		ReactDOM.render(React.createElement(
			Modal,
			{ multipleBtn: true, title: null, confirm1: confirmBtn1, confirm2: confirmBtn2, cancel: cancelBtn, onConfirm1: onConfirm1, onConfirm2: onConfirm2 },
			message
		), document.getElementById("container-modal"));

		//确定事件
		function onConfirm1(){
			if(callback1){
				callback1();
			}
		}
		function onConfirm2(){
			if(callback2){
				callback2();
			}
		}
	}

	//系统自定义内容确认框
	this.confirmMessage = function(title,message,confirmBtn,cancelBtn,callback_confirm,callback_cancel){
		$("#container-modal").remove();
		$("body").append('<div id="container-modal"></div>');

		var html = null;
		html = $(message);

		//ReactDOM.render(<Modal title={title} confirm={confirmBtn} cancel={cancelBtn} onConfirm={onConfirm} onCancel={onCancel}>{message}</Modal>,document.getElementById("container-modal"));

		ReactDOM.render(React.createElement(
			Modal,
			{ title: title, confirm: confirmBtn, cancel: cancelBtn, onConfirm: onConfirm, onCancel: onCancel },
			message
		), document.getElementById("container-modal"));

		//确定事件
		function onConfirm(){
			if(callback_confirm){
				callback_confirm();
			}
		}
		function onCancel(){
			if(callback_cancel){
				callback_cancel();
			}
		}
	}

	//生成数据表
	this.renderRTable = function(elementId){
		//ReactDOM.render(<RTable />,document.getElementById(elementId));
	}


	//ajax请求
	this.ajaxRequest = function(config, callback,errCallback){
		var header = $("meta[name='_csrf_header']").attr("content");
		var token = $("meta[name='_csrf']").attr("content");
		var cfg = $.extend({

			url:config.url,
			method:config.method == undefined ? 'post' : config.method,
			param:config.data,
			dataType:config.dataType == undefined ? 'json' : config.dataType

		},config);

		$.ajax({
			url:cfg.url,
			type:cfg.method,
			data:cfg.param,
			global:false,//不触发全局ajax事件
			dataType:cfg.dataType,
			//async: false,
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success:function(msg,str,response){

				//增加对返回消息的处理
				if(response.statusText == "OK" || response.statusText == "success"){

					var result;
					if((cfg.dataType).toLowerCase() == "html"){
						callback(msg);
						return false;
					}
					var result = eval("("+response.responseText+")");
					if(str == "success"){
						if(result.success==true || result.success=="true"){
							if(callback){
								callback(result.data,result.msg);
							}
						}else{
							if(errCallback){
								errCallback(result.data,result.msg);
							}else{
								if(typeof(esteel_main) =="object"){
									//alertError(result.msg);
								}
								alertError(result.msg);
							}
						}
					}else{
						if(errCallback){
							errCallback(result.data,result.msg);
						}else{
							if(typeof(esteel_main) =="object"){
								//alertError(result.msg);
							}
						}
					}
				}else{
					if(typeof(esteel_main) =="object"){
						//alertError(result.msg);
					}
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {
				//console.log(XMLHttpRequest, textStatus, errorThrown)
				alertError(textStatus)
			}
		});

	}


	//元素block
	this.blockElement = function(elementId, message){
		var $elem;
		if(elementId == "body"){
			$elem = $("body");
		}else{
			$elem = $("#"+elementId);
		}
		$elem.block({
			message: '<h4>'+message+'</h4>',
			css: {
				border: 'none',
				backgroundColor:'transparent',
				color:'#4B4C4D',
			},
			overlayCSS:{
				backgroundColor:"#f5f5f5",
				opacity:1,
			}
		})
	}
	this.unblockElement = function(elementId){
		$("#"+elementId).unblock();
	}

	//鼠标移动到消息栏时出现滚动条，滚动消息
	this.hoverNotificationMsgScroll = function(){
		$("body").mousemove(function(evt){
			if($(evt.target).parents(".notifyjs-corner").length>0){
				console.log($(evt.target).parents(".notifyjs-corner"))
				$("body").css("overflow","hidden");
				this.blockElement("body");
				/*$(".notifyjs-corner").css({
					"height":$(".notifyjs-corner").height()>$("body").height()?$("body").height():"auto",
				 	"overflow-x":"hidden",
				 	"overflow-y":"auto"
				 });*/
			}else{
				/*
				$("body").css("overflow","auto");
				$(".notifyjs-corner").css({
					"height":"auto",
					"overflow-x":"hidden",
					"overflow-y":"hidden"
				});
				*/
			}
		}.bind(this))
	}

	//插入错误提示气泡
	this.insertErrorBubble = function(elementId,errorText){
		var $element = $("#"+elementId);
		$element.next(".validetta-bubble").remove();

		var pos, W = 0, H = 0;
		var $bubble = $("<div class='validetta-bubble validetta-bubble--bottom'></div>");
		$bubble.html(errorText);

		//组件下拉
		if($element.parents(".react-selectbox").length>0) {

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

	}
}


$.notify.addStyle('notification', {
	html:
	"<div>" +
		"<div class='clearfix'>" +
			"<div class='icon' data-notify-html='icon'><i class='fa fa-hand-o-up'></i></div>"+
			"<div class='content'>"+
				"<div class='title' data-notify-html='title'></div>" +
				"<div class='message' data-notify-html='message'></div>" +
			"</div>"+
			"<div class='close'><i class='fa fa-remove'></i></div>"+
		"</div>"+
		"<div class='clearfix'>" +
			"<div class='buttons'>" +
			"<button class='btn btn-default no' data-notify-html='no'>取消</button>" +
			"<button class='btn btn-primary yes' data-notify-html='yes'>确定</button>" +
			"</div>" +
		"</div>" +
	"</div>"
});
$.notify.addStyle('notification2', {
	html:
	"<div>" +
	"<div class='clearfix'>" +
	"<div class='icon hidden' data-notify-html='icon'><i class='fa fa-hand-o-up'></i></div>"+
	"<div class='content'>"+
	"<div class='title hidden' data-notify-html='title'></div>" +
	"<div class='message' data-notify-html='message'></div>" +
	"</div>"+
	"<div class='close'><i class='fa fa-remove'></i></div>"+
	"</div>"+
	"<div class='clearfix'>" +
	"<div class='buttons'>" +
	//"<button class='btn btn-primary yes' data-notify-html='yes'>确定</button>" +
	"<button class='btn btn-info btn-block yes' data-notify-html='yes'>确定</button>" +
	"</div>" +
	"</div>" +
	"</div>"
});

//打开新窗口页面的处理
function winOpen(url,params){
    var postForm = $("#hiddenPostForm");
    postForm.empty();
    for (var x in params) {
        postForm.append($("<input>",{type:"hidden",value:params[x],name:x,id:"hiddenPostForm_id_"+x}));
    }
    var _parameter = $("meta[name='_csrf_parameter']").attr("content");
    var _token = $("meta[name='_csrf']").attr("content");

    postForm.append($("<input>",{type:"hidden",value:_token,name:_parameter,id:"hiddenPostForm_id_"+_token}));
    postForm.attr("method","post");
    postForm.attr("action",url);
    postForm.attr("target","_blank");
    postForm.submit();
    return ;
}

//列表加载动画
function listLoadingIconFormat(){
	return "<img src='"+systemPath+"images/731.gif'/><br/>读取数据中";
}

//千分位显示
function comdify(n){
	n=n.toString();
    var re=/\d{1,3}(?=(\d{3})+$)/g;
    var n1=n.replace(/^(\d+)((\.\d+)?)$/,function(s,s1,s2){return s1.replace(re,"$&,")+s2;});
    return n1;
}