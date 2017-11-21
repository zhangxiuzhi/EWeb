import classNames from 'classnames';
import Protypes from 'prop-types';
import React from 'react';
import ReactDOM from 'react-dom';

export default class TreeListNode extends React.Component{

	constructor(props){
		super(props);

		this.state={
			dataName:props.dataName,
			text:props.text,		//文本
			url:props.url,			//url
			opened:false,//props.opened,		//是否打开
			loaded:false,
			childrenNode:props.children || [],	//子节点
			remote:props.remote ? true : false						//远程节点
		}

		//帮点展开或关闭子节点事件
		this.onToggleChildren  = this.onToggleChildren.bind(this);
		//为当前节点添加子节点
		this.addChildNodeList = this.addChildNodeList.bind(this);
	}

//DOM加载完成
	componentDidMount(){
		//console.log("node load finish",this.state.dataName,this.state.actived)
	}
	//更新后
	componentDidUpdate(){
		//console.log("node update finish",this.state.dataName,this.state.actived)
		console.log("node update")
	}

	onToggleChildren (event){

		console.log(this.props)

		//如果含有子节点就展开或关闭
		if(this.state.childrenNode && this.state.childrenNode.length > 0){
			this.setState({
				opened:!this.state.opened
			});
		} else if (this.state.url && this.state.url != ""){
			//跳转页面
			//未含子节点的，则跳转url
			//window.location.href = this.state.url;
		}else{
			/*this.setState({
				actived:true
			});*/
			if(this.state.remote){
				this.setState({
					loaded:true		//开关图标变成加载图标
				})
			}
			//节点上的回调
			//回调treeList
			if(this.props.handleNodeClick){
				this.props.handleNodeClick(event,this);
			}
		}

		//子节点展开收缩
		/*var $this = $(e.target);
		$this.is('a') || ($this = $this.closest('a'));
		var $active = $this.parent().siblings( ".active" );
		$active && $active.toggleClass('active').find('> ul:visible').stop().slideUp(200);

		($this.parent().hasClass('active') && $this.next().stop().slideUp(200)) || $this.next().stop().slideDown(200);
		$this.parent().toggleClass('active');

		$this.next().is('ul') && e.preventDefault();*/

		//console.log("node click",this.refs.treeNode,this.props)



	}

	addChildNodeList(nodes){
		console.log(nodes)
		this.setState(nodes)
	}

	///解析节点，并构建子节点
	parserChildItem(index,_node){
		var nodes = [],node_child = [],
			nodeProp = {
				className:"",	//节点class
				//opened:false,		//打开状态
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

		const nodeProp = this.props;
		console.log(nodeProp)

		var childnodes = [];
		if(nodeProp.children && nodeProp.children.length>0){
			for(var i=0;i<nodeProp.children.length;i++){
				childnodes.push(this.parserChildItem(i,nodeProp.children[i]))
			}
		}

		//节点class
		const nodeClasses = classNames("tree-node",{
			"parentNode":childnodes.length >0 || nodeProp.remote ,
			"opened": this.state.opened,
			"actived": this.state.actived
		},this.props.className);

		//console.log(this.state.dataName,nodeClasses)
		//是否含有有子节点的，箭头图标
		const toggleClasses = classNames("toggleIcon", {
			"fa fa-angle-right":childnodes.length>0 || nodeProp.remote,
			"expanded":this.state.expanded,
			"loaded":this.state.loaded
		});

		return (
			<li ref="treeNode" className={nodeClasses}>
				<a  href="javascript:void(0)" onClick={this.onToggleChildren}>
					<i></i>
					<span>{this.state.text}</span>
					<i ref="toggleIcon" className={toggleClasses}></i>
				</a>
				<ul className="nav nav-sub" ref="childrenWrap">

					{childnodes}
				</ul>
			</li>
		);
	}

}


