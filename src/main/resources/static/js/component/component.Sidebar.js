/**
 * Created by wzj on 2017/11/1.
 */

class ComponentSidebar extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			data: [
				{ name: "index", text: "首页", url:""},
				{ name: "addOffer", text: "发布报盘", url:"addOffer.html"},
				{ name: "myOffer", text: "我的报盘记录", url:"myOffer.html"},
				{ name: "myPurchase", text: "我的采购", url:"myPurchase.html"},
				{ name: "mySale", text: "我的销售", url:"mySale.html"}
			],
			focusNode:this.props.focusNode
		};
	}

	render() {
		const data = this.state.data;
		const fn = this.state.focusNode;
		return React.createElement(Sidebar, { data: data, className: "nav-main", focusNode:fn} );
	}
}