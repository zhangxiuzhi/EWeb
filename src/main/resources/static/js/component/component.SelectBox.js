/**
 * Created by WangZhenJia(159109799@qq.com) on 2017/9/5.
 */
 
class ComponentSelectBox extends React.Component {
    constructor(props) {
        super(props);
 
        this.state = {
            data: [],
            node:""
        };
 
        this.customChange = this.customChange.bind(this);
    }
 
    customChange(node) {
        this.setState({
            node:node
        })
        if(this.props.onChange){
            this.props.onChange(node);
        }
    }
 
    render() {;
        var data = this.props.data ? this.props.data : this.state.data
        return React.createElement(SelectBox, {
            label: "",
            name:this.props.inputName,
            value:this.props.inputValue,
            data: data,
            filter: true,
            onChange: this.customChange
        })
    }
}