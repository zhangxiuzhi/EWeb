import React from 'react';
import ReactDOM from 'react-dom';

export default class TreeListHead extends React.Component{
	constructor(props){
		super(props);
	}
	render(){
		return <li className="tree-header"><h5 className="nav-title">{this.props.text}</h5></li>
	}
}
