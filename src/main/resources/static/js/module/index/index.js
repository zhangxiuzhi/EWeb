/**
 * 交易大厅-列表
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

        //顶部菜单栏
        //当前选中交易大厅
        //ReactDOM.render(<HeaderMenu focusItem="交易大厅"/>, document.getElementById("component-headerMenu"));
        ReactDOM.render(React.createElement(ComponentHeaderMenu, { current:{name:"index", text:"首页"} }), document.getElementById("component-headerMenu"));


    }

    //加载港口列表
    this.load_portList = function() {
        esteel_index.ajaxRequest({
            url: "pricing/pricingPort.do"
        }, function (data, msg) {
            //港口
            var $port = $("#component-shipmentportKey");
            //ReactDOM.render(<DropdownList data={data} class="form-group" formLabel={$port.attr("label")} formName={$port.attr("name")} rightAddonIcon="fa-plus"/>, $port[0]);
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

    //window.setInterval("refreshExpireTime()",1000);
});

/**
 * 刷新报盘剩余时间
 * @type {number}
 */
var pageDate = new Date().getTime();
function refreshExpireTime(){
    var newDate = new Date().getTime();
    $("INPUT[type='hidden']").each(function (n,element) {
       var hid = $(element).attr('id');
       if (hid&&hid.indexOf("hidden_")!=-1){
           var offerId = hid.substring("hidden_".length,hid.length);
           var newDiff = parseFloat($(element).val())-(newDate-pageDate);
           if (!isNaN(parseFloat($(element).val()))&&parseInt(newDiff)<=0){
               esteel_index.list.ajaxRequestData();
           }
           $("#expireTime_"+offerId).html(getDiff(newDiff));
       }
    });
}

//下单
function placeOrder(url,offerId) {

    var callback = function(){

        //console.log(arguments[1]);

        esteel_index.confirm(null, arguments[1]+"<br>是否确定下单？", function () {
            esteel_index.ajaxRequest({
                url: url,
                data:{
                    offerId:offerId
                }
            }, function (data, msg) {
                window.location="http://"+window.location.host+systemPath+"offer/myOrder";
                // var serialize = $("#form-filter").serialize();
                // esteel_index.list.ajaxRequestData(null, serialize);
            })
        });
    };
    
    if ($("#login_href").attr("href")) {
        window.location=$("#login_href").attr("href");
    }else{
        //查询保证金
          esteel_index.ajaxRequest(
            {
                url: "offer/checkOrderOffer",
                data:{
                    offerId:offerId
                }
            },callback
        );

    }
}