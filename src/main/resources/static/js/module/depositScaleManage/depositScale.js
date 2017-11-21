/**报盘押金管理-列表
 * Created by wzj on 2017/6/7.
 */

function JBSFrame_index() {

    JBSFrame.call(this);

    //列表
    this.list = null;
    this.status = "";	//状态
    this.orderStatusCollection = [];//状态集合
    this.filter = "";	//过滤
    this.portArray = [];

    //初始化UI
    this.initUI = function () {
    var offerid=$("#component-offerId").val();
        //列表
        this.list = ReactDOM.render(React.createElement(ListTable, { url: "admin/depositScale/DepositScaleList?offerid="+offerid }), document.getElementById("component-table"));
        //加载客户列表
        this.load_cuestomerList();
        //添加
        $("#btn-addDepositScale").click(function () {
            var searchData = [];
            var serialize = $("#addform-filter").serialize();//表单数据
            //提交表单
           this.submitForm(serialize)
        }.bind(this));
        //提交表单添加
        this.submitForm = function(form){
        	this.ajaxRequest({
        		url:"admin/depositScale/add",
        		data:form
        	},function(data,msg){
        		//刷新列表
        		esteel_index.list.ajaxRequestData(null, null);
        	},function(data,msg){
        		alert(msg);
        	})
        }
        //搜索过滤
        $("#btn-searchFilter").click(function () {
            var searchData = [];
            var serialize = $("#form-filter").serialize();
            this.list.ajaxRequestData(null, serialize);
        }.bind(this));
        //重置过滤
        $("#btn-resetFilter").click(function () {
            $("#form-filter")[0].reset();
            this.list.ajaxRequestData(null, null);
        }.bind(this));
    }
    //加载客户列表
    this.load_cuestomerList = function() {
        esteel_index.ajaxRequest({
            url: "admin/depositScale/findCustomer?offerid="+$("#component-offerId").val(),
        }, function (data, msg) {
            var $customer = $("#addcomponent-customerId");
            ReactDOM.render(React.createElement(DropdownList, { data: data, "class": "form-group", formLabel: $customer.attr("label"), formName: $customer.attr("name"), rightAddonIcon: "fa-plus" }), $customer[0]);

        })
    }
}


/*
 //body load
 --------------------------------------------------------------------*/
var esteel_index;
$(document).ready(function (e) {
    esteel_index = new JBSFrame_index();
    //初始化UI
    esteel_index.initUI();
});

//删除
function deleteDepositScale(url) {

      esteel_index.confirm(null, "是否删除？", function () {
          esteel_index.ajaxRequest({
              url: url
          }, function (data, msg) {
        		//刷新列表
      		esteel_index.list.ajaxRequestData(null, null);
          })
      })
}
//修改
function updateDepositScale(url,value) {
     esteel_index.confirmMessage("title","<span>保证金 : </span><input type='text' id='depositScale' value='"+value+"'/>","确定","取消",function(){
    	 var ds=$("#depositScale").val();
    	 esteel_index.ajaxRequest({
             url: url+"&depositScale="+ds
         }, function (data, msg) {
       		//刷新列表
     		esteel_index.list.ajaxRequestData(null, null);
         })
})
}