/**
 * 会员中心-账号安全-修改手机
 * Created by wzj on 2017/12/5.
 */

function JBSFrame_safe_updateMobile() {

	JBSFrame.call(this);


	//初始化UI
	this.initUI = function () {
		//主菜单栏
		//当前选中发布报盘
		this.sidebar = ReactDOM.render(React.createElement(ComponentMemberSidebar,{focusNode:{name:"accountSafe",text:"账号安全"}}), document.getElementById("component-sidebar"));


	}

	//渲染表单元素
	this.renderFormElement = function(){

		//渲染步骤
		this.renderStep();
	}

	//渲染步骤
	this.renderStep = function(){

		var navListItems = $('#updateMobile-panel li'),
			wellsBtn = $(".updateForm-step-content a.step-btn");
		$('.updateForm-step-content').hide();

		wellsBtn.click(function(e){
			e.preventDefault();
			var target = $(this).attr('href');
			var $item;
			for(var i=0;i<navListItems.length;i++){
				if($(navListItems[i]).data("href") == target){
					$item = $(navListItems[i])
				}
			}
			//显示当前步骤
			navListItems.removeClass('active');
			$item.addClass('active');
			//完成上个步骤
			$item.prev().addClass("done");
			//显示当前步骤面板
			$(".updateForm-step-content").hide();
			$(target).show();
		});
		//默认显示第一个步骤
		$('#updateMobile-step-1').show();

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
		$("#router-pageCotainer").load('/view/safe/'+path+".html", function(){
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

}


/*
 //body load
 --------------------------------------------------------------------*/
var esteel_safe_updateMobile;
$(document).ready(function (e) {
	esteel_safe_updateMobile = new JBSFrame_safe_updateMobile();
	//初始化UI
	esteel_safe_updateMobile.initUI();
	//初始化路由
	esteel_safe_updateMobile.initRouter();
});
