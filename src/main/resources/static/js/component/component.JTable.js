/**
 * Created by wzj on 2017/11/20.
 */

class ComponentJTable extends React.Component {

	constructor(props) {
		super(props);

		this.options = props.options;
		this.thcols = [];
		this.row0 = [''];
		this.row2 = [];
		this.state = {
			data: props.datas
		};
	}

	//DOM加载完成
	componentDidMount() {
		console.log()
	}

	render() {
		//数据
		var datas = this.state.data;
		var thead = this.options.thead;
		console.log(this.options);
		for (var i = 0; i < thead.length; i++) {
			this.thcols.push({
				dataField: thead[i].dataField,
				dataName: thead[i].dataName,
				width: thead[i].width
			});
			this.row2.push({
				dataField: thead[i].dataField,
				dataFormat: thead[i].dataFormat || null
			});
		}

		var options = {
			url: this.options.url,
			thcols: this.thcols,
			status: this.props.status,
			searchData: "",
			page: 1,
			sizePerPage: 10,
			nodata: { text: "没有交易报盘" }
			//loadingText:{dataFormat:listLoadingIconFormat},	//自定义列表加载动画
		};
		return React.createElement(
			JTable,
			{ options: options, datas: datas, ref: "jtable" },
			React.createElement(JTr, { className: "sep-row", keys: this.row0, colspan: this.row2.length }),
			React.createElement(JTr, { keys: this.row2 })
		);
	}
}