/**
 * Created by wzj on 2017/11/9.
 */

import React from 'react';
import ReactDOM from 'react-dom';
import classNames from 'classnames';
import SortButton from './SortButton';

import './SortGroupButton.scss';

export default class SortGroupButton extends React.Component{

	/*
	 *  sort:asc升序,desc降序,none无
	 *
	 *  规则: asc -> desc -> none -> asc
	 * */

	constructor(props){
		super(props)

		this.onToggle = this.onToggle.bind(this);

		this.state = {
			data:props.data
		}
	}



	//获取排序按钮点击后的排序值
	onToggle(button){
		var data = this.state.data;
		for(var i=0;i<data.length;i++){
			//改变当前操作的按钮值
			if(data[i].id == button.sortId){
				data[i].sort = button.sort;
			}
		}

		//自定义事件，传递整个按钮组值
		if(this.props.onGroupToggle){
			this.props.onGroupToggle(data);
		}

	}


	render(){
		const { className, name, id , onToggle, onGroupToggle} = this.props
		const classes = classNames('react-sortGroupButton',className);

		const btns = this.props.data;

		return (
			<div className={classes}>
				<div className="btn-group" >
				{btns.map((btn,index)=>
					<SortButton key={index}
					data={btn}
					onToggle={this.onToggle}
					/>
				)}
				</div>
			</div>
		)
	}
}

