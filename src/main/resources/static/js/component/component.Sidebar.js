/**
 * Created by wzj on 2017/11/1.
 */

class ComponentSidebar extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			data: [
				{name:"", text:"我的订单", children:[
					{ name: "myPurchase", text: "我的采购", url:"myPurchase.html"},
					{ name: "mySale", text: "我的销售", url:"mySale.html"}
				]},
				{ name: "", text: "我的报盘",children:[
					{ name: "addOffer", text: "发布报盘", url:"addOffer.html"},
					{ name: "myOffer", text: "报盘记录", url:"myOffer.html"},
				]},
				{ name: "", text: "我的招标",children:[
					{ name: "addBid", text: "发布招标", url:"addOffer.html"},
					{ name: "myBid", text: "招标记录", url:"myOffer.html"},
				]},
				{ name: "", text: "我的投标",children:[
					{ name: "bidStore", text: "投标标的仓库", url:"addOffer.html"},
					{ name: "bidList", text: "投标记录", url:"myOffer.html"},
				]},
				{ name: "", text: "我的意向",children:[
					{ name: "purpose", text: "发布意向", url:"addOffer.html"}
				]},
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