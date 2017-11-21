/**
 * 我的采购
 * Created by wzj on 2017/6/19.
 */

function JBSFrame_myOrder() {

    JBSFrame.call(this);

    //列表
    this.list = null;
    //  this.list_pricing = null;

    //初始化UI
    this.initUI = function () {

        //顶部菜单栏

        //当前选中交易大厅
        //ReactDOM.render(<HeaderMenu focusItem="我的点价"/>, document.getElementById("component-headerMenu"));
        ReactDOM.render(React.createElement(HeaderMenu, { focusItem: "我的销售" }), document.getElementById("component-headerMenu"));

        //列表
        //this.list = ReactDOM.render(<ListTable_MySale url="offer/customerPricing"/>, document.getElementById("component-table"));
        this.list = ReactDOM.render(React.createElement(ListTable_MySale, { url: "offer/mySaleList" }), document.getElementById("component-table"));

        //加载品名列表
        this.load_tbPricingCommodityList();

        //加载港口列表
        this.load_portList();

        //加载状态列表
        this.load_stateList();

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
        esteel_mySale.ajaxRequest({
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
        $("#pricing-mySaleListStatus-switch .btn").on("click",function(){
            $("#pricing-mySaleListStatus-switch .btn").removeClass("active");
            $(this).addClass("active");

            //状态+其他过滤条件
            var serialize = $("#form-filter").serialize();
            esteel_mySale.list.ajaxRequestData(null, serialize+"&orderStatus="+$(this).data("value"));
        });

    }

    //加载品名列表
    this.load_tbPricingCommodityList = function () {
        esteel_mySale.ajaxRequest({
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
var esteel_mySale;
$(document).ready(function (e) {
    esteel_mySale = new JBSFrame_myOrder();
    //初始化UI
    esteel_mySale.initUI();
});

function confirmPricing(keyId, status,commodityName) {
    jbsframe.ajaxRequest({
            url: "offer/confirmPricing",
            data: {
                keyId: keyId,
                status: status,
                commodityName:commodityName
            }
        },
        function () {
            try {
                esteel_mySale.list.ajaxRequestData(null, null);
            } catch (e) {
            }
        }
    );
}