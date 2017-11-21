/**
 * Created by wzj on 2017/6/7.
 */

/**
 * 顶部菜单
 *
 *
 *
 *
 *
 *
 *
 *
 */


/*--------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------*/
//
class ComponentHeaderMenu extends React.Component {

	constructor(props) {
		super(props);
		//初始数据
		this.state = {
			data: {
				main: {name: "index", text: "全部商品",children:[
					{name:"",text:"品种1",children:[
						{name:"",text:"a",url:""},
						{name:"",text:"b",url:""},
					]},
					{name:"",text:"品种2",children:[]},
					{name:"",text:"品种3",children:[]},
					{name:"",text:"品种3",children:[]},
					{name:"",text:"品种4",children:[]}
				]},
				nav: [
					{name: "index", text: "首页", tag: "",url:"/demo/index.html"},
					{name: "marker", text: "交易", tag: "hot",url:"/demo/view/marker/marker.html"},
					{name: "1", text: "招标", tag: ""},
					{name: "2", text: "意向", tag: ""},
					{name: "3", text: "资讯", tag: ""},
					{name: "3", text: "数据", tag: "new"}
				]
			},
			focusNode:{
				name:"marker",
				text:"交易"
			}
		}
	}
	/*******************************************************************/
	componentWillMount(){

	}
	//DOM加载完成
	componentDidMount(){

	}
	/*******************************************************************/

	ajaxRequestData() {

	}
	/*******************************************************************/

	render() {
		const data = this.state.data;
		const fn = this.state.focusNode;
		return React.createElement(HeaderMenu, { data: data, className: "esteel-headerMenu", focusNode:fn} );
	}
}

