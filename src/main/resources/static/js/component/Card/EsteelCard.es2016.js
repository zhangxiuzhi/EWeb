/**
 * Created by wzj on 2017/11/2.
 */

class EsteelCard extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			data: props.data
		};
	}

	//渲染不同类型的卡片内容
	renderCardType(card, type) {
		if (type == "f") {
			return this.renderCardF(card);
		}
		if (type == "s") {
			return this.renderCardS(card);
		}
		if (type == "p") {
			return this.renderCardP(card);
		}
	}
	//期货
	renderCardF(cc) {
		return React.createElement(
			"div",
			{ className: "cc" },
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u4EF7\u683C\uFF08\u7F8E\u5143/\u5E72\u5428\uFF09"
			),
			React.createElement(
				"div",
				{ className: "cc-value price" },
				cc.price
			),
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u54C1\u4F4D\uFF08%\uFF09\u4E28\u53EF\u4EA4\u6613\u91CF\uFF08\u6E7F\u5428\uFF09"
			),
			React.createElement(
				"div",
				{ className: "cc-value" },
				cc.grade,
				" | ",
				cc.number
			),
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u8FD0\u8F93\u72B6\u6001"
			),
			React.createElement(
				"div",
				{ className: "cc-value" },
				"\u88C5\u8239\u671F:",
				cc.transportStatus
			)
		);
	}
	//现货
	renderCardS(cc) {
		return React.createElement(
			"div",
			{ className: "cc" },
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u4EF7\u683C\uFF08\u7F8E\u5143/\u5E72\u5428\uFF09"
			),
			React.createElement(
				"div",
				{ className: "cc-value price" },
				cc.price
			),
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u6E2F\u53E3 | \u54C1\u4F4D\uFF08%\uFF09\u4E28\u53EF\u4EA4\u6613\u91CF\uFF08\u6E7F\u5428\uFF09"
			),
			React.createElement(
				"div",
				{ className: "cc-value" },
				cc.port,
				" | ",
				cc.grade,
				" | ",
				cc.number
			),
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u8FD0\u8F93\u72B6\u6001"
			),
			React.createElement(
				"div",
				{ className: "cc-value" },
				cc.breakUp
			)
		);
	}
	//点价
	renderCardP(cc) {
		return React.createElement(
			"div",
			{ className: "cc" },
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u4EF7\u683C\uFF08\u7F8E\u5143/\u5E72\u5428\uFF09"
			),
			React.createElement(
				"div",
				{ className: "cc-value price" },
				cc.price
			),
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u6E2F\u53E3 \u4E28\u53EF\u4EA4\u6613\u91CF\uFF08\u6E7F\u5428\uFF09"
			),
			React.createElement(
				"div",
				{ className: "cc-value" },
				cc.port,
				" | ",
				cc.number
			),
			React.createElement(
				"div",
				{ className: "cc-label" },
				"\u70B9\u4EF7\u671F\u4E28\u4EA4\u8D27\u671F"
			),
			React.createElement(
				"div",
				{ className: "cc-value" },
				React.createElement(
					"p",
					null,
					cc.offerDate
				),
				React.createElement(
					"p",
					null,
					cc.dealDate
				)
			)
		);
	}

	//卡片类型标签
	renderCartTypeTag(type) {
		if (type == "f") {
			return React.createElement(
				"span",
				{ className: "tag tag-f" },
				"\u8FDC"
			);
		}
		if (type == "s") {
			return React.createElement(
				"span",
				{ className: "tag tag-s" },
				"\u73B0"
			);
		}
		if (type == "p") {
			return React.createElement(
				"span",
				{ className: "tag tag-p" },
				"\u70B9"
			);
		}
	}

	render() {
		const { className, name, id, focusNode } = this.props;
		const classes = classNames('card-wrap clearfix', className);

		const { data: propsData, onToggle } = this.props;
		let data = propsData;

		return React.createElement(
			"div",
			{ className: classes },
			data.map((card, index) => React.createElement(
				"div",
				{ key: card.id || index, className: "card-item" },
				React.createElement(
					"div",
					{ className: "card-type" },
					this.renderCartTypeTag(card.type),
					React.createElement(
						"span",
						{ className: "tag tag-a" },
						"\u5B9A"
					)
				),
				React.createElement(
					"div",
					{ className: "card-title" },
					React.createElement("div", { className: "image" }),
					React.createElement(
						"div",
						{ className: "name" },
						card.commodityName
					),
					React.createElement(
						"div",
						{ className: "subname" },
						React.createElement("span", { className: "name-icon" }),
						card.company
					)
				),
				React.createElement(
					"div",
					{ className: "card-content" },
					this.renderCardType(card, card.type)
				),
				React.createElement(
					"div",
					{ className: "card-time" },
					"\u5269\u4F59\u65F6\u95F4:",
					card.expiryDate
				)
			))
	);
	}

}