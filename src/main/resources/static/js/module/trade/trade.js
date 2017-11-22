/**
 * 交易大厅-列表
 * Created by wzj on 2017/6/7.
 */

function JBSFrame_index() {

    JBSFrame.call(this);

    //列表
    this.list = null;
    this.status = "";	//状态
    this.filter = "";	//过滤
    this.sorter = null; //排序
    this.portArray = [];

    //初始化UI
    this.initUI = function () {

        //主菜单栏
        //当前选中交易大厅
        ReactDOM.render(React.createElement(ComponentHeaderMenu, { current:{name:"trade", text:"交易"} }), document.getElementById("component-headerMenu"));

        //过滤
        //交易类型
        this.filter_tradeType = ReactDOM.render(React.createElement(ComponentRadioBox, {value:'all',className:"TagStyle"}), document.getElementById("component-filter-tradeType"));
        //港口
        this.filter_port = ReactDOM.render(React.createElement(ComponentRadioBox, {value:'all',className:"TagStyle"}), document.getElementById("component-filter-port"));
        //品位
        this.filter_brand = ReactDOM.render(React.createElement(ComponentRadioBox, {value:'all'}), document.getElementById("component-filter-brand"));

        //排序
        var sortData = [
            {id:"trade",text:"交易量",sort:"none"},
            {id:"price",text:"价格",sort:"asc"},
            {id:"number",text:"数量",sort:"asc"}
        ]
        this.sorter = ReactDOM.render(React.createElement(ComponentSortGroupButton, {data:sortData,onChange:this.reloadList}), document.getElementById("component-tradeSortGroup"));

        //卡片展示
        this.list = ReactDOM.render(React.createElement(TradeCardList, {}), document.getElementById("component-tradeCardList"));
    }

    //加载港口列表
    this.load_portList = function() {
    }

    //加载品名列表
    this.load_tbPricingCommodityList = function() {
    }

    //重新加载卡片列表
    this.reloadList = function(){
        //排序结果值
        this.sorter.value;

    }.bind(this)
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