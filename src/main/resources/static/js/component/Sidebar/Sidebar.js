/**
 * Created by wzj on 2017/10/31.
 */


class Sidebar extends React.Component {

	constructor() {
		super();
	}
	render(){
		const { className, name, id, focusNode } = this.props;
		const classes = classNames('react-sidebar', className);

		const { data: propsData, onToggle } = this.props;
		let data = propsData;

		return React.createElement(
			"div",
			{ className: classes },
			React.createElement(
				"ul",
				{ className: "nav-list" },
				data.map((node, index) => React.createElement(SNode,
					{ key: node.id || index, node: node, focusNode:focusNode }))
			)
		);
	}
}