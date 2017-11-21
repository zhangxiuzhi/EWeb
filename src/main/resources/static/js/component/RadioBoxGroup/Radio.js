/**
 * Created by wzj on 2017/11/14.
 */

import React from 'react';
import ReactDOM from 'react-dom';
import classNames from 'classnames';


export default class Radio extends React.Component{
	constructor(props) {
		super(props);

		this.handleClick = this.handleClick.bind(this);



	}

	//绑定标签点击等于radio点击
	handleClick(event){
		const label = this.refs.label;
		if(event.target !== label){
			event.preventDefault();
			label.click();
			return
		}

	}


	render(){
		const radio = this.props.data;
		const checkedOption = this.props.checked;

		const classes = classNames('radio-inline',{
			"checked":this.props.isChecked == radio.value ? true : false
		});

		const inputId = this.props.groupId + "-" +radio.value;

		return (
			<div className={classes} key={radio.id || index} >
				<input className="react-radioBox-screenreader-only" type="radio"
					onChange = {this.props.onChange}
					checked = {this.props.isChecked == radio.value ? true : false}
					id={inputId}
					name={radio.name} value={radio.value}
					label={radio.text}
					ref="radio"/>
				<div className="radio" onClick={this.handleClick}><div className="point"></div></div>
				 <label htmlFor={inputId} ref="label">{radio.text}</label>
			</div>
			)
	}

}