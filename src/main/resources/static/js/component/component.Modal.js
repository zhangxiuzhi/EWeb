//var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

/**
 * Created by wzj on 2017/4/10.
 */
/**
 * 模态框
 */
var Modal = React.createClass({
	displayName: 'Modal',

	//已插入真实 DOM 之后
	componentDidMount: function () {
		//初始化modal
		$(this.refs.root).modal({ backdrop: 'static', keyboard: false, show: true });
		//modal关闭后执行事件
		$(this.refs.root).on('hidden.bs.modal', this.handleHidden);
	},
	//更新之前
	componentWillUpdate: function () {},
	//更新之后
	componentDidUpdate: function () {},
	//已移出真实 DOM
	componentWillUnmount: function () {
		//console.log("2",this,this.refs)
	},

	render: function () {
		var button = [];
		var confirmButton = null;
		var cancelButton = null;
		var confirmButton1 = null;
		var confirmButton2 = null;

		if (this.props.confirm) {
			confirmButton = React.createElement(
				Button,
				{ className: 'btn-primary', onClick: this.handleConfirm },
				this.props.confirm
			);
		}
		if (this.props.cancel) {
			cancelButton = React.createElement(
				Button,
				{ className: 'btn-default', onClick: this.handleCancel },
				this.props.cancel
			);
		}
		if (this.props.confirm1) {
			confirmButton1 = React.createElement(
				Button,
				{ className: 'btn-primary', onClick: this.handleConfirm1 },
				this.props.confirm1
			);
		}
		if (this.props.confirm2) {
			confirmButton2 = React.createElement(
				Button,
				{ className: 'btn-primary', onClick: this.handleConfirm2 },
				this.props.confirm2
			);
		}

		var html = null;
		if (this.props.children.$$typeof) {
			html = this.props.children;
		} else {
			html = React.createElement('div', { dangerouslySetInnerHTML: { __html: this.props.children } });
		}

		//样式名
		var classname = 'modal fade in';

		//多个确认按钮
		if (this.props.multipleBtn) {
			button.push(cancelButton);
			button.push(confirmButton1);
			button.push(confirmButton2);
			classname += " multipleBtn";
		} else {
			button.push(cancelButton);
			button.push(confirmButton);
		}

		return React.createElement(
			'div',
			{ className: classname, ref: 'root' },
			React.createElement(
				'div',
				{ className: this.props.title == null ? "modal-dialog hideHeader" : "modal-dialog" },
				React.createElement(
					'div',
					{ className: 'modal-content' },
					React.createElement(
						'div',
						{ className: 'modal-header' },
						React.createElement(
							'button',
							{ className: 'close', type: 'button', onClick: this.handleCancel },
							'\xD7'
						),
						React.createElement(
							'h3',
							null,
							this.props.title
						)
					),
					React.createElement(
						'div',
						{ className: 'modal-body' },
						html
					),
					React.createElement(
						'div',
						{ className: 'modal-footer' },
						button
					)
				)
			)
		);
	},
	//打开模态窗
	open: function () {
		$(this.refs.root).modal("show");
	},
	//关闭模态框
	close: function () {
		$(this.refs.root).modal("hide");
		$(".modal-backdrop").removeClass("in").remove();
	},
	//确认框确定按钮事件
	handleConfirm: function () {
		if (this.props.onConfirm) {
			this.props.onConfirm();
		}
		this.close();
	},
	//确认框确定按钮事件
	handleConfirm1: function () {
		if (this.props.onConfirm1) {
			this.props.onConfirm1();
		}
		this.close();
	},
	//确认框确定按钮事件
	handleConfirm2: function () {
		if (this.props.onConfirm2) {
			this.props.onConfirm2();
		}
		this.close();
	},
	//确认框取消按钮事件
	handleCancel: function () {
		if (this.props.onCancel) {
			this.props.onCancel();
		}
		this.close();
	},
	handleHidden: function () {
		if (this.props.onHidden) {
			this.props.onHidden();
			this.close();
		}
	}
});
var Button = React.createClass({
	displayName: 'Button',

	render: function () {
		return React.createElement('a', _extends({}, this.props, {
			href: 'javascript:;',
			role: 'button', className: (this.props.className || '') + ' btn btn-lg' }));
	}
});