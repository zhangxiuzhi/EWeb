/**
 * Created by WangZhenJia(159109799@qq.com) on 2017/9/5.
 */

class ComponentSelectBox extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			data: []
		};

		this.customChange = this.customChange.bind(this);
	}

	customChange(node) {
		if(this.props.onChange){
			this.props.onChange(node);
		}
	}

	render() {;
		const data = this.props.data ? this.props.data : this.state.data
		return React.createElement(SelectBox, {
			label: "",
			name:this.props.inputName,
			value:this.props.inputValue,
			data: data,
			filter: true,
			onChange: this.customChange
		})
	}
}