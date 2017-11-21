import React from 'react';
import classNames from 'classnames';
import Protypes from 'prop-types';
import ReactDOM from 'react-dom';
import NavHead from './TreeListHead.js';
import NavItemList from './TreeList2.js';

import './Tree.scss';

export default class Tree extends React.Component{
	constructor(props){
		super(props);

		this.state = {
			data:props.data || [],				//数据
			currentNode:props.currentNode		//当前需要选中的节点
		}

		//节点点击
		this.onNodeClick = this.onNodeClick.bind(this);
		this.updateTreeData = this.updateTreeData.bind(this);
	}

	//DOM加载完成
	componentDidMount() {
		//当前选中节点的所有含有子节点的父节点 ul.nav.nav-sub
		var $navSubs = $("#main-NavMenu li.active").parents("ul.nav.nav-sub");
		//含有子节点的父节点的父节点 li
		/*$navSubs.each(function(index,navSub){
		 $(navSub).parent("li").addClass("active");
		 $(navSub).stop().slideDown(200);//
		 });*/
	//	console.log("load finish")
	}
	//更新后
	componentDidUpdate(){
		//console.log("update finish",this.state.currentNode)
	}

	//节点点击
	onNodeClick(event,node){
		//设置当前节点选中状态
		this.setCurrentNodeActived({name:node.state.dataName});

		if(this.props.onNodeClick){
			//属性中自定义事件(event,点击的节点,当前tree)
			this.props.onNodeClick(event,node,this);
		}
	}

	//设置当前节点选中状态
	setCurrentNodeActived(obj){
		this.setState({
			currentNode:obj
		})
	}
	updateTreeData(newData){
		this.setState({
			data:newData
		})
		this.forceUpdate();
	}
	render(){

		//onMenuClickEvent
		/*
		 <NavMenu onMenuClickEvent={}/>属性
		 */

		const { className, name, id } = this.props
		const classes = classNames('react-tree',{
			"react-tree-expandAll":this.props.defautlExpanded
		},className);

		const navs = this.state.data;

		return (
				<div className={classes} id={id} name={name}>
				{navs.map((nav,index) =>
					<NavItemList key={index} data={nav}  currentNode={this.state.currentNode} handleNodeClick={this.onNodeClick}/>
				)}
				</div>
		);
	}
}
Tree.defaultProps = {
	treeHead:""
}

Tree.propTypes  = {
	data:Protypes.array,				//数据
	className:Protypes.string,
	name:Protypes.string,
	id:Protypes.string,
	treeHead:Protypes.string,			//自定义标题
	currentNode:Protypes.object,		//当前选中节点
	defautlExpanded:Protypes.bool,		//默认是否全部展开
	onNodeClick:Protypes.func			//自定义节点点击回调
}
