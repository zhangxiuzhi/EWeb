

var _extends = Object.assign || function (target) {
      for (var i = 1; i < arguments.length; i++) {
        var source = arguments[i];for (var key in source) {
          if (Object.prototype.hasOwnProperty.call(source, key)) {
            target[key] = source[key];
          }
        }
      }return target;
    };

var SizePerPageDropDown = function (_Component) {
  _inherits(SizePerPageDropDown, _Component);

  function SizePerPageDropDown() {
    _classCallCheck(this, SizePerPageDropDown);

    return _possibleConstructorReturn(this, (SizePerPageDropDown.__proto__ || Object.getPrototypeOf(SizePerPageDropDown)).apply(this, arguments));
  }

  _createClass(SizePerPageDropDown, [{
    key: 'render',
    value: function render() {
      var _props = this.props,
          open = _props.open,
          hidden = _props.hidden,
          onClick = _props.onClick,
          options = _props.options,
          className = _props.className,
          variation = _props.variation,
          btnContextual = _props.btnContextual,
          currSizePerPage = _props.currSizePerPage;


      var openClass = open ? 'open' : '';
      var dropDownStyle = { visibility: hidden ? 'hidden' : 'visible' };

      return React.createElement(
          'span',
          { className: variation + ' ' + openClass + ' ' + className, style: dropDownStyle },
          React.createElement(
              'button',
              { className: 'btn ' + btnContextual + ' dropdown-toggle',
                id: 'pageDropDown', 'data-toggle': 'dropdown',
                'aria-expanded': open,
                onClick: onClick },
              currSizePerPage,
              React.createElement(
                  'span',
                  null,
                  ' ',
                  React.createElement('span', { className: 'caret' })
              )
          ),
          React.createElement(
              'ul',
              { className: 'dropdown-menu', role: 'menu', 'aria-labelledby': 'pageDropDown' },
              options
          )
      );
    }
  }]);

  return SizePerPageDropDown;
}(React.Component);

SizePerPageDropDown.propTypes = {
  open: React.PropTypes.bool,
  hidden: React.PropTypes.bool,
  btnContextual: React.PropTypes.string,
  currSizePerPage: React.PropTypes.string,
  options: React.PropTypes.array,
  variation: React.PropTypes.oneOf([ 'dropdown', 'dropup' ]),
  className: React.PropTypes.string,
  onClick: React.PropTypes.func
};
SizePerPageDropDown.defaultProps = {
  open: false,
  hidden: false,
  btnContextual: 'btn-default',
  variation: 'dropdown',
  className: ''
};