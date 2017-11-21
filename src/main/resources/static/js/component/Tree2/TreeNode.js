/**
 * Created by wzj on 2017/9/8.
 */

import React from 'react';
import ReactDOM from 'react-dom';
import PropTypes from 'prop-types';
import classNames from 'classnames';

export default class TreeNode extends React.Component {

	constructor(props) {
		super(props);

		this.onNodeClick = this.onNodeClick.bind(this);
		this.addNewChildNode = this.addNewChildNode.bind(this);

		this.state = {
			toggled: props.node.toggled ? props.node.toggled : false //props.node.toggled		//是否打开
			,children: props.node.children  || []
		}

	}

	onNodeClick(event){
		const {node, onToggle} = this.props;

		this.setState({
			toggled: !this.state.toggled
		});

		if (onToggle) {
			onToggle(event, this );
		}
	}

	//添加子节点
	addNewChildNode(_child){
		this.setState({
			children:_child
		})
	}

	render(){
		const {node:{name,text,children,toggled}} = this.props;

		//节点class
		const nodeClasses = classNames("tree-node",{
			"parentNode":children,
			"toggled": this.state.toggled
		},this.props.className);

		//开关class
		const toggleClasses = classNames("toggleIcon",{
			"fa fa-angle-right": children
		})

		return (
			<li ref={ref => this.topLevelRef = ref} className={nodeClasses}>
				<a href="javascript:void(0)" onClick={this.onNodeClick}>
					<i className={toggleClasses}></i>
					{text}
				</a>
				{this.renderDrawer()}
			</li>
		);
	}

	renderDrawer(decorators, animations) {
		const {node:{name,text,toggled,remote}} = this.props;
		var hasChildren = this.state.children.length>0 ? true : false;

		//如果含有子节点
		if (hasChildren) {
			return this.renderChildren();
		}

		//如果可以远程加载
		if (remote) {
			return this.renderLoading();
		}

		return hasChildren ? this.renderChildren() : null

	}

	//绘制子节点
	renderChildren(decorators) {
		const {node:{name,text,toggled,remote},onToggle} = this.props;
		const children = this.state.children;

		if (!Array.isArray(children)) {
			children = children ? [children] : [];
		}

		return (
			<ul ref={ref => this.subtreeRef = ref} className="nav nav-sub">
				{children.map((child, index) =>
					<TreeNode key={child.id || index} node={child} onToggle={onToggle}/>
				)}
			</ul>
		);
	}

	//绘制加载提示
	renderLoading() {
		const {node:{name,text,children,toggled}} = this.props;

		return (
			<ul className="nav nav-sub">
				<li>
					<a>Loading...</a>
				</li>
			</ul>
		);
	}
}


TreeNode.propTypes = {
	node: PropTypes.object.isRequired,
	onToggle: PropTypes.func						//开关方式
};