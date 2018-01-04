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
        this.getOrgSelect = this.getOrgSelect.bind(this);
        this.insertErrorBubble = this.insertErrorBubble.bind(this);
        this.removeErrorBubble = this.removeErrorBubble.bind(this);
    }

    //自定义change方法
    customChange(node) {
        this.setState({
            node:node
        });
        //是否验证
        if(this.props.validetta &&  node != undefined){
            this.removeErrorBubble();
        }
        if(this.props.onChange){
            this.props.onChange(node);
        }
    }

    //获取原始下拉框
    getOrgSelect(){
        return $(this.refs.selectbox.refs.select);
    }

    //插入错误提示
    insertErrorBubble(text){
        var pos, W = 0, H = 0;
        var $bubble = $("<div class='validetta-bubble validetta-bubble--bottom'></div>");
        $bubble.html(text);

        var $element = this.getOrgSelect();
        var $elemParent = $element.parent(".react-selectbox-native").siblings(".react-selectbox-button");
        pos = $elemParent.position();
        H = $elemParent[0].offsetHeight;
        $bubble.css({
            top:pos.top + H + 0,
            left:pos.left + W + 15
        });
        $elemParent.next(".validetta-bubble").remove();
        $elemParent.after($bubble);
    }

    //删除错误提示
    removeErrorBubble(){
        var $element = this.getOrgSelect();
        var $elemParent = $element.parent(".react-selectbox-native").siblings(".react-selectbox-button");
        $elemParent.next(".validetta-bubble").remove();
    }

 
    render() {;
        const data = this.props.data.length>0 ? this.props.data : this.state.data
        return React.createElement(SelectBox, {
            ref:'selectbox',
            label: "",
            name:this.props.inputName,
            value:this.props.inputValue,
            data: data,
            filter: true,
            validetta:this.props.validetta,
            onChange: this.customChange
        })
    }
}