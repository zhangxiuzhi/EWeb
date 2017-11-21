/**
 *
 * 我的客户的点价列表
 *
 * Created by wzj on 2017/6/19.
 */



/*--------------------------------------------------------------------------------------------*/


class ListTable_pricing extends React.Component {

    constructor(props) {
        super(props);

        this.thcols = [
            {dataField: "pricingPrice", dataName: "成交价格", width: 200},
            {dataField: "pricingNum", dataName: "数量", width: 160},
            {dataField: "pricingTime", dataName: "点价时间", width: 160},
            {dataField: "pricingId", dataName: "操作", width: 160}
        ]

        this.row0 = [''];
        this.row1 = [
            {dataField: "offerId", dataFormat: offDateformat}

        ];
        this.row2 = [
            {dataField:"pricingPrice",dataFormat:pricingPriceFormat},
            {dataField:"pricingNum",dataFormat:pricingNumFormat},
            {dataField: "pricingTime", dataFormat: pricingTimeDateFormat},
            {dataField: "pricingId", dataFormat: pricingStatusFormat}
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
            nodata: {text: "您还没有点价记录"},
            class:"table-multiple"
        }
        return (
            <JTable options={options} datas={datas} ref="jtable">
                <JTr className="sep-row" keys={this.row0} colspan={this.row2.length}/>
                <JTr className="tr-th" keys={this.row1} colspan={this.row2.length}/>
                <JTr  className="child-row" keys={this.row2}/>
            </JTable>
        );
    }
}



function pricingTimeDateFormat(cell, row) {
    return new Date(cell).pattern("yyyy-MM-dd HH:mm:ss");
}

function offDateformat(cell, row) {
    var info = "";
    info += "<div class='pull-left margin-right-10' style='width:120px;'> 品名：<span class='text-default'>"+row.commodityName+"</span></div>";
    info += "<div class='pull-left margin-right-10' style='width:220px;'> 合同号：<span class='text-default'>"+row.offerName+"</span></div>";
    info += "<div class='pull-left margin-right-20' > 港口：<span class='text-default'>"+row.lookupName+"</span></div>";
    info += "<div class='pull-left margin-right-20' > 数量：<span class='text-default'>"+row.offerNum+"</span> <sub class='text-gray-low'>湿吨</sub></div>";
    info += "<div class='pull-left margin-right-20' > 价格指数：<span class='text-default'>"+row.priceIndex+"</span></div>";
    info += "<div class='pull-left margin-right-20' > 升贴水：<span class='text-default'>"+row.premiumsDiscounts+"</span></div>";

    return info;
}

function pricingStatusFormat(cell, row) {
    if (row.pricingStatus == 0) {
        return "<button target='_blank' class='btn btn-ghost btn-primary btn-xs' onclick=confirmPricing(" + cell + ",1)>确认</button> " + "<button target='_blank' class='btn btn-ghost btn-primary btn-xs' onclick=confirmPricing(" + cell + ",3)>拒绝</button>"
    }
    if (row.pricingStatus == 1) {
        return "<span class='text-success'>已确认</span>";
    }
    if (row.pricingStatus == 3) {
        return "<span class='text-warning'>已拒绝</span>";
    }
    if (row.pricingStatus == 2) {
        return "<span class='text-success'>平台已确认</span>";
    }
    if (row.pricingStatus == 4) {
        return "<span class='text-warning'>平台已拒绝</span>";
    }
}

//格式化价格,单位
function pricingPriceFormat(cell,row){
    return "<span class='text-danger'>"+cell + "</span> <sub class='text-gray-low'>元</sub>";
}
//格式化数量,单位
function pricingNumFormat(cell,row){
    return "<span class='text-success'>"+cell +"</span> <sub class='text-gray-low'>湿吨</sub>";
}