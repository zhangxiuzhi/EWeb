import React from 'react';
import ReactDOM from 'react-dom';

export default class PageButton extends React.Component {

  constructor(props) {
    super(props);
  }

  pageBtnClick = e => {
    e.preventDefault();
    this.props.changePage(e.currentTarget.textContent);
  }

  render() {
    /*const classes = classSet({
      'active': this.props.active,
      'disabled': this.props.disable,
      'hidden': this.props.hidden,
      'page-item': true
    });
    */
    var classes = this.props.active?"active":"";
        classes += this.props.disable? ' disabled':"";
        classes +=  this.props.hidden?' hidden': ""
        classes +=   ' page-item';

    return (
      <li className={ classes }>
        <a href='#' onClick={ this.pageBtnClick } className='page-link'>{ this.props.children }</a>
      </li>
    );
  }
}
