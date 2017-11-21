/**
 * Created by wzj on 2017/11/6.
 */



class HeaderMenu extends React.Component {

	constructor() {
		super();
	}



	//已插入真实 DOM 之后
	componentDidMount() {
		//显示主节点的子项
		var refs = this.refs;
		$(refs.allitem).hover(function(){
			$(refs.allitemList).show();
		}, function(){
			$(refs.allitemList).hide();
		});

		$(refs.allitemList).find(".item-li").hover(function(){
			$(this).addClass("hover");
		},function(){
			$(this).removeClass("hover");
		});
	}

	//主节点
	renderMainLink(link){
		return React.createElement(
			'li',
			{ className: 'nav-allitem', ref: 'allitem' },
			React.createElement(
				'a',
				{ href: '' },
				link.text
			),
			React.createElement(
				'div',
				{ className: 'allitem-list', ref: 'allitemList' },
				React.createElement(
					'ul',
					{ className: 'item' },
					link.children.map((sub, index) => this.renderMainLinkSub(sub, index))
				)
			)
		);
	}
	//主节点分支
	renderMainLinkSub(sub,index){
		var subchildren = null;
		var sub_icon = null;
		if (sub.children.length > 0) {
			sub_icon = React.createElement('span', { className: 'fa fa-angle-right' });
			subchildren = this.renderMainLinkSubNode(sub);
		}

		return React.createElement(
			'li',
			{ className: 'item-li', key: sub.id || index },
			React.createElement(
				'div',
				{ className: 'item-nav' },
				React.createElement('span', { className: '' }),
				React.createElement(
					'h3',
					null,
					sub.text
				),
				sub_icon
			),
			subchildren
		);
	}
	//主节点分支节点
	renderMainLinkSubNode(sub){
		return React.createElement(
			'div',
			{ className: 'item-list' },
			React.createElement(
				'ul',
				null,
				sub.children.map((li, index) => React.createElement(
					'li',
					{ key: li.id || index },
					li.text
				))
			)
		);
	}

	//节点
	renderLink(fn,link,index){
		const classes = classNames('nav-link', {
			"selected": fn.name == link.name ? true : false
		});

		var tag = null;
		if (link.tag) {
			const classes_tag = classNames('tag', 'tag-' + link.tag);
			tag = React.createElement(
				'span',
				{ className: classes_tag },
				link.tag
			);
		}
		return React.createElement(
			'li',
			{ key: link.id || index, className: classes },
			React.createElement(
				'a',
				{ href: link.url },
				tag,
				link.text
			)
		);
	}

	render(){
		const { className, name, id, focusNode } = this.props;
		const classes = classNames('react-headerMenu', className);

		const main = this.props.data.main;
		const nav = this.props.data.nav;

		return React.createElement(
			'div',
			{ className: classes },
			React.createElement(
				'ul',
				{ className: 'clearfix' },
				this.renderMainLink(main),
				nav.map((link, index) => this.renderLink(focusNode, link, index))
			)
		);
	}
}