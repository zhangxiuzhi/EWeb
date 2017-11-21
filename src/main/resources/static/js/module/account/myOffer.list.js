"use strict";
/*
var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }
*/
/**
 *
 * //我的报盘列表
 *
 * Created by wzj on 2017/6/7.
 */

/*--------------------------------------------------------------------------------------------*/
//报盘状态
var stateOperationButtonArray = [{ id: 0, name: "草稿", url: "" }, { id: 1, name: "已发布", url: "" }, { id: 2, name: "已成交", url: "" }, { id: 3, name: "点价中", url: "" }, { id: 4, name: "已下架", url: "" }, { id: 5, name: "点价完成", url: "" },{ id: 6, name: "待支付定金", url: "" }, { id: 99, name: "详情", url: systemPath + "offer/offerDetail" }, { id: 100, name: "编辑", url: systemPath + "offer/offerEdit" }, { id: 101, name: "删除", url: "offer/offerDel?offerId=" }];

var ListTable = function (_React$Component) {
    _inherits(ListTable, _React$Component);

    function ListTable(props) {
        _classCallCheck(this, ListTable);

        var _this = _possibleConstructorReturn(this, (ListTable.__proto__ || Object.getPrototypeOf(ListTable)).call(this, props));

        _this.thcols = [{ dataField: "commodityName", dataName: "品名",align:"left" }, { dataField: "offerNum", dataName: "数量（湿吨）", width: 120 ,align:"right"}, { dataField: "priceIndex", dataName: "价格（元/湿吨）", width: 120 ,align:"right"}, { dataField: "offerDate", dataName: "点价期", width: 180 }, { dataField: "lookupName", dataName: "港口", width: 120 }, { dataField: "expiryDate", dataName: "有效期", width: 180 }, { dataField: "offerStatus", dataName: "状态", width: 60, align: "center" }, { dataField: "offerId", dataName: "操作", width: 220 }];

        _this.row0 = [''];
        //this.row1 = [{dataField:'offerId',dataFormat:customFormatterOrderNo}];
        _this.row2 = [{ dataField: 'commodityName',align:"left" }, { dataField: 'offerNum', dataFormat: customFormatterOfferNum ,align:"right"}, { dataField: "priceIndex", dataFormat: customFormatPriceIndex,align:"right" }, { dataField: 'offerDateStart', dataFormat: customFormatterOfferDateStart }, { dataField: 'lookupName' }, { dataField: 'expiryDate', dataFormat: customFormatterExpireDate }, { dataField: 'offerStatus', dataFormat: customFormatterStatus }, { dataField: 'offerId', dataFormat: customFormatterOperation }];

        return _this;
    }

    /*******************************************************************/


    _createClass(ListTable, [{
        key: "ajaxRequestData",
        value: function ajaxRequestData(state, searchData) {
            this.refs.jtable.ajaxRequestData(state, searchData);
        }

        /*******************************************************************/

    }, {
        key: "render",
        value: function render() {
            var datas = [];
            var options = {
                url: this.props.url,
                thcols: this.thcols,
                status: this.props.status,
                searchData: "",
                page: 1,
                sizePerPage: 10,
                nodata: { text: "您还没有发布报盘" },
                loadingText:{dataFormat:listLoadingIconFormat},	//自定义列表加载动画
            };
            return React.createElement(JTable, { options: options, datas: datas, ref: "jtable" }, React.createElement(JTr, { className: "sep-row", keys: this.row0, colspan: this.row2.length }), React.createElement(JTr, { keys: this.row2 }));
        }
    }]);

    return ListTable;
}(React.Component);

//自定义 商品编号，卖家信息


function customFormatterOrderNo(cell, row) {
    var html = "";
    html += "<div class='pull-left' style='width:200px;margin-right:10px'>商品编号：<span style='color:#333;'>" + cell + "</span></div>";
    html += "<div class='pull-left'>卖方：<span style='color:#333;'>" + row.saleName + "</span></div>";
    return html;
}

//返回价格指数
function customFormatPriceIndex(cell, row) {
    var preDis = row.premiumsDiscounts;
    if (parseInt(preDis) > 0) {
        return '<span class="text-danger">' + row.priceIndex + "</span>+" + preDis;
    }
    return '<span class="text-danger">' + row.priceIndex + "</span>" + preDis;
}

//报盘时间
function customFormatterExpireDate(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd HH:mm:ss");
}

//数量
function customFormatterOfferNum(cell, row) {
    var unit = "";
    // if (row.offerUnit == "0") {
    //     unit = " <sub class='text-gray-low'>吨</sub>";
    // }
    // if (row.offerUnit == "1") {
    //     unit = " <sub class='text-gray-low'>湿吨</sub>";
    // }
    return "<span class='text-success'>" + comdify(cell) + "</span>" + unit;
}
//点价期
function customFormatterOfferDateStart(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd") + " / " + new Date(row.offerDateEnd).pattern("yyyy-MM-dd");
}
//报盘时间
function customFormatterOfferDate(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd HH:mm:ss");
}

//显示状态
function customFormatterStatus(cell, row) {
    return getStatusOperationButtonUrl(cell).name;
}

//自定义操作
function customFormatterOperation(cell, row) {
    if (cell == null || cell == undefined) {
        return "";
    } else {

        var editUrl = getStatusOperationButtonUrl(100).url + row.offerId; //编辑
        var delUrl = getStatusOperationButtonUrl(101).url + row.offerId; //删除

        var obj = {
            offerId: row.offerId
        };

        var html = "<button target='_blank' class='btn btn-ghost btn-primary btn-xs' onclick=winOpen(getStatusOperationButtonUrl(99).url," + JSON.stringify(obj) + ")>详情</button>";

        //草稿
        if (row.offerStatus == 0) {
            html += " <button target='_blank' class='btn btn-ghost btn-primary btn-xs' onclick=winOpen(getStatusOperationButtonUrl(100).url," + JSON.stringify(obj) + ")>编辑</button> ";
            html += " <button target='_blank' class='btn btn-ghost btn-primary btn-xs' onclick=changeState(" + cell + ",1)>发布</button>" + " <button target='_blank' class='btn btn-ghost btn-primary btn-xs' onclick=deleteOffer(" + cell + ",'" + delUrl + "')>删除</button>";
        }
        //已发布
        if (row.offerStatus == 1) {
            html += " <button target='_blank' class='btn btn-ghost btn-primary btn-xs' onclick=changeState(" + cell + ",0)>撤回</button>" ;
        }

        return html;
    }
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