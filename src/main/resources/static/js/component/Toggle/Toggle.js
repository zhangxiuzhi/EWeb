/**
 * Created by WangZhenJia(159109799@qq.com) on 2017/9/5.
 */

class Toggle extends React.PureComponent {

	constructor(props) {
		super(props)

		this.state = {
			//checked: !!(props.checked || props.defaultChecked)
			checked:props.defaultChecked,
		};

		this.handleClick = this.handleClick.bind(this);
	}

	handleClick(event) {
		const checkbox = this.input;

		if (event.target !== checkbox) {
			event.preventDefault();
			checkbox.click();
			return;
		}
		//如果组件有checked属性，否则取checkbox的checked属性
		//const checked = this.props.hasOwnProperty('checked') ? this.props.checked : checkbox.checked;

		//设置state
		this.setState({ checked: checkbox.checked });
	}

	render() {
		const { className, id, name, defaultChecked, onChange } = this.props;
		const classes = classNames('react-toggle', {
			'react-toggle--checked': this.state.checked,
			'react-toggle--disabled': this.props.disabled
		}, className);

		return React.createElement(
			'div',
			{ className: classes,
				onClick: this.handleClick
			},
			React.createElement('div', { className: 'toggle-track' }),
			React.createElement('div', { className: 'toggle-thumb' }),
			React.createElement('input', {
				id: id,
				name: name,
				value:this.state.checked == true ? "1" : "0",
				defaultChecked:defaultChecked,
				onChange:onChange,
				ref: ref => {
					this.input = ref;
				},
				className: 'react-toggle-screenreader-only',
				type: 'checkbox'
			})
		);
	}
}

/*
Toggle.propTypes = {
	className: Protypes.string,
	value: Protypes.string,
	name: Protypes.string,
	id: Protypes.string,
	onChange: Protypes.func,
	checked: Protypes.bool,
	defaultChecked: Protypes.bool,
	disabled: Protypes.bool
};*/
