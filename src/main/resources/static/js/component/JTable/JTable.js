/**
 * Created by wzj on 2017/5/4.
 */

/*--------------------------------------------------------------------------------------------*/
//表格头单元格组件Th
class JTh extends React.Component {
	render() {
		return React.createElement(
			"th",
			{ width: this.props.width, style:{textAlign:this.props.align} },
			this.props.text
		);
	}
}
/*--------------------------------------------------------------------------------------------*/
//表格体单元格组件Td

class JTd extends React.Component {
	render() {
		var text = this.props.children; //this.props.text
		if (this.props.dataFormat) {
			//渲染HTML
			text = this.props.dataFormat(text, this.props.data, this);
			text = React.createElement("div", { dangerouslySetInnerHTML: { __html: text } });
		}

		//if(this.props.text){
		//		return <td colSpan={this.props.colSpan} rowSpan={this.props.rowSpan} className={this.props.className} style={{backgroundColor:'#00CC00'}}>{text}</td>;
		//}else{
		return React.createElement(
			"td",
			{ colSpan: this.props.colSpan, rowSpan: this.props.rowSpan, ref:"jtd" ,style:{textAlign:this.props.align}},
			text
		);
		//}
	}
}
/*--------------------------------------------------------------------------------------------*/
//表格行组件Tr
class JTr extends React.Component {
	render() {
		//console.log("jtr",this.props,this.props.data)

		var data = this.props.data;
		var index = this.props.index; //一单多货的第几行数据

		var siblingsLength = this.props.siblingsLength; //纵向合并数量


		//var td = this.props.ths.map((th)=>
		//<JTd colSpan={this.props.colspan} dataFormat={this.props.dataFormat} data={data}>{data[th.dataField]}</JTd>
		//console.log(th)
		//一单多货，当前单元格向下纵向合并
		//th.rowSpan ?
		//<JTd colSpan={this.props.colspan} rowSpan={this.props.siblingsLength} dataFormat={th.dataFormat} data={data} >{data[th.dataField]}</JTd>
		//:
		//<JTd colSpan={this.props.colspan} dataFormat={th.dataFormat} data={data} >{data[th.dataField]}</JTd>
		//	);
		var td = [];
		var keys = this.props.keys;
		/*
		 keys =  {dataField:'placeOrderTime',dataFormat:customFormatterOrderTime},
		 */
		if (keys.length > 0) {
			if (index == siblingsLength) {
				for (var i = 0; i < keys.length; i++) {
					if (keys[i].rowSpan == true) {
						td.push(React.createElement(
							JTd,
							{ rowSpan: siblingsLength, colSpan: this.props.colspan, dataFormat: keys[i].dataFormat, data: data, align:keys[i].align },
							data[keys[i].dataField]
						));
					} else {
						td.push(React.createElement(
							JTd,
							{ colSpan: this.props.colspan, dataFormat: keys[i].dataFormat, data: data, align:keys[i].align },
							data[keys[i].dataField]
						));
					}
				}
			} else if (index == siblingsLength + 1) {
				for (var i = 0; i < keys.length; i++) {
					if (keys[i].rowSpan != true) {
						td.push(React.createElement(
							JTd,
							{ colSpan: this.props.colspan, dataFormat: keys[i].dataFormat, data: data, align:keys[i].align },
							data[keys[i].dataField]
						));
					}
				}
			} else {
				for (var i = 0; i < keys.length; i++) {
					td.push(React.createElement(
						JTd,
						{ colSpan: this.props.colspan, dataFormat: keys[i].dataFormat, data: data, align:keys[i].align },
						data[keys[i].dataField]
					));
				}
			}
		} else {
			td.push(React.createElement(
				JTd,
				{ colSpan: this.props.children.props.colspan, dataFormat: keys.dataFormat, align:keys.align },
				keys.text
			));
		}

		return React.createElement(
			"tr",
			{ className: this.props.className, ref:"jtr" },
			td
		);
	}
}
/*--------------------------------------------------------------------------------------------*/
//表格头组件Thead
class JThead extends React.Component {

	render() {
		var ths = this.props.thcols;
		var html_ths = ths.map(th => React.createElement(JTh, { width: th.width, text: th.dataName, field: th.dataField, align:th.align }));
		return React.createElement(
			"thead",
			null,
			React.createElement(
				"tr",
				null,
				html_ths
			)
		);
	}
}

/*--------------------------------------------------------------------------------------------*/
//表格体组件Tbody
class JTbody extends React.Component {
	render() {
		return React.createElement(
			"tbody",
			{ className: this.props.className, ref:"jtbody" },
			this.props.children
		);
	}
}
/*--------------------------------------------------------------------------------------------*/
//表格组件Table
class JTable extends React.Component {
	constructor(props) {
		super(props);

		/*
		 options = {
		 url:this.props.url,
		 thcols:this.thcols,
		 status:this.props.status,
		 searchData:{
		 key:"",
		 value:""
		 },
		 page:1,
		 sizePerPage:5,
		 nodata:{
		 text:"data not available",
		 dataFormat:customFormatterOrderTime,
		 }
		 }
		 */
		this.options = this.props.options; //表格设置
		this.thcols = this.options.thcols; //所有表头字段
		this.nodata = {
			text: this.options.nodata ? this.options.nodata.text : "data not available",
			dataFormat: this.options.nodata ? this.options.nodata.dataFormat : null
		};
		this.loading = {
			text: this.options.loadingText ? this.options.loadingText.text : "读取数据中。。。",
			dataFormat: this.options.loadingText ? this.options.loadingText.dataFormat : null
		};
		this.state = {
			finish: false, //没有加载完成
			datas: this.props.datas.length == 0 ? [] : this.props.datas,
			status: this.options.status,
			searchData: this.options.searchData,
			page: this.options.page,
			sizePerPage: this.options.sizePerPage,
			totalSize: 0,
			pageStartIndex: 1,
			paginationSize: 3
		};

		this.topPagination = false;
		this.bottomPagination = true;

		this.ajaxRequestData = this.ajaxRequestData.bind(this);
		this.handlePageChange = this.handlePageChange.bind(this);
		this.handleSizePerPageChange = this.handleSizePerPageChange.bind(this);
	}

	/*******************************************************************/
	//DOM加载完成
	componentDidMount() {
		//ajax加载数据
		this.ajaxRequestData();
	}
	//完成渲染新的props或者state后调用
	componentDidUpdate() {
		if(this.options.customRenderFinishCallBack){
			this.options.customRenderFinishCallBack();
		}
	}
	/*******************************************************************/
	//ajax加载数据
	ajaxRequestData(status = this.state.status, searchData = this.state.searchData, page = this.state.page, sizePerPage = this.state.sizePerPage) {
		var _d = {
			status: status,
			//searchData :searchData,
			pageNum: page,
			pageSize: sizePerPage
		}
		for(var s in searchData){
			_d[s] = searchData[s];
		}

		//开始加载
		this.setState({ finish: false });
		//ajax
		$.ajax({
			url:  this.options.url,
			data:_d,
			method: "POST",
			dataType: "JSON",
			/*beforeSend: function (xhr) {
				xhr.setRequestHeader(header, token);
				if (searchData != null) {
					this.data = this.data + "&" + searchData;
				}
			},*/
			success: function (str, msg, response) {
				if (response.statusText == "OK" || response.statusText == "success") {
					var result = JSON.parse(response.responseText);
					this.setState({
						finish: true, //加载完毕
						searchData:searchData,	//过滤条件
						datas: result.data,
						page: result.number + 1,
						sizePerPage: result.size,
						totalSize: result.totalElements
					});
				}
			}.bind(this),
			error: function (str, msg, response) {}.bind(this)
		});
	}
	/*******************************************************************/
	//分页数量选项事件
	handleSizePerPageChange(sizePerPage) {
		this.ajaxRequestData(this.state.status, this.state.searchData, 1, sizePerPage);
	}

	//分页事件
	handlePageChange(page, sizePerPage) {
		this.ajaxRequestData(this.state.status, this.state.searchData, page, sizePerPage);
	}
	/*******************************************************************/
	//迭代tr
	//length 一共有几行,用于纵向合并单元格
	renderTr(tr, child, data, length) {

		//显示多条子数据时，若没有子数据，则不添加子数据行
		if (child.props.className == "child-row" && data.isMultil == "N" && data.childs.length == 0) {
			//tr.push(<JTr data={data} keys={child.props.keys} colspan={child.props.colspan} rowspan={child.props.rowSpan} index={tr.length} className={child.props.className} siblingsLength={length}/>);
		} else {
			tr.push(React.createElement(JTr, { data: data, keys: child.props.keys, colspan: child.props.colspan, rowspan: child.props.rowSpan, index: tr.length, className: child.props.className, siblingsLength: length }));
		}

		return tr;
	}

	/*******************************************************************/

	//
	render() {
		//每页显示条数列表
		var sizePerPageList = [{ text: '5', value: 5 }, { text: '10', value: 10 }, { text: '所有', value: this.state.totalSize }];
		//分页对象-顶部
		var top_pagination = null;
		//分页对象-底部
		var bottom_pagination = null;
		//
		var datas = this.state.datas;
		var children = this.props.children;
		var tbodyCls = ""; //className
		var tbody = [];
		var tr = [];

		//加载提示
		if (this.state.finish == false && this.options.url != "") {
			tbody = [], tr = [];
			var loading = this.loading;
			tr = React.createElement(
				JTr,
				{ className: "sep-row", keys: [] },
				React.createElement(JTd, { colspan: this.thcols.length })
			);
			tbody.push(React.createElement(
				JTbody,
				null,
				tr
			));
			tr = React.createElement(
				JTr,
				{ className: "loading-row", keys: loading },
				React.createElement(
					JTd,
					{ colspan: this.thcols.length },
					loading.text
				)
			);
			tbody.push(React.createElement(
				JTbody,
				null,
				tr
			));
		} else {

			//没有数据
			if (datas == undefined || datas.length == 0) {
				tbody = [], tr = [];
				var noDataRow = this.nodata;
				tr = React.createElement(
					JTr,
					{ className: "sep-row", keys: [] },
					React.createElement(JTd, { colspan: this.thcols.length })
				);
				tbody.push(React.createElement(
					JTbody,
					null,
					tr
				));
				tr = React.createElement(
					JTr,
					{ className: "nodata-row", keys: noDataRow },
					React.createElement(
						JTd,
						{ colspan: this.thcols.length },
						noDataRow.text
					)
				);
				tbody.push(React.createElement(
					JTbody,
					null,
					tr
				));
			} else {

				top_pagination = this.topPagination ? React.createElement(PaginationList, {
					currPage: this.state.page,
					sizePerPage: this.state.sizePerPage,
					dataSize: this.state.totalSize,
					pageStartIndex: this.state.pageStartIndex,
					paginationSize: this.state.paginationSize,
					hideSizePerPage: "true",
					sizePerPageList: sizePerPageList,
					prePage: "\u4E0A\u4E00\u9875",
					nextPage: "\u4E0B\u4E00\u9875",
					firstPage: "\u9996\u9875",
					lastPage: "\u5C3E\u9875",
					changePage: this.handlePageChange //分页事件
					//onSizePerPageList={this.handleSizePerPageChange} //分页下拉事件
				}) : null;
				//分页对象-底部
				bottom_pagination = this.bottomPagination ? React.createElement(PaginationList, {
					currPage: this.state.page,
					sizePerPage: this.state.sizePerPage,
					dataSize: this.state.totalSize,
					pageStartIndex: this.state.pageStartIndex,
					paginationSize: this.state.paginationSize,
					sizePerPageList: sizePerPageList,
					prePage: "\u4E0A\u4E00\u9875",
					nextPage: "\u4E0B\u4E00\u9875",
					firstPage: "\u9996\u9875",
					lastPage: "\u5C3E\u9875",
					changePage: this.handlePageChange //分页事件
					, onSizePerPageList: this.handleSizePerPageChange //分页下拉事件
				}) : null;

				tbody = [], tr = [];
				//有数据时进行转换
				for (var i = 0; i < datas.length; i++) {
					tr = [];
					if (datas[i].isMultil == "Y" && datas[i].childs.length > 0) {
						//一单多货的情况
						//添加第一行，第二行
						//for (var j = 0; j < 2; j++) {
						//tr = this.renderTr(tr, children[j], datas[i]);
						//}

						//添加订单内容
						for (var or = 0; or < datas[i].childs.length; or++) {
							//tr = this.renderTr(tr, children[children.length - 1], datas[i].childs[or], datas[i].childs.length);
						}

						for(var j=0;j<children.length;j++){
							if(children[j].props.className =="sep-row"){
								tr = this.renderTr(tr, children[j], datas[i]);
							}
							if(children[j].props.className =="tr-th"){
								tr = this.renderTr(tr, children[j], datas[i]);
							}
							if(children[j].props.className =="child-row"){
								for (var or = 0; or < datas[i].childs.length; or++) {
									tr = this.renderTr(tr, children[j], datas[i].childs[or], datas[i].childs.length);
								}
							}
							if(children[j].props.className =="tr-summary"){
								tr = this.renderTr(tr, children[j], datas[i]);
							}
						}

						/*if (datas[i].orderstatustype == "13") {
						 tbodyCls = "tbody-outOfDate"; //过期或者完成的订单
						 }*/
						tbody.push(React.createElement(
							JTbody,
							{ className: tbodyCls },
							" ",
							tr,
							" "
						));
					} else {
						//一单一货
						if (typeof children == "object" && children.length == 0) {
							tr = this.renderTr(tr, children, datas[i]);
						} else {
							for (var j = 0; j < children.length; j++) {
								//含有自定义格式化
								/*
								 if(children[j].props.dataFormat){
								 tr.push(<JTr dataFormat={children[j].props.dataFormat} data={datas[i]} ths={children[j].props.keys} colspan={children[j].props.colspan} className={children[j].props.className}/>);
								 }else{
								 tr.push(<JTr data={datas[i]} ths={children[j].props.keys} colspan={children[j].props.colspan} className={children[j].props.className}/>);
								 }
								 */
								tr = this.renderTr(tr, children[j], datas[i]);
							}
						}
						if (datas[i].orderstatustype == "5") {
							tbodyCls = "tbody-outOfDate"; //过期或者完成的订单
						}

						tbody.push(React.createElement(
							JTbody,
							{ className: tbodyCls },
							" ",
							tr,
							" "
						));
					}

					//console.log(datas[i].isMultil,datas[i].childs)
				}
			}
		}

		var tableClassName = "table orderTable";
		if (this.options.class) {
			tableClassName = tableClassName + " " + this.options.class;
		}

		return React.createElement(
			"div",
			null,
			top_pagination,
			React.createElement(
				"table",
				{ className: tableClassName },
				React.createElement(JThead, { thcols: this.thcols }),
				tbody
			),
			bottom_pagination
		);
	}
}