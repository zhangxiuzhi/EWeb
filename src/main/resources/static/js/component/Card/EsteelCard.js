/**
 * Created by wzj on 2017/11/2.
 */

import React from 'react';
import ReactDOM from 'react-dom';
import classNames from 'classnames';

import './EsteelCard.scss';
export default class EsteelCard extends React.Component {

	constructor(props) {
		super(props)

		this.state = {
			data:props.data
		}

	}

	//渲染不同类型的卡片内容
	renderCardType(card,type){
		if(type == "f"){
			return this.renderCardF(card);
		}
		if(type == "s"){
			return this.renderCardS(card);
		}
		if(type == "p"){
			return this.renderCardP(card);
		}

	}
	//期货
	renderCardF(cc){
		return(
			<div className="cc">
				<div className='cc-label'>价格（美元/干吨）</div>
				<div className='cc-value price'>{cc.price}</div>
				<div className='cc-label'>品位（%）丨可交易量（湿吨）</div>
				<div className='cc-value'>{cc.grade} | {cc.number}</div>
				<div className="cc-label">运输状态</div>
				<div className="cc-value">装船期:{cc.transportStatus }</div>
			</div>
		);
	}
	//现货
	renderCardS(cc){
		return(
			<div className="cc">
				<div className='cc-label'>价格（美元/干吨）</div>
				<div className='cc-value price'>{cc.price}</div>
				<div className='cc-label'>港口 | 品位（%）丨可交易量（湿吨）</div>
				<div className='cc-value'>{cc.port} | {cc.grade} | {cc.number}</div>
				<div className="cc-label">运输状态</div>
				<div className="cc-value">{cc.breakUp }</div>
			</div>
		);
	}
	//点价
	renderCardP(cc){
		return(
			<div className="cc">
				<div className='cc-label'>价格（美元/干吨）</div>
				<div className='cc-value price'>{cc.price}</div>
				<div className='cc-label'>港口 丨可交易量（湿吨）</div>
				<div className='cc-value'>{cc.port} | {cc.number}</div>
				<div className="cc-label">点价期丨交货期</div>
				<div className="cc-value"><p>{cc.offerDate}</p><p>{cc.dealDate}</p></div>
			</div>
		);
	}

	//卡片类型标签
	renderCartTypeTag(type){
		if(type == "f"){
			return <span className="tag tag-f">远</span>;
		}
		if(type == "s"){
			return <span className="tag tag-s">现</span>;
		}
		if(type == "p"){
			return <span className="tag tag-p">点</span>;
		}
	}

	render(){
		const { className, name, id, focusNode } = this.props
		const classes = classNames('card-wrap clearfix',className);

		const {data: propsData, onToggle} = this.props;
		let data = propsData;

		return (
			<div className={classes}>
			{data.map((card,index)=>
					<div key={card.id || index} className="card-item">
						<div className="card-type">
							{this.renderCartTypeTag(card.type)}
							<span className="tag tag-a">定</span>
						</div>
						<div className="card-title">
							<div className="image"></div>
							<div className="name">{card.commodityName}</div>
							<div className="subname"><span className="name-icon"></span>{card.company}</div>
						</div>
						<div className="card-content">
							{this.renderCardType(card,card.type)}
						</div>
						<div className="card-time">剩余时间:{card.expiryDate}</div>
					</div>
				)}
			</div>
		)
	}

}