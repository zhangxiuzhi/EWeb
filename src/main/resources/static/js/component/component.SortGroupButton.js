/**
 * Created by wzj on 2017/11/9.
 */


class ComponentSortGroupButton extends React.Component{

	constructor(props){
		super(props)

		/*
		*  sort:asc升序,desc降序,none无
		* */
		this.state = {
			data:[]
		}
		this.value = [];
		this.onGroupToggle = this.onGroupToggle.bind(this);
	}

	//最终切换值
	onGroupToggle(data){
		this.value = data;
		//自定义方法，当排序按钮变化后
		if(this.props.onChange){
			this.props.onChange();
		}
	}

	render(){
		const data = this.props.data;

		return React.createElement(SortGroupButton, {data:data,  onGroupToggle:this.onGroupToggle});

	}
}