/**
 * Created by wzj on 2017/10/31.
 */

class SNode extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			children: []
		};

		this.onNodeClick = this.onNodeClick.bind(this);
	}

	//加载子级
	renderChildNode() {
		const children = this.props.node.children;
		const focusNode = this.props.focusNode;

		if (Array.isArray(children)) {
			return React.createElement(
				"ul",
				null,
				children.map((child, index) => React.createElement(SNode, { key: child.id || index, node: child, focusNode: focusNode }))
		);
		} else {
			return null;
		}
	}

	onNodeClick() {
		if (this.refs.menuNode.className == "open") {
			this.refs.menuNode.className = null;
		} else {
			this.refs.menuNode.className = "open";
		}
	}

	render() {
		const { node: { name, text, url, children, toggled } } = this.props;
		const { focusNode: fn } = this.props;

		var isOpened = this.props.node.open ? "open" : null;
		const classes = classNames('', {
			"open": isOpened //父级是否展开
		});

		var isSelected = false;
		if (fn.name == name) {
			isSelected = true;
		} else {
			isSelected = false;
		}
		const classes_link = classNames('', {
			"nav-submenu": children && children.length > 0, //子节点类
			"selected": isSelected //是否选中
		});

		return React.createElement(
			"li",
			{ ref: "menuNode", className: classes },
			React.createElement(
				"a",
				{ className: classes_link,
					href: url,
					onClick: this.onNodeClick
				},
				text
			),
			this.renderChildNode()
		);
	}
}