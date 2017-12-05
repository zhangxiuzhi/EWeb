/**
 * Created by wzj on 2017/11/1.
 */

class ComponentMemberSidebar extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			data: [
				{name:"userInfo", text:"个人资料", url:"/member/userInfo"},
				{ name: "accountSafe", text: "账号安全", url:"/safe/accountSafe"},
				{ name: "bind", text: "账号绑定", url:""},
				{ name: "sub", text: "子账号管理", url:""},
				{ name: "funds", text: "资金管理", url:""},
				{ name: "message", text: "消息中心", url:""},
				{ name: "note", text: "短信订阅", url:""}
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