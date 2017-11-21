/**
 * Created by wzj on 2017/10/31.
 */

import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import SNode from './SNode';

import './Sidebar.scss';

export default class Tree extends React.Component {
	constructor(props) {
		super(props);

	}


	render(){
		const { className, name, id, focusNode } = this.props
		const classes = classNames('react-sidebar',className);

		const {data: propsData, onToggle} = this.props;
		let data = propsData;

		return(
			<div className={classes}>
				<ul className="nav-main">
				{data.map((node,index)=>
					<SNode key={node.id || index} node={node} focusNode={focusNode}/>
				)}
				</ul >
			</div>
		)
	}
}