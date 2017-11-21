/**
 * 我的采购
 * Created by wzj on 2017/6/19.
 */

function JBSFrame_myOrder() {

    JBSFrame.call(this);

    //列表
    this.list = null;
    //  this.list_pricing = null;
    //验证点价数量，必须为100的倍数
    this.validatePricingNumber = true;

    //初始化UI
    this.initUI = function () {

        //顶部菜单栏
        //当前选中交易大厅
        //ReactDOM.render(<HeaderMenu focusItem="我的成交"/>, document.getElementById("component-headerMenu"));
        ReactDOM.render(React.createElement(HeaderMenu, { focusItem: "我的购买" }), document.getElementById("component-headerMenu"));


        //列表
        //this.list = ReactDOM.render(<ListTable url="offer/myOrderList"/>, document.getElementById("component-table"));
        this.list =  ReactDOM.render(React.createElement(ListTable, { url: "offer/myOrderList" }), document.getElementById("component-table"));

        //点价列表
        //  this.list_pricing = ReactDOM.render(<ListTable_pricing url="offer/myPricing"/>, document.getElementById("component-table-pricing"));

        //加载品名列表
        this.load_tbPricingCommodityList();

        //加载港口列表
        this.load_portList();

        //加载状态列表
        this.load_stateList();

        //点价开始时间
        $("#offer-pricingStartDate").on("dp.change", function (evt) {
            $("#offer-pricingEndDate").datetimepicker("minDate", evt.date);
        });
        //点价结束时间
        $("#offer-pricingEndDate").on("dp.change", function (evt) {
            $("#offer-pricingStartDate").datetimepicker("maxDate", evt.date);
        });

        //搜索过滤
        $("#btn-searchFilter").click(function () {
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
    this.load_portList = function () {
        esteel_myOrder.ajaxRequest({
            url: "pricing/pricingPort.do"
        }, function (data, msg) {
            //港口
            var $port = $("#component-shipmentportKey");
          //  ReactDOM.render(<DropdownList data={data} class="form-group" formLabel={$port.attr("label")} formName={$port.attr("name")} rightAddonIcon="fa-plus"/>, $port[0]);
            ReactDOM.render(React.createElement(DropdownList, { data: data, "class": "form-group", formLabel: $port.attr("label"), formName: $port.attr("name"), rightAddonIcon: "fa-plus" }), $port[0]);

        })
    }

    //加载状态列表
    this.load_stateList = function () {
        /*
        //港口
        var $status = $("#component-offerStatus");
        var data = [
            //{value: 0, text: "草稿"},
           // {value: 1, text: "已发布"},
            {value: 2, text: "已成交"},
            {value: 3, text: "点价中"},
            //{value: 4, text: "已下架"},
            {value: 5, text: "点价完成"}
        ]
        //ReactDOM.render(<DropdownList data={data} class="form-group" formLabel={$port.attr("label")} formName={$port.attr("name")} rightAddonIcon="fa-plus"/>, $status[0]);
        ReactDOM.render(React.createElement(DropdownList, { data: data, "class": "form-group", formLabel: $status.attr("label"), formName: $status.attr("name"), rightAddonIcon: "fa-plus" }), $status[0]);
        */
        $("#pricing-myOrderListStatus-switch .btn").on("click",function(){
            $("#pricing-myOrderListStatus-switch .btn").removeClass("active");
            $(this).addClass("active");

            //状态+其他过滤条件
            var serialize = $("#form-filter").serialize();
            esteel_myOrder.list.ajaxRequestData(null, serialize+"&orderStatus="+$(this).data("value"));
        });

    }

    //加载品名列表
    this.load_tbPricingCommodityList = function () {
        esteel_myOrder.ajaxRequest({
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
        }.bind(this));
    }
}


/*
 //body load
 --------------------------------------------------------------------*/
var esteel_myOrder;
$(document).ready(function (e) {
    esteel_myOrder = new JBSFrame_myOrder();
    //初始化UI
    esteel_myOrder.initUI();
    window.setInterval("checkPricingTime()",10000);
});

//点价
function orderPricing(buyId) {
    var html = '<div id="dialog-orderPricing">'+
        '<div class="form-row">'+
            '<div class="col-md-12">'+
                '<div class="form-group form-subline">'+
                    '<label class="control-label">购买价格</label>'+
                    '<input type="text" class="form-control mask-money mask-money-special fs-16 text-danger" id="orderPricing-price"  />'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="form-row">'+
            '<div class="col-md-12">'+
                '<div class="form-group form-subline">'+
                    '<label class="control-label">购买数量</label>'+
                    '<input type="text" class="form-control mask-number fs-16 text-success mask-number" id="orderPricing-number" />'+
                '</div>'+
            '</div>'+
        '</div>'+
    '</div>';


    esteel_myOrder.confirmMessage("点价", html, "确定", null, function () {
        var pricingPrice = $("#orderPricing-price").val();
        var pricingNum = $("#orderPricing-number").val();
        esteel_myOrder.ajaxRequest({
                url: "offer/orderPrice",
                data: {
                    pricingPrice: pricingPrice,
                    pricingNum: pricingNum,
                    buyId: buyId
                }
            },
            function () {
                esteel_myOrder.list.ajaxRequestData(null, null);
            }
        );

    });


        //金额控制
        esteel_myOrder.renderMoneyMask();
        //数量控制
        esteel_myOrder.renderNumberMask();
    $("#orderPricing-number").change(function(){
        if(Number(this.value)%100>0){
            esteel_myOrder.validatePricingNumber = false;   //验证点数量为100的倍数失败
            esteel_myOrder.alertError("点价数量必须为100的倍数");
            $(this).parents(".modal-body").siblings(".modal-footer").children("a.btn").addClass("disabled");
        }else{
            esteel_myOrder.validatePricingNumber = true;
            $(this).parents(".modal-body").siblings(".modal-footer").children("a.btn").removeClass("disabled");
        }
    });

}

// 检查是否是点价时间
 function checkPricingTime(){
    try {
        var callback = function (isPricingTime) {
            if (isPricingTime.toString()!=$("#isPricingTime").val()){
                $("#isPricingTime").val(isPricingTime.toString());
                esteel_myOrder.list.ajaxRequestData(null,null);
            }
        }
        $.get(systemPath + "offer/isPricingTime",callback);
    }catch (e){}

}