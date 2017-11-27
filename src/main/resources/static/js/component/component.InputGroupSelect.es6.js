"use strict";

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

/**
 * Created by wzj on 2017/3/20.
 */

/**
 * 输入框组
 * 包含:
 * 左边：按钮，下拉框，单选，复选，图标
 * 右侧：按钮
 *
 *
 * 属性
 * 	leftAddon 左侧组件类型  //icon,select,checkbox,radio
 * 	leftAddonData 左侧组件数据	//用于select
 *
 *  rightAddon 右侧组件类型 //button
 *	rightAddonText 右侧按钮描述
 *  rightAddonEvent	右侧按钮自定义事件
 *
 */

/*--------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------*/
//
var InputGroupSelect = function (_React$Component) {
	_inherits(InputGroupSelect, _React$Component);

	function InputGroupSelect(props) {
		_classCallCheck(this, InputGroupSelect);

		var _this = _possibleConstructorReturn(this, _React$Component.call(this, props));

		_this.right_btn = true; //右侧插件
		_this.left_addon = false; //左侧插件

		_this.select = null;

		//右侧插件
		_this.rightAddon = _this.props.rightAddon ? _this.props.rightAddon : "button"; //
		_this.rightAddonIcon = _this.props.rightAddonIcon ? _this.props.rightAddonIcon : "fa fa-caret-down";
		_this.rightAddonText = _this.props.rightAddonText ? _this.props.rightAddonText : false; //

		_this.multiple = _this.props.multiple == "true" ? true : false; //是否多选
		//初始数据
		_this.state = {
			data: []
		};

		_this.handleRightAddonClick = _this.handleRightAddonClick.bind(_this);
		_this.handleLeftAddonClick = _this.handleLeftAddonClick.bind(_this);
		return _this;
	}
	/*******************************************************************/
	//DOM加载完成


	InputGroupSelect.prototype.componentDidMount = function componentDidMount() {
		//生成select2组件
		this.renderSelect();
		if (this.props.customRenderFinishCallBack) {
			this.props.customRenderFinishCallBack();
		}
	};
	//完成渲染新的props或者state后调用


	InputGroupSelect.prototype.componentDidUpdate = function componentDidUpdate() {};
	/*******************************************************************/
	//右侧按钮点击


	InputGroupSelect.prototype.handleRightAddonClick = function handleRightAddonClick() {
		if (this.props.rightAddonClick) {
			//this.props.rightAddonClick(this);
		}
		this.select.select2("open");
	};
	//左侧按钮点击


	InputGroupSelect.prototype.handleLeftAddonClick = function handleLeftAddonClick() {};

	/*******************************************************************/
	//生成select2组件


	InputGroupSelect.prototype.renderSelect = function renderSelect() {
		var thisProps = this.props;
		var marginLeft = $(this.refs.iGLabel).width();
		var width = $(this.refs.IGWrap).width() - $(this.refs.iGAddon).width() - 2;
		this.select = $(this.refs.iGSelect).css({ width: width }).select2({
			//maximumSelectionLength: 4,
			containerCss: {
				marginLeft: marginLeft + 15
			},
			//language: "zh-CN",
			language: {
				errorLoading: function errorLoading() {
					return "无法载入结果。";
				}, inputTooLong: function inputTooLong(e) {
					var t = e.input.length - e.maximum,
						n = "请删除" + t + "个字符";return n;
				}, inputTooShort: function inputTooShort(e) {
					var t = e.minimum - e.input.length,
						n = "请再输入至少" + t + "个字符";return n;
				}, loadingMore: function loadingMore() {
					return "载入更多结果…";
				}, maximumSelected: function maximumSelected(e) {
					var t = "最多只能选择" + e.maximum + "个项目";return t;
				}, noResults: function noResults() {
					return thisProps.noResults ? thisProps.noResults  : "未找到结果";
				}, searching: function searching() {
					return "搜索中…";
				}
			},
			//dir: "rtl",
			tags: true
			,createTag: function (params) {
				// Don't offset to create a tag if there is no @ symbol
				if (params.term.indexOf('@') === -1) {
					// Return null to disable tag creation
					return null;
				}
				return {
					id: params.term,
					text: params.term
				}
			}
		});
		//自定义选中事件
		this.select.on('select2:select', function (evt) {
			if (this.props.onSelect) {
				this.props.onSelect(evt.currentTarget);
			}
		}.bind(this
			/*data:[{ id: 1, text: "Ford"     },
			 { id: 2, text: "Dodge"    },
			 { id: 3, text: "Mercedes" },
			 { id: 4, text: "Jaguar"   }]*/
		));
	};
	//设置select值


	InputGroupSelect.prototype.setSelectValue = function setSelectValue(value) {
		this.select.val(value);
		this.select.trigger('change');
	};
	//获取select值


	InputGroupSelect.prototype.getSelectValue = function getSelectValue() {
		return this.select.val();
	};
	//获取数据集


	InputGroupSelect.prototype.getDataList = function getDataList() {
		return this.props.data;
	};
	/*******************************************************************/


	InputGroupSelect.prototype.render = function render() {
		//配置
		var rightAddonIcon = "",
			rightAddonText = "",
			rightAddon;

		if (this.rightAddonIcon != false) {
			rightAddonIcon = "fa " + this.rightAddonIcon;
		}
		if (this.rightAddonText != false) {
			rightAddonText = this.rightAddonText;
		}
		rightAddon = React.createElement("div", { className: "input-group-btn", ref: "iGAddon" }, React.createElement("button", { className: "btn btn-default", type: "button", onClick: this.handleRightAddonClick }, React.createElement("i", { className: rightAddonIcon }), rightAddonText));

		//标签
		var label = this.props.formLabel ? React.createElement("label", { ref: "iGLabel", className: "control-label" }, this.props.formLabel) : null;

		//option
		var opts = [];
		for (var i = 0; i < this.props.data.length; i++) {
			opts.push(React.createElement("option", { value: this.props.data[i].value }, this.props.data[i].text));
		}

		//单选or多选
		var select = null;
		if (this.multiple) {
			select = React.createElement("select", { ref: "iGSelect", type: "text", name: this.props.formName, multiple: true, "data-validetta": this.props.validetta }, opts);
		} else {
			select = React.createElement("select", { ref: "iGSelect", type: "text", name: this.props.formName, "data-validetta": this.props.validetta }, opts);
		}
		//
		var cls = this.props.class + " component-InputGroupSelect";
		return React.createElement("div", { className: cls, ref: "IGWrap" }, label, React.createElement("div", { className: "input-group" }, select, rightAddon));
	};

	return InputGroupSelect;
}(React.Component);

/*! Select2 4.0.3 | https://github.com/select2/select2/blob/master/LICENSE.md */

(function () {
	if (jQuery && jQuery.fn && jQuery.fn.select2 && jQuery.fn.select2.amd) var e = jQuery.fn.select2.amd;return e.define("zh-CN", [], function () {
		return { errorLoading: function errorLoading() {
			return "无法载入结果。";
		}, inputTooLong: function inputTooLong(e) {
			var t = e.input.length - e.maximum,
				n = "请删除" + t + "个字符";return n;
		}, inputTooShort: function inputTooShort(e) {
			var t = e.minimum - e.input.length,
				n = "请再输入至少" + t + "个字符";return n;
		}, loadingMore: function loadingMore() {
			return "载入更多结果…";
		}, maximumSelected: function maximumSelected(e) {
			var t = "最多只能选择" + e.maximum + "个项目";return t;
		}, noResults: function noResults() {
			return "未找到结果";
		}, searching: function searching() {
			return "搜索中…";
		} };
	}), { define: e.define, require: e.require };
})();