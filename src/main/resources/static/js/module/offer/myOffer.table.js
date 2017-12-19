/**
 * Created by wzj on 2017/12/19
 * 我的报盘列表.
 */

class MyOfferTable extends React.Component {

	constructor(props) {
		super(props);

		this.thead = [
			{ dataField: "dataTime", dataName: "商品编号",align:"left"},
			{ dataField: "shipName", dataName: "品名"},
			{ dataField: "portName", dataName: "报盘账号", width: 160 },
			{ dataField: "goodsA", dataName: "港口"  },
			{ dataField: "loadA", dataName: "数量（湿吨）", width: 120 ,dataFormat:formatNumber },
			{ dataField: "goodsB", dataName: "价格（元/湿吨）"  },
			{ dataField: "loadB", dataName: "报盘有效期", width: 120,dataFormat:formatNumber},
			{ dataField: "eta", dataName: "状态"},
			{ dataField: "etb", dataName: "操作"}
		];
		this.state = {
			data:[],
			searchData:{}
		}
		this.reloadTable = this.reloadTable.bind(this);
	}

	reloadTable(_searchData){
		this.refs.jtable.refs.jtable.setState({
			searchData:_searchData
		});
		this.refs.jtable.refs.jtable.ajaxRequestData()
	}

	render() {
		var datas = this.state.data;
		var options = {
			url:"/data/dataArrivalsList",
			thead: this.thead,
			status: this.props.status,
			searchData:this.props.searchData,
			pageNum: 1,
			pageSize: 10,
			nodata: "没有数据",
			//loadingText:{dataFormat:listLoadingIconFormat},	//自定义列表加载动画
		};
		return React.createElement(ComponentJTable, {options: options, datas: datas, ref: "jtable"});
	}
}
function formatNumber(cell){
	var cell = cell == null ? "" : cell;
	return "<span style='color: #52A751;'>"+cell+"</span>";
}