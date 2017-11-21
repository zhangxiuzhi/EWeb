/**
 * Created by wzj on 2017/9/8.
 */

import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import TreeNode from './TreeNode.js';

import './Tree.scss';

export default class Tree extends React.Component {
	constructor(props){
		super(props);

	}
	render() {
		const { className, name, id } = this.props
		const classes = classNames('react-tree',className);

		const {data: propsData, onToggle} = this.props;
		let data = propsData;

		if (!Array.isArray(data)) {
			data = [data];
		}
		return (
			<div className={classes} name={name} id={id}>
				<ul ref={ref => this.treeBaseRef = ref} className="nav">
					{data.map((node, index) =>
					<TreeNode
						key={node.id || index}
						node={node}
						onToggle={onToggle}
						/>
					)}
				</ul>
			</div>
		);
	}
}

Tree.propTypes = {
	onToggle: PropTypes.func
};