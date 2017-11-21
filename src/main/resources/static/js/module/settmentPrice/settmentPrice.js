"use strict";

/**
 * 结算价格-列表
 * Created by wzj on 2017/6/7.
 */

function JBSFrame_index() {

    JBSFrame.call(this);

    //列表
    this.list = null;
    this.status = ""; //状态
    this.orderStatusCollection = []; //状态集合
    this.filter = ""; //过滤
    this.portArray = [];

    //初始化UI
    this.initUI = function () {

        //生成时间控件
        this.renderDatetimepicker();

        //列表
        this.list = ReactDOM.render(React.createElement(ListTable, { url: "admin/settlementprice/findAll" }), document.getElementById("component-table"));

        //添加
        $("#btn-addSettment").click(function () {
            var searchData = [];
            var serialize = $("#form-filter").serialize(); //表单数据
            //提交表单
            this.submitForm(serialize);
        }.bind(this));
    };

    //提交表单
    this.submitForm = function (form) {
        this.ajaxRequest({
            url: "admin/settlementprice/add",
            data: form
        }, function (data, msg) {
            //刷新列表
            esteel_index.list.ajaxRequestData(null, null);
        }, function (data, msg) {
            alert(msg);
        });
    };
}

/*
 //body load
 --------------------------------------------------------------------*/
var esteel_index;
$(document).ready(function (e) {
    console.log("22");

    esteel_index = new JBSFrame_index();
    //初始化UI
    esteel_index.initUI();
});

//删除
function deletesettment(url) {

    esteel_index.confirm(null, "是否删除？", function () {
        esteel_index.ajaxRequest({
            url: url
        }, function (data, msg) {
            //刷新列表
            esteel_index.list.ajaxRequestData(null, null);
        });
    });
}