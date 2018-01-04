/**
 * 会员中心
 * Created by wzj on 2017/11/30.
 */
function JBSFrame_member_headSet() {

	JBSFrame.call(this);


	//初始化UI
	this.initUI = function () {
		//主菜单栏
		//当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(ComponentMemberSidebar,{
			focusNode:{name:"userInfo",text:"个人资料"}
		}), document.getElementById("component-sidebar"));


	}

	this.renderFormElement = function(){
		//初始化图片裁剪
		esteel_member_headSet.initCutter();
	}

	/*---------------------------------------------------------------------------------------------------------------------------*/

	//初始化路由
	this.initRouter = function () {

		//页面路由，在页面设置
		var offerRoutes = {}
		$("#component-sidebar .nav-list a").each(function(idx,elem){
			var href = elem.getAttribute("href");
			var rr = elem.getAttribute("name");
			offerRoutes[rr] = self.loadRouter
		})
		$("#router-linkNode a").each(function(idx,elem){
			var rr = elem.getAttribute("linkNode")
			offerRoutes[rr] = self.loadRouter
		})
		var router = Router(offerRoutes);
		router.configure({
			on: self.selectTypeTab	//切换路由后设置高亮标签
		});
		router.init('/'+$("#router-linkNode >a.selected").attr("linkNode"));//初始化页面
	}

	this.loadRouter = function(){
		var path = window.location.hash.slice(2);
		$("#router-pageCotainer").load('/view/member/'+path+".html", function(){
			self.renderFormElement();//渲染表单元素
		});	//加载静态文件
	}

	//切换路由后设置高亮标签
	this.selectTypeTab = function(){
		var path = window.location.hash.slice(2);
		$("#router-linkNode >a").removeClass("selected");
		$("#router-linkNode >a[linkNode='"+path+"']").addClass("selected");
	}

	/*---------------------------------------------------------------------------------------------------------------------------*/
	var self = this;

	//裁剪照片
	this.initCutter = function(){
		this.cutter = new jQuery.UtrialAvatarCutter({
				//主图片所在容器ID
				content : "picture_original",
				//缩略图配置,ID:所在容器ID;width,height:缩略图大小
				purviews : [
					{id:"picture_100",width:100,height:100},
					{id:"picture_50",width:50,height:50}
				],
				boxWidth:600,
				boxHeight:300,
				//选择器默认大小
				selector : {width:200,height:200},
				onChange: function (result){
					for(var c in result){
						$("#cutter-"+c).val(result[c]);
					}
				}
		});
	}
	//添加图片
	this.setupImage = function(result){
		var saveStr = result.data[0];
		var type = result.data[1];
		var url = "/user/export/"+saveStr+"/"+type+"/html;";
		$("#picture_original img").attr("src",url);
		var $img = $("#picture_original img");
		$img[0].onload= function(){
			var size = getImgNaturalDimensions($img[0])
			$img.css({
				width:size[0] > 1000 ?  size[0] / 2 * 0.3 : size[0] * 0.3,
				height:size[1]  > 1000 ?  size[1] / 2 * 0.3 : size[1] * 0.3
			});
			esteel_member_headSet.cutter.init();
		}
	}
}


/*
 //body load
 --------------------------------------------------------------------*/
var esteel_member_headSet;
$(document).ready(function (e) {
	esteel_member_headSet = new JBSFrame_member_headSet();
	//初始化UI
	esteel_member_headSet.initUI();
	//初始化路由
	esteel_member_headSet.initRouter();

});

//文件上传
function upload(elem){
	var fileId =elem.id; 
	$.ajaxFileUpload({
		url: '/user/uploadFile',
		secureuri: false,
		fileElementId: elem.id,// file标签的id
		dataType: 'json',
		beforeSend: function (xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function (result) {
			//返回文件id
			if(result.data!=null){
				//图片回写
				//reloadUploadImage(fileId,result);
				//保存数据库的字符串
				var saveStr = result.data[0];
				var type = result.data[1];
				//赋值
				$("#filestr").val(saveStr);
				$("#filetype").val(type);

				//设置图片
				esteel_member_headSet.setupImage(result);
			}
		}
    });
}


//普通会员操作
function confirm(){
	//参数
	var rank = 1; //表示操作为普通会员
	var fileId = $("#filestr"); //文件类型
	var imgType = $("#filetype"); //文件字符串
	
	//裁剪之后的参数值
	var x = null;
	var y = null;
	var width = null;
	var height = null;
	
	esteel_member_headSet.ajaxRequest({
		url : "/user/imageCut",
		data : {
			rank : 1,
			fileId : 1,
			imgType : 1,
			x : $("#cutter-x").val(),
			y : $("#cutter-y").val(),
			width : $("#cutter-w").val(),
			height : $("#cutter-y").val()
		}
	}, function(data,msg) {
		if(msg=="1"){
			//五秒倒计时
			
			window.location.href="/member/userInfo"; 
		}else{
			alert("保存失败，请重试");
		}
	});
}








function getImgNaturalDimensions(img, callback) {
	var nWidth, nHeight
	if (img.naturalWidth) { // 现代浏览器
		nWidth = img.naturalWidth
		nHeight = img.naturalHeight
	} else { // IE6/7/8
		var imgae = new Image()
		image.src = img.src
		image.onload = function() {
			callback(image.width, image.height)
		}
	}
	return [nWidth, nHeight]
}