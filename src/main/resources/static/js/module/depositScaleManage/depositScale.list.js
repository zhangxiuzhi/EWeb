"use strict";

var _createClass = function () {
    function defineProperties(target, props) {
        for (var i = 0; i < props.length; i++) {
            var descriptor = props[i];
            descriptor.enumerable = descriptor.enumerable || false;
            descriptor.configurable = true;
            if ("value" in descriptor) descriptor.writable = true;
            Object.defineProperty(target, descriptor.key, descriptor);
        }
    }

    return function (Constructor, protoProps, staticProps) {
        if (protoProps) defineProperties(Constructor.prototype, protoProps);
        if (staticProps) defineProperties(Constructor, staticProps);
        return Constructor;
    };
}();

function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
        throw new TypeError("Cannot call a class as a function");
    }
}

function _possibleConstructorReturn(self, call) {
    if (!self) {
        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
    }
    return call && (typeof call === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
    if (typeof superClass !== "function" && superClass !== null) {
        throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
    }
    subClass.prototype = Object.create(superClass && superClass.prototype, {
        constructor: {
            value: subClass,
            enumerable: false,
            writable: true,
            configurable: true
        }
    });
    if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

/**
 *
 * Created by wzj on 2017/3/20.
 */

/*--------------------------------------------------------------------------------------------*/

var stateOperationButtonArray = [   
     {name: "删除", url: "admin/depositScale/delete?depositId="},
     {name: "修改", url: "admin/depositScale/update?depositId="}
 ];

class ListTable extends React.Component {

    constructor(props) {
        super(props);

        this.thcols = [
            {dataField: "customerId", dataName: "客户ID", width: 150},
            {dataField: "customerName", dataName: "客户名称", width: 200},
            {dataField: "depositScale", dataName: "保证金比率", width: 150},
            {dataField: "operatorTime", dataName: "录入时间", width: 150},
            {dataField: "scaleId", dataName: "操作", width: 160}
        ]

        this.row0 = [''];
        this.row2 = [
            {dataField: "customerId",  dataFormat: customFormatterContractId},
            {dataField: 'customerName'},
            {dataField: 'depositScale',dataFormat: FormatterDatedepositScale},
            {dataField: 'operatorTime',dataFormat: FormatteroperatorTime},
            {dataField: 'scaleId', dataFormat: customFormatterOperation}
        ]

    }

    /*******************************************************************/
    ajaxRequestData(state, searchData) {
        this.refs.jtable.ajaxRequestData(state, searchData);
    }

    /*******************************************************************/
    render() {
        var datas = [];
        var options = {
            url: this.props.url,
            thcols: this.thcols,
            status: this.props.status,
            searchData: "",
            page: 1,
            sizePerPage: 5,
            nodata: {text: "暂时没有数据"}
        }
        return (
            <JTable options={options} datas={datas} ref="jtable">
                <JTr className="sep-row" keys={this.row0} colspan={this.row2.length}/>
                <JTr keys={this.row2}/>
            </JTable>
        );
    }
}
/*
//自定义序号
function customFormatterIndex(cell,row){
	return 0;
}*/
//客户id
function customFormatterContractId(cell) {
    return "<span class='text-danger'>" + cell + "</span>";
}
/*function customFormatterPrice(cell, row) {
    var unit = "";
    return "<span class='text-success'>" + cell + "</span>" + unit;
}*/
//押金比率
function FormatterDatedepositScale(cell) {
	if(cell==0){
		return "<span class='text-danger'>" + cell + "</span>";
	}else{
		 return "<span class='text-danger'>" + cell+"%</span>";
	}
}
//操作时间
function FormatteroperatorTime(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd HH:mm:ss");
}
//自定义操作
function customFormatterOperation(cell, row) {
    if (cell == null || cell == undefined) {
        return "";
    }
    var url = getStatusOperationButtonUrl("删除").url + row.scaleId;
    var del= "<button class='btn btn-ghost btn-primary btn-xs' onclick=deleteDepositScale('" + url + "')>删除</button>";
    var urlupdate = getStatusOperationButtonUrl("修改").url + row.scaleId;
    var depositScaleValue=row.depositScale;
    var update= "<button class='btn btn-ghost btn-primary btn-xs' onclick=updateDepositScale('" + urlupdate+"','"+depositScaleValue + "')>修改</button>";
    return del+" "+update;
}


//根据操作描述获取按钮url地址
function getStatusOperationButtonUrl(desc) {
    var sob = stateOperationButtonArray;
    var name, url = "";
    for (var i in sob) {
        if (desc == sob[i].name) {
            url = sob[i].url;
            name = sob[i].name;
        }
    }
    return {name: name, url: url};
}
