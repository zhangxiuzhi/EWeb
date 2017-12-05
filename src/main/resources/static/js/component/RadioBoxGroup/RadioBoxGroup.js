/**
 * Created by wzj on 2017/11/14.
 */

class RadioBoxGroup extends React.Component {
	constructor(props) {
		super(props);

		this.id = RadioBox.generateId();

		this.state = {
			currentValue: props.value
		};

		this.handleChange = this.handleChange.bind(this);
	}

	//选择切换后
	handleChange(event) {
		this.setState({
			currentValue: event.target.value
		});
		//自定义回调
		if (this.props.onChange) {
			this.props.onChange(event.target.value, event.target.getAttribute("label"));
		}
	}

	render() {
		const { className, name, id, onToggle } = this.props;
		const classes = classNames('react-radioBox', className);

		const data = this.props.data;

		const groupId = "react-radioBox" + this.id;

		return React.createElement(
			"div",
			{ className: classes },
			data.map((radio, index) => React.createElement(RadioBox, { data: radio, key: radio.id || index,
				groupId: groupId,
				name:name,
				onChange: this.handleChange,
				isChecked: this.state.currentValue }))
	);
	}
}

RadioBox.idGenerator = 1;
RadioBox.generateId = () => RadioBox.idGenerator++;