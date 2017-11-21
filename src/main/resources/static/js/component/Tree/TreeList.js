import React from 'react';
import ReactDOM from 'react-dom';
import TreeListHead from './TreeListHead.js';
import TreeListNode from './TreeListNode2.js';

export default class TreeList extends React.Component{

	constructor(props){
		super(props);

		this.state = {
			head: this.props.treeHead ? this.props.treeHead : this.props.data.head,
			items:this.props.data.items
		}

	}


	//解析节点，并构建子节点
	parserChildItem(index,_node){
		var nodes = [],node_child = [],
			nodeProp = {
				className:"",	//节点class
				opened:false,		//打开状态
				actived:false,		//选中状态
				dataName:_node.name,
				text:_node.text,	//文本
				url:_node.url,		//url
				remote:_node.remote	//远程节点
			};
		//节点是否为当前选中节点
		if(this.props.currentNode!=null) {
			if ( _node.name == this.props.currentNode.name) {
				nodeProp.actived = true;
			}else{
				nodeProp.actived = false;
			}
		}
		//节点是否含有子节点
		if(_node.children && _node.children.length>0){
			var nchild = _node.children;
			for(var i=0;i<nchild.length;i++){
				node_child.push(this.parserChildItem(i,nchild[i]));
			}
		}
		//console.log(nodeProp)
		nodes.push(<TreeListNode key={index} {...nodeProp} childrenNode={node_child} handleNodeClick={this.props.handleNodeClick}/>);

		/*if(_node.children && _node.children.length>0){
			var nchild = _node.children;
			for(var i=0;i<nchild.length;i++){
				node_child.push(this.parserChildItem(i,nchild[i]));
			}
			n = <TreeListNode key={index} text={_node.text} url={_node.url} childrenNode={node_child} itemClick={this.itemClickToItemList}/>;
		}*/

		return nodes;
		/*
		return _children.map((child,index)=>
			<TreeListNode key={index} text={child.text} children={child.children?child.children:null} url={child.url} />
		);
		*/
	}

	render(){
		const list_nodes = []
		if(this.state.items!=null){
			var items = this.state.items;
			for(var i=0;i<items.length;i++){
				//list_nodes.push(this.parserChildItem(i,items[i]));
				list_nodes.push(<TreeListNode key={i} {...items[i]}/>);
			}
		}

		return (
			<ul className="nav">
				<TreeListHead text={this.state.head}/>
				{list_nodes}
			</ul>
		);
	}

}
