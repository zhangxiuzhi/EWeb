/**
 *
 * //我的点价列表
 *
 * Created by wzj on 2017/6/19.
 */



/*--------------------------------------------------------------------------------------------*/


class ListTable_pricing extends React.Component {

    constructor(props) {
        super(props);

        this.thcols = [

            {dataField:"pricingPrice",dataName:"价格",width:200},
            {dataField:"pricingNum",dataName:"数量",width:160},
            {dataField:"pricingTime",dataName:"点价时间",width:160},
            {dataField:"pricingStatus",dataName:"状态",width:160}
        ]

        this.row0 = [''];
        this.row1 = [{dataField:'commodityName'}];
        this.row2 = [
            {dataField:"pricingPrice"},
            {dataField:"pricingNum"},
            {dataField:"pricingTime",dataFormat:pricingTimeDateFormat},
            {dataField:"pricingStatus",dataFormat:pricingStatusFormat}

        ]

    }

	/*******************************************************************/
    ajaxRequestData(state,searchData){
        this.refs.jtable.ajaxRequestData(state,searchData);
    }

	/*******************************************************************/
    render() {
        var datas = [
    /*        {
                approval: null,
                approvalTime: null,
                buyId: 45,
                commodityKey: 2050072,
                commodityName: "IOC球团",
                customerId: "E00000059",
                operationId: "E00000059",
                pricingId: 109,
                isMultil:"Y",
                childs:[
                    { pricingNum: 111, pricingPrice: 111, pricingStatus: 0, pricingTime: 1498532611510},
                    { pricingNum: 222, pricingPrice: 22, pricingStatus: 0, pricingTime: 1498532611510}
                ]
            }*/
        ];
        var options = {
            url:this.props.url,
            thcols:this.thcols,
            status:this.props.status,
            searchData:"",
            page:1,
            sizePerPage:5,
            nodata:{text:"您还没有点价记录"},
            class:"table-multiple"
        }
        return (
			<JTable options={options} datas={datas} ref="jtable">
				<JTr className="sep-row" keys={this.row0} colspan={this.row2.length}/>
				<JTr className="tr-th" keys={this.row1} colspan={this.row2.length}/>
				<JTr  keys={this.row2}/>
			</JTable>
        );
    }
}

