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

        //列表
        this.list = ReactDOM.render(React.createElement(ListTable, { url: "admin/depositScale/offerList" }), document.getElementById("component-table"));

        //加载港口列表
        this.load_portList();
        //加载品名列表
        this.load_tbPricingCommodityList();

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

    //加载港口列表
    this.load_portList = function() {
        esteel_index.ajaxRequest({
            url: "pricing/pricingPort.do"
        }, function (data, msg) {
            //港口
            var $port = $("#component-shipmentportKey");
            ReactDOM.render(React.createElement(DropdownList, { data: data, "class": "form-group", formLabel: $port.attr("label"), formName: $port.attr("name"), rightAddonIcon: "fa-plus" }), $port[0]);

        })
    }

    //加载品名列表
    this.load_tbPricingCommodityList = function() {
        esteel_index.ajaxRequest({
            url: "pricing/pricingCommodity.do"
        }, function (data, msg) {
            //品名
            $("#component-tbPricingCommodity").autocomplete({
                source: function (request, response) {
                    var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
                    response($.grep(data, function (item) {
                        return matcher.test(item.text) ||
                            matcher.test(item.value);
                    }));
                },
                select: function (event, ui) {
                    $("#component-tbPricingCommodity").val(ui.item.text);
                    $("input[name='commodityKey']").val(ui.item.value);
                    return false;
                }
            }).data("ui-autocomplete")._renderItem = function (ul, item) {
                return $("<li>")
                    .data("item.autocomplete", item)
                    .append($("<a></a>").text(item.text))
                    .appendTo(ul);
            };
        }.bind(this))
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

    window.setInterval("refreshExpireTime()",1000);
});

/**
 * 刷新报盘剩余时间
 * @type {number}
 */
var pageDate = new Date().getTime();
function refreshExpireTime(){
    var newDate = new Date().getTime();
    $("INPUT[type='hidden']").each(function () {
       var hid = $(this).attr('id');
       if (hid&&hid.indexOf("hidden_")!=-1){
           var offerId = hid.substring("hidden_".length,hid.length);
           var newDiff = parseFloat($(this).val())-(newDate-pageDate);
           if (newDiff<=0){
               esteel_index.list.ajaxRequestData();
           }
           $("#expireTime_"+offerId).html(getDiff(newDiff));
       }
    });
}