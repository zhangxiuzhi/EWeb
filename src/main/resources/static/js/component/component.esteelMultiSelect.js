/**
 * Created by wzj on 2017/11/24.
 */

class ComponentEsteelMultiSelect extends React.Component {

	constructor(props) {
		super(props);
	}

	onChange(selectValue) {
		console.log(selectValue);
	}

	componentDidMount() {
		$(this.refs.box).hover(function () {
			$(this).addClass("hover");
		}, function () {
			$(this).removeClass("hover");
		});
		$(this.refs.clickText).click(function () {
			this.renderModal();
		}.bind(this));
	}


	render() {
		const data = this.props.data;
		return React.createElement(MultiSelect, {
			data: data,
			name:this.props.inputName,
			onChange: this.onChange
		});//React.createElement(MultiSelect, { onChange: this.onChange });

	}

}