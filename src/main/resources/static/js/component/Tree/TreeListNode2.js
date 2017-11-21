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
			childrenNode:props.childrenNode || [],	//子节点
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
		//console.log(nodes)


		var dd=[];
		for(var i=0;i<nodes.length;i++){
			var nodeProp = {
				className:"",	//节点class
				opened:false,		//打开状态
				actived:false,		//选中状态
				dataName:nodes[i].name,
				text:nodes[i].text,	//文本
				url:nodes[i].url,		//url
				remote:false	//远程节点
			};
			dd.push(<TreeListNode key={i} {...nodeProp}/>)
		}
		this.setState({ childrenNode:dd})
		this.forceUpdate();
	}

	render(){

		const nodeProp = this.props;

		//节点class
		const nodeClasses = classNames("tree-node",{
			"parentNode":nodeProp.childrenNode.length >0 || nodeProp.remote ,
			"opened": this.state.opened,
			"actived": this.state.actived
		},this.props.className);

		//console.log(this.state.dataName,nodeClasses)
		//是否含有有子节点的，箭头图标
		const toggleClasses = classNames("toggleIcon", {
			"fa fa-angle-right":nodeProp.childrenNode.length>0 || nodeProp.remote,
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

					{nodeProp.childrenNode}
				</ul>
			</li>
		);
	}

}


