/**
 * Created by WangZhenJia(159109799@qq.com) on 2017/9/5.
 */


class ComponentToggle extends React.Component{

	constructor(props){
		super(props)

		this.ui_toggle_onChange = this.ui_toggle_onChange.bind(this);
	}

	//toggle 开关自定义事件
	ui_toggle_onChange(event){
		var target = event.target;
		target.checked
		if(this.props.onChange){
			this.props.onChange(target.checked);
		}
	}


	render(){
		return React.createElement(Toggle, {defaultChecked:true, name:this.props.inputName, onChange:this.ui_toggle_onChange});
	}
}


//toggle
var toggleProps_1 = {
	//className:'wzj-ui-toggle'	//自定义class
	//,defaultChecked:true		//默认选中
	//,disabled:true
}