"use strict";
/*
function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
*/
/**
 *
 * //我的采购列表组件
 *
 * Created by wzj on 2017/3/20.
 */

/*--------------------------------------------------------------------------------------------*/
//报盘状态
var stateOperationButtonArray = [{ id: 102, name: "下单", url: "offer/orderOffer" }, {
    id: 99, name: "详情", url: systemPath + "offer/offerDetail" }];

var ListTable = function (_React$Component) {
    _inherits(ListTable, _React$Component);

    function ListTable(props) {
        _classCallCheck(this, ListTable);

        var _this = _possibleConstructorReturn(this, _React$Component.call(this, props));

        _this.thcols = [
            { dataField: "commodityName", dataName: "品名",align:"left" },
            {dataField: "offerNum", dataName: "数量（湿吨）", width: 120, align:"right"},
            { dataField: "priceIndex", dataName: "价格（元/湿吨）", width: 120, align:"right" },
            {dataField: "offerDateStart", dataName: "点价期", width: 180},
            {dataField: "dealDateStart", dataName: "交货期", width: 180},
            { dataField: "lookupName", dataName: "港口", width: 120 },
            {dataField: "expiryDate", dataName: "剩余时间", width: 160},
            { dataField: "offerStatus", dataName: "操作", width: 160 }
        ];

        _this.row0 = [''];
        //this.row1 = [{dataField:'offerId',dataFormat:customFormatterOrderNo}];
        _this.row2 = [
            { dataField: 'commodityName',align:"left" },
            {dataField: 'offerNum', align:"right", dataFormat: customFormatterOfferNum},
            { dataField: "priceIndex", align:"right",dataFormat: customFormatPriceIndex },
            {dataField: 'offerDateStart', dataFormat: customFormatterOfferDateStart},
            {dataField: 'dealDateStart', dataFormat: customFormatterDealDate},
            { dataField: 'lookupName' },
            {dataField: 'expiryDate', dataFormat: customFormatterExpireDate},
            { dataField: 'offerStatus', dataFormat: customFormatterOperation }
        ];

        return _this;
    }

    /*******************************************************************/
    /*******************************************************************/


    ListTable.prototype.ajaxRequestData = function ajaxRequestData(state, searchData) {
        this.refs.jtable.ajaxRequestData(state, searchData);
    };

    //DOM加载完成


    ListTable.prototype.componentDidMount = function componentDidMount() {};
    /*******************************************************************/


    ListTable.prototype.render = function render() {
        var datas = [];
        var options = {
            url: this.props.url,
            thcols: this.thcols,
            status: this.props.status,
            searchData: "",
            page: 1,
            sizePerPage: 10,
            nodata: { text: "没有交易报盘" },
            loadingText: { dataFormat: listLoadingIconFormat } //自定义列表加载动画
        };
        return React.createElement(JTable, { options: options, datas: datas, ref: "jtable" }, React.createElement(JTr, { className: "sep-row", keys: this.row0, colspan: this.row2.length }), React.createElement(JTr, { keys: this.row2 }));
    };

    return ListTable;
}(React.Component);

//返回价格指数


function customFormatPriceIndex(cell, row) {
    var preDis = row.premiumsDiscounts;
    if (parseInt(preDis) >= 0) {
        return '<span class="text-danger">' + row.priceIndex + "</span>+" + preDis;
    }
    return '<span class="text-danger">' + row.priceIndex + "</span>" + preDis;
}

//合同号
// function customFormatterOfferName(cell) {
//     return "<span class='text-danger'>" + cell + "</span>";
// }
//数量
function customFormatterOfferNum(cell, row) {
    var unit = "";
    if (row.offerUnit == "0") {
        unit = " <sub class='text-gray-low'>吨</sub>";
    }
    // if (row.offerUnit == "1") {
    //     unit = " <sub class='text-gray-low'>湿吨</sub>";
    // }
    return "<span class='text-success'>" + comdify(cell) + "</span>" + unit;
}
//点价期
function customFormatterOfferDateStart(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd") + " / " + new Date(row.offerDateEnd).pattern("yyyy-MM-dd");
}

//交货期
function customFormatterDealDate(cell,row){
    return new Date(cell).pattern("yyyy-MM-dd") + " / " + new Date(row.dealDateEnd).pattern("yyyy-MM-dd");
}
//报盘时间
function customFormatterExpireDate(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd");
}

//自定义操作
function customFormatterOperation(cell, row) {
    if (cell == null || cell == undefined) {
        return "";
    }
    var obj = {
        offerId: row.offerId
    };
    var result = "<button class='btn btn-ghost btn-primary btn-xs' onclick=winOpen(getStatusOperationButtonUrl(99).url," + JSON.stringify(obj) + ")>详情</button>";
    // 自己公司的卖盘不能下单

    var url = getStatusOperationButtonUrl(102).url + row.offerId;

    if (row.customerId == $("#_customerId").val()||$("#_customerId").val()!=row.targetCustomerId) {
        return result + " <button target='_blank' class='btn btn-ghost btn-primary btn-xs disabled' >下单</button>";
    }

    return result + " <button target='_blank' class='btn btn-ghost btn-primary btn-xs' onclick=placeOrder('" + getStatusOperationButtonUrl(102).url + "','" + row.offerId + "')>下单</button>";
}

//根据操作描述获取按钮url地址
function getStatusOperationButtonUrl(desc) {
    var sob = stateOperationButtonArray;
    var name,
        url = "";
    for (var i in sob) {
        if (desc == sob[i].id) {
            url = sob[i].url;
            name = sob[i].name;
        }
    }
    return { name: name, url: url };
}

//格式化数量，添加单位
function customFormatterQuantity(cell, row) {
    return cell + "<span class='margin-left-5'>" + row.quantityunit + "</span>";
}
//格式化价格，添加单位
function customFormatterPrice(cell, row) {
    return cell + "<span class='margin-left-5'>" + row.priceunit + "</span>";
}

function getDiff(dateDiff) {

    var days = Math.floor(dateDiff / (24 * 3600 * 1000));

    //计算出小时数
    var leave1 = dateDiff % (24 * 3600 * 1000); //计算天数后剩余的毫秒数
    var hours = Math.floor(leave1 / (3600 * 1000)); //计算相差分钟数
    var leave2 = leave1 % (3600 * 1000); //计算小时数后剩余的毫秒数
    var minutes = Math.floor(leave2 / (60 * 1000)); //计算相差秒数
    var leave3 = leave2 % (60 * 1000); //计算分钟数后剩余的毫秒数
    var seconds = Math.round(leave3 / 1000);
    var result = "";
    if (days > 0) {
        result = days + "天";
    }
    if (days > 0 || hours > 0) {
        result = result + hours + "小时";
    }
    if (days > 0 || hours > 0 || minutes > 0) {
        result = result + minutes + "分";
    }

    result = result + seconds + "秒";

    //小于一分钟 显示为红色
    if (dateDiff < 60 * 1000) {
        return "<span class='text-danger'>" + result + "</span>";
    }
    //小于一小时 警告
    if (dateDiff < 60 * 60 * 1000) {
        return "<span class='text-warning'>" + result + "</span>";
    }

    return result;
}

function customFormatterExpireDate(cell, row) {

    var offerId = row.offerId;

    var result = getDiff(row.expireTime);
    return "<input type='hidden' id='hidden_" + offerId + "' value='" + row.expireTime + "'><span id='expireTime_" + offerId + "'>" + result + "</span>";
}