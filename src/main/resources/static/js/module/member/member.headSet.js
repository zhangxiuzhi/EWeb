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
		console.log(offerRoutes)
		var router = Router(offerRoutes);
		router.configure({
			on: self.selectTypeTab	//切换路由后设置高亮标签
		});
		router.init('/'+$("#router-linkNode >a.selected").attr("linkNode"));//初始化页面
	}

	this.loadRouter = function(){
		var path = window.location.hash.slice(2);
		$("#router-pageCotainer").load('/view/member/'+path+".html", function(){

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
				var saveStr = result.data[0]+result.data[1];
				//赋值
				$("#fileType").val(saveStr);
			}
		}
    });
}
//保存
function confirm(){
	
}











