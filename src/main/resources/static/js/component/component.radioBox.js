/**
 * Created by wzj on 2017/11/14.
 */



class ComponentRadioBox extends React.Component{

	constructor(props){
		super(props)

		/*
		 *
		 * */
		this.state = {
			data: [
					{id: "tradeType-all", text: "全部", value: "all",name:"tradeType"},
					{id: "tradeType-inStock", text: "港口现货", value: "inStock",name:"tradeType"},
					{id: "tradeType-future", text: "远期现货", value: "future",name:"tradeType"},
					{id: "tradeType-pricing", text: "点价", value: "pricing",name:"tradeType"}
			]
		}
	}

	onChange(value,label){

	}

	render(){
		const datas = this.state.data;

		return React.createElement(RadioBox,{data:datas,className:this.props.className, value:this.props.value, onChange:this.onChange});

	}
}
