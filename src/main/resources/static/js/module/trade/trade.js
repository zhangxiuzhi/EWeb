/**
 * 交易大厅-列表
 * Created by wzj on 2017/6/7.
 */
var filterData_tradeType = [
    {id: "tradeType-all", text: "全部", value: "all",name:"tradeType"},
    {id: "tradeType-inStock", text: "港口现货", value: "inStock",name:"tradeType"},
    {id: "tradeType-future", text: "远期现货", value: "future",name:"tradeType"},
    {id: "tradeType-pricing", text: "点价", value: "pricing",name:"tradeType"}
]
var filterData_tradePort = [
    {id: "tradePort-all", text: "全部", value: "all",name:"tradePort"},
    {id: "tradePort-qd", text: "青岛港", value: "qd",name:"tradePort"},
    {id: "tradePort-ly", text: "连云港", value: "ly",name:"tradePort"},
    {id: "tradePort-dl", text: "大连港", value: "dl",name:"tradePort"},
    {id: "tradePort-jt", text: "京唐港", value: "jt",name:"tradePort"},
    {id: "tradePort-tj", text: "天津港", value: "tj",name:"tradePort"},
    {id: "tradePort-rz", text: "日照港", value: "rz",name:"tradePort"}
]
var filterData_tradeBrand = [
    {id: "tradeBrand-all", text: "全部", value: "all",name:"tradeBrand"},
    {id: "tradeBrand-inStock", text: "60%以下", value: "inStock",name:"tradeBrand"},
    {id: "tradeBrand-future", text: "60%-62%", value: "future",name:"tradeBrand"},
    {id: "tradeBrand-pricing", text: "62%-65%", value: "pricing",name:"tradeBrand"}
]
var sortData = [
    {id:"trade",text:"交易量",sort:"none"},
    {id:"price",text:"价格",sort:"asc"},
    {id:"number",text:"数量",sort:"asc"}
]


function JBSFrame_index() {

    JBSFrame.call(this);

    //列表
    this.list = null;
    this.status = "";	//状态
    this.filter_tradeType = null;	//过滤交易类型
    this.filter_tradePort = null;   //过滤港口
    this.filter_tradeBrand = null;  //过滤品位
    this.sorter = null; //排序
    this.portArray = [];

    //初始化UI
    this.initUI = function () {

        //主菜单栏
        //当前选中交易大厅
        ReactDOM.render(React.createElement(ComponentHeaderMenu, { current:{name:"trade", text:"交易"} }), document.getElementById("component-headerMenu"));

        //过滤
        //交易类型
        this.filter_tradeType = ReactDOM.render(React.createElement(ComponentRadioBox, {value:'all', data:filterData_tradeType, className:"TagStyle"}), document.getElementById("component-filter-tradeType"));
        //港口
        this.filter_tradePort = ReactDOM.render(React.createElement(ComponentRadioBox, {value:'all', data:filterData_tradePort, className:"TagStyle"}), document.getElementById("component-filter-tradePort"));
        //品位
        this.filter_tradeBrand = ReactDOM.render(React.createElement(ComponentRadioBox, {value:'all', data:filterData_tradeBrand, className:"TagStyle"}), document.getElementById("component-filter-tradeBrand"));

        //排序
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