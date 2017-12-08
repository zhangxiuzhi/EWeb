/**
 * Created by wzj on 2017/11/20.
 */

import React from 'react';
import ReactDOM from 'react-dom';
import JTable from './component/JTable/JTable';
import JTr from './component/JTable/JTr';

export default class ComponentJTable extends React.Component{

	constructor(props){
		super(props);


		this.thcols = [
			{ dataField: "indexId", dataName: "序号", width: 160 },
			{ dataField: "settlementPrice", dataName: "结算价", width: 160 },
			{ dataField: "entryDate", dataName: "录入时间", width: 160 }
		]

		this.row0 = [''];
		this.row2 = [
			{ dataField: 'indexId', dataFormat: customFormatterIndex },
			{ dataField: 'settlementPrice' },
			{ dataField: 'entryDate' }
		];


		this.state = {
			data:[]
		}
	}

	//DOM加载完成
	componentDidMount() {

		console.log(this.refs.jtable)

		this.refs.jtable.setState({
			finish:true,
			datas:[
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"},
				{indexId:"1",settlementPrice:"100.00",entryDate:"1511162525849"}
			],
			page: 1,
			totalSize: 50
		})
	}

	render(){
		var datas = this.state.data;
		var options = {
			url: "",
			thcols: this.thcols,
			status: this.props.status,
			searchData: "",
			page: 1,
			sizePerPage: 10,
			nodata: {text: "没有交易报盘"},
			//loadingText:{dataFormat:listLoadingIconFormat},	//自定义列表加载动画
		};
		return React.createElement(JTable,
			{options: options, datas: datas, ref: "jtable"},
			React.createElement(JTr, {className: "sep-row", keys: this.row0, colspan: this.row2.length}),
			React.createElement(JTr, {keys: this.row2})
		);
	}
}

function customFormatterIndex(){
	return "a"
}