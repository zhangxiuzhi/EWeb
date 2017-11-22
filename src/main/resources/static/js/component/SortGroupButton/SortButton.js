/**
 * Created by wzj on 2017/11/9.
 */

class SortButton extends React.Component {

	/*
	 *  sort:asc升序,desc降序,none无
	 *
	 *  规则: asc -> desc -> none -> asc
	 * */

	constructor(props) {
		super(props);

		this.onNodeClick = this.onNodeClick.bind(this);

		this.state = {
			sortId: props.data.id,
			sort: props.data.sort //排序方式
		};
	}

	//更新后
	componentDidUpdate() {
		//console.log("update finish", this.state);
		//将当前排序值传给按钮组
		this.props.onToggle(this.state);
	}

	//点击
	onNodeClick(event) {
		const { onToggle } = this.props;

		//按升降序规则来切换
		var sort = "none";
		if (this.state.sort == "asc") {
			sort = "desc";
		}
		if (this.state.sort == "desc") {
			sort = "none";
		}
		if (this.state.sort == "none" || this.state.sort == "") {
			sort = "asc";
		}
		this.setState({
			sort: sort
		});
	}

	//根据内容返回类型
	returnSortIcon() {
		if (this.state.sort == "asc") {
			return "↑";
		}
		if (this.state.sort == "desc") {
			return "↓";
		}
		if (this.state.sort == "none" || this.state.sort == "") {
			return "↑↓";
		}
	}
	//渲染图标类型
	renderSortTypeIcon() {
		const classes = classNames("icon",this.state.sort);
		return React.createElement(
			"span",
			{ className: classes } //"icon fa"
			//,this.returnSortIcon()
		);
	}

	render() {
		const btn = this.props.data;
		const classes = classNames('btn btn-default', this.state.sort);

		return React.createElement(
			"button",
			{ className: classes, onClick: this.onNodeClick },
			btn.text,
			this.renderSortTypeIcon()
		);
	}
}