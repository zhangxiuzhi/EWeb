/**
 * Created by WangZhenJia(159109799@qq.com) on 2017/9/5.
 */

class ComponentSelectBox extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			data: [{ value: "node1", text: "天津港" }, { value: "node2", text: '大连港' }, { value: "node3", text: '京唐港' }, { value: "node4", text: '曹妃甸港' }, { value: "node5", text: '青岛港' }, { value: "node6", text: '京唐港' }, { value: "node7", text: '日照港' }, { value: "node8", text: '连云港' }, { value: "node9", text: '北仑港' }, { value: "node10", text: '防城港' }, { value: "node11", text: '湛江港' }, { value: "node12", text: '锦州港' }, { value: "node13", text: '营口港' }, { value: "node14", text: '丹东港' }, { value: "node15", text: '鲅鱼圈港' }, { value: "node16", text: '秦皇岛港' }, { value: "node17", text: '唐山港' }, { value: "node18", text: '黄骅港' }, { value: "node19", text: '龙口港' }]
		};

		this.customChange = this.customChange.bind(this);
	}

	customChange(node) {
		if(this.props.onChange){
			this.props.onChange(node);
		}
	}

	render() {;
		const data = this.props.data ? this.props.data : this.state.data
		return React.createElement(SelectBox, {
			label: "",
			name:this.props.inputName,
			data: data,
			filter: true,
			onChange: this.customChange
		})
	}
}