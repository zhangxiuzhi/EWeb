/**
 * Created by wzj on 2017/11/24.
 */

class MultiSelect extends React.Component {

	constructor(props) {
		super(props);

		this.onOptionClick = this.onOptionClick.bind(this);
		this.moveInAll = this.moveInAll.bind(this);
		this.moveIn = this.moveIn.bind(this);
		this.moveOutAll = this.moveOutAll.bind(this);
		this.moveOut = this.moveOut.bind(this);
		this.filterOptions = this.filterOptions.bind(this);
	}

	onOptionClick() {
		//console.log(this.refs.MSL, this.refs.MSL.value);
	}

	componentDidMount() {
		$(this.refs.MSL).bind('dblclick', function (event) {
			//绑定双击事件
			//获取全部的选项,删除并追加给对方
			$(event.target).remove();
			var option = new Option(event.target.text, event.target.value);
			option.selected = true;
			$(this.refs.MSR).append(option); //添加到到第二个select里面
			$(this.refs.MSR).children("option").prop("selected", false);

			if (this.props.onChange) {
				this.props.onChange(this.getMSRValue());
			}
		}.bind(this));

		$(this.refs.MSR).bind('dblclick', function (event) {
			//绑定双击事件
			//获取全部的选项,删除并追加给对方
			$(event.target).remove();
			var option = new Option(event.target.text, event.target.value);
			option.selected = true;
			$(this.refs.MSL).append(option); //添加到到第二个select里面
			$(this.refs.MSL).children("option").prop("selected", false);

			if (this.props.onChange) {
				this.props.onChange(this.getMSRValue());
			}
		}.bind(this));

		/*-------------------------------------*/

		$(this.refs.box).hover(function () {
			$(this).addClass("hover");
		}, function () {
			$(this).removeClass("hover");
		});
		$(this.refs.clickText).click(function () {
			$(this.refs.root).addClass("showMS");
		}.bind(this));
	}

	moveInAll() {
		$(this.refs.MSL).children("option").appendTo($(this.refs.MSR));
		$(this.refs.MSR).children("option").prop("selected", false);
		$(this.refs.hiddenValue).val(this.getMSRValue());
		if (this.props.onChange) {
			this.props.onChange(this.getMSRValue());
		}
	}
	moveIn() {
		$(this.refs.MSL).children("option:selected").appendTo($(this.refs.MSR));
		$(this.refs.MSR).children("option").prop("selected", false);
		$(this.refs.hiddenValue).val(this.getMSRValue());
		if (this.props.onChange) {
			this.props.onChange(this.getMSRValue());
		}
	}
	moveOutAll() {
		$(this.refs.MSR).children("option").appendTo($(this.refs.MSL));
		$(this.refs.MSL).children("option").prop("selected", false);
		$(this.refs.hiddenValue).val(this.getMSRValue());
		if (this.props.onChange) {
			this.props.onChange(this.getMSRValue());
		}
	}
	moveOut() {
		$(this.refs.MSR).children("option:selected").appendTo($(this.refs.MSL));
		$(this.refs.MSL).children("option").prop("selected", false);
		$(this.refs.MSR).children("option").prop("selected", false);
		$(this.refs.hiddenValue).val(this.getMSRValue());
		if (this.props.onChange) {
			this.props.onChange(this.getMSRValue());
		}
	}

	getMSRValue() {
		var opts = $(this.refs.MSR).children("option");
		var vals = [];
		for (var i = 0; i < opts.length; i++) {
			vals.push(opts[i].value);
		}
		return vals;
	}

	filterOptions() {
		var filterValue = this.refs.input.value;
		var $mslOpts = $(this.refs.MSL).children("option");
		for (var i = 0; i < $mslOpts.length; i++) {
			if ($mslOpts[i].text.indexOf(filterValue) == -1) {
				$mslOpts[i].style.display = "none";
			} else {
				$mslOpts[i].style.display = "";
			}
		}
	}

	render() {
		const orgData = this.props.data.orgList;
		const curData = this.props.data.curList;
		const mslData = [];
		const msrData = [];

		for (var i = 0; i < orgData.length; i++) {
			mslData.push(React.createElement(
				'option',
				{ value: orgData[i].value },
				orgData[i].text
			));
		}
		for (var j = 0; j < curData.length; j++) {
			msrData.push(React.createElement(
				'option',
				{ value: orgData[j].value },
				curData[j].text
			));
		}
		return React.createElement(
			'div',
			{ className: 'react-multiSelect', ref: 'ms' },
			React.createElement(
				'div',
				{ className: 'react-ms-top' },
				React.createElement('input', { className: 'react-ms-top-input form-control', type: 'text', ref: 'input', onKeyUp: this.filterOptions, placeholder: '\u8BF7\u8F93\u5165\u5173\u952E\u5B57\u67E5\u627E' }),
				React.createElement('input', { className: 'react-ms-top-text form-control', type: 'text', value: '\u5DF2\u9009\u62E9\u6210\u5458', disabled: 'true' })
			),
			React.createElement(
				'div',
				{ className: 'react-ms-bottom' },
				React.createElement(
					'div',
					{ className: 'react-ms-left' },
					React.createElement(
						'select',
						{ multiple: true, ref: 'MSL', className: 'react-msl', id: 'msl' },
						mslData
					)
				),
				React.createElement(
					'div',
					{ className: 'react-ms-btns' },
					React.createElement(
						'button',
						{ className: 'btn btn-default', onClick: this.moveInAll },
						React.createElement('span', { className: 'fa fa-angle-double-right' })
					),
					React.createElement(
						'button',
						{ className: 'btn btn-default', onClick: this.moveIn },
						React.createElement('span', { className: 'fa fa-angle-right' })
					),
					React.createElement(
						'button',
						{ className: 'btn btn-default', onClick: this.moveOutAll },
						React.createElement('span', { className: 'fa fa-angle-double-left' })
					),
					React.createElement(
						'button',
						{ className: 'btn btn-default', onClick: this.moveOut },
						React.createElement('span', { className: 'fa fa-angle-left' })
					)
				),
				React.createElement(
					'div',
					{ className: 'react-ms-right' },
					React.createElement(
						'select',
						{
							multiple: true,
							ref: 'MSR',
							className: 'react-msr',
							id: 'msr',
							//name:this.props.name,
						},
						msrData
					)
				)
			),
			React.createElement('input', { type: 'hidden', name: this.props.name, ref: 'hiddenValue'})
		);
	}
}