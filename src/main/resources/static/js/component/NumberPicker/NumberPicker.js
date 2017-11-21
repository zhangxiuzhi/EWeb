/**
 * Created by wzj on 2017/9/7.
 */
import React from 'react';
import ReactDOM from 'react-dom';
import Protypes from 'prop-types';
import classNames from 'classnames';

import './NumberPicker.scss';

export default class NumberPicker extends React.Component{

	constructor(props){
		super(props);

		this.state = {
			numValue:props.numValue ? props.numValue : 0
		}

		this.handleDownClick = this.handleDownClick.bind(this);
		this.handleUpClick = this.handleUpClick.bind(this);
	}

	//减少
	handleDownClick(){
		this.propagateStateChange(this.state.numValue - 1);
	}

	//增加
	handleUpClick(){
		this.propagateStateChange(this.state.numValue + 1);
	}

	propagateStateChange(num){
		if(num >= 0){
			this.setState({
				numValue:num
			});
			if(this.props.onChange){
				this.props.onChange(num);
			}
		}
	}

	render(){


		var LR_L = <div className="input-group-btn">
						<button className="btn minus" onClick={this.handleDownClick}><i className="fa fa-minus"></i></button>
			          </div>;

		var LR_R = <div className="input-group-btn">
						<button className="btn plus" onClick={this.handleUpClick}><i className="fa fa-plus"></i></button>
					  </div>;

		var RUD = <div className="input-group-btn layout-updown">
						<button className="btn up" onClick={this.handleUpClick}><i className="fa fa-caret-up"></i></button>
						<button className="btn down" onClick={this.handleDownClick}><i className="fa fa-caret-down"></i></button>
					</div>

		const dir = this.props.direction;
		if(dir == "LR"){
			RUD = null;
		}
		if(dir == "RUD") {
			LR_L = null;
			LR_R = null;
		}

		const classes = classNames('react-number-picker',"input-group",this.props.className)

		return (
			<div className={classes}>
				{LR_L}
				<input type="text" className="form-control" value={this.state.numValue}/>
				{LR_R}
				{RUD}
			</div>
		);
	}
}

NumberPicker.defaultProps={
	direction:"LR" //右侧上下排放
}

NumberPicker.propTypes  = {
	className: Protypes.string,
	direction: Protypes.string,  	//按钮方向
	// LR:左右排放
	// RUD:右侧上下排放
	onChange: Protypes.func	//	自定义回调
}