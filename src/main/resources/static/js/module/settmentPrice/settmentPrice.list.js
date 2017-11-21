"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

/**
 *
 * 结算价列表
 *
 * Created by wzj on 2017/3/20.
 */
/*--------------------------------------------------------------------------------------------*/
var stateOperationButtonArray = [{ name: "删除", url: "admin/settlementprice/delete?settId=" }];

var ListTable = function (_React$Component) {
    _inherits(ListTable, _React$Component);

    function ListTable(props) {
        _classCallCheck(this, ListTable);

        var _this = _possibleConstructorReturn(this, (ListTable.__proto__ || Object.getPrototypeOf(ListTable)).call(this, props));

        _this.thcols = [{ dataField: "indexId", dataName: "序号", width: 160 }, { dataField: "contractId", dataName: "合约号", width: 160 }, { dataField: "settlementPrice", dataName: "结算价", width: 160 }, { dataField: "settlementDate", dataName: "结算价时间", width: 160 }, { dataField: "entryDate", dataName: "录入时间", width: 160 }, { dataField: "indexId", dataName: "操作", width: 160 }];

        _this.row0 = [''];
        _this.row2 = [{ dataField: 'indexId', dataFormat: customFormatterIndex }, { dataField: "contractId", dataFormat: customFormatterContractId }, { dataField: 'settlementPrice', dataFormat: customFormatterPrice }, { dataField: 'settlementDate', dataFormat: customFormatterSettlementDate }, { dataField: 'entryDate', dataFormat: customFormatterOfferDate }, { dataField: 'indexId', dataFormat: customFormatterOperation }];

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
                nodata: { text: "暂时没有数据" }
            };
            return React.createElement(
                JTable,
                { options: options, datas: datas, ref: "jtable" },
                React.createElement(JTr, { className: "sep-row", keys: this.row0, colspan: this.row2.length }),
                React.createElement(JTr, { keys: this.row2 })
            );
        }
    }]);

    return ListTable;
}(React.Component);

//自定义序号


function customFormatterIndex(cell, row) {
    return 0;
}
//合约号
function customFormatterContractId(cell) {
    return "<span class='text-danger'>" + cell + "</span>";
}
//结算价
function customFormatterPrice(cell, row) {
    var unit = "";
    return "<span class='text-success'>" + cell + "</span>" + unit;
}
//结算价时间
function customFormatterSettlementDate(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd");
}
//报盘时间
function customFormatterOfferDate(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd HH:mm:ss");
}
//自定义操作
function customFormatterOperation(cell, row) {
    if (cell == null || cell == undefined) {
        return "";
    }
    var url = getStatusOperationButtonUrl("删除").url + row.indexId;
    return "<button class='btn btn-ghost btn-primary btn-xs' onclick=deletesettment('" + url + "')>删除</button>";
}

//根据操作描述获取按钮url地址
function getStatusOperationButtonUrl(desc) {
    var sob = stateOperationButtonArray;
    var name,
        url = "";
    for (var i in sob) {
        if (desc == sob[i].name) {
            url = sob[i].url;
            name = sob[i].name;
        }
    }
    return { name: name, url: url };
}