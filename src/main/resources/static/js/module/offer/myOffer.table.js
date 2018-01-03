/**
 * Created by wzj on 2017/12/19
 * 我的报盘列表.
 */

class MyOfferTable extends React.Component {

	constructor(props) {
		super(props);

		this.thead = [
			{ dataField: "offerCode", dataName: "商品编号",align:"left"},
			{ dataField: "createUser", dataName: "报盘账号", width: 120},
			{ dataField: "commodityName", dataName: "品名", width: 120},
			{ dataField: "fe", dataName: "品位"},
			{ dataField: "portName", dataName: "港口", width: 120},
			{ dataField: "tradableQuantity", dataName: "可交易数量（湿吨）", width: 160},
			{ dataField: "priceText", dataName: "价格（元/湿吨）", width: 160},
			{ dataField: "validTimeText", dataName: "报盘有效期", width: 120},
			{ dataField: "offerStatusText", dataName: "状态", width: 160, dataFormat:formatStatus},
			{ dataField: "offerStatusText", dataName: "操作", width: 80, dataFormat:formatOperation}
		];
		this.state = {
			data:[],
			searchData:{}
		}
		this.reloadTable = this.reloadTable.bind(this);
	}

	reloadTable(_searchData){
		this.refs.jtable.reloadTable(_searchData);
	}

	render() {
		var datas = this.state.data;
		var options = {
			url:"/offer/iron/queryList",
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
//数量
function formatNumber(cell){
	return "<span>"+cell+"</span>";
}
//状态
function formatStatus(cell, row){
	var _editAble = false;
	if (cell == '草稿' || cell == '下架') {
		_editAble = true;
	}
	
	var html = "";
	html += "<div><span>"+cell+"</span></div>";
	if (_editAble) {
		html += "<div><a href='/offer/iron/edit/"+row.offerCode+"' target='_blank'>编辑</a></div>";
	} else {
		html += "<div><a href='/offer/iron/detailBySelf/"+row.offerCode+"' target='_blank'>查看详情</a></div>";
	}
	
	return html;
}
//操作
function formatOperation(cell, row){
	var _putShelvesAble = false;
	var _offShelvesAble = false;
	var _deleteAble = false;
	if (cell == '在售') {
		_offShelvesAble = true;
	}
	if (cell == '草稿' || cell == '下架') {
		_putShelvesAble = true;
		_deleteAble = true;
	}
	
	var html = "";
	if (_putShelvesAble) {
		html += "<div><a href='javascript:;' onclick=onTablePutShelves('"+row.offerCode+"')>上架</a></div>";
	}
	if (_offShelvesAble) {
		html += "<div><a href='javascript:;' onclick=onTableOffShelves('"+row.offerCode+"')>下架</a></div>";
	}
	if (_deleteAble) {
		html += "<div><a href='javascript:;' onclick=onTableDelete('"+row.offerCode+"')>删除</a></div>";
	}
	
	return html;
}

