/**
 * 会员中心-子账号管理-列表配置
 * Created by wzj on 2017/12/8.
 */

class SubAccountList extends React.Component{

	constructor(props) {
		super(props);

		this.thead = [
			{ dataField: "userId", dataName: "序号",width: 60 },
			{ dataField: "account", dataName: "账号", width: 160, dataFormat: format_subAccount },
			{ dataField: "userName", dataName: "姓名", width: 160 },
			{ dataField: "dept", dataName: "部门",  },
			{ dataField: "positon", dataName: "职务",  },
			{ dataField: "email", dataName: "邮箱",  },
			{ dataField: "indexId", dataName: "操作", width: 120 , dataFormat: format_operation}
		];
		this.state = {
			data:[
				
			]
		}
		this.reloadTable = this.reloadTable.bind(this);
	}

	reloadTable(_searchData){
		this.refs.jtable.refs.jtable.setState({
			searchData:_searchData
		});
		this.refs.jtable.refs.jtable.ajaxRequestData()
	}

	render() {
		var datas = this.state.data;
		var options = {
			url:"/company/findMembers",
			thead: this.thead,
			status: this.props.status,
			page: 1,
			sizePerPage: 10,
			nodata: {text: "没有子账号信息"},
			//loadingText:{dataFormat:listLoadingIconFormat},	//自定义列表加载动画
		};
		return React.createElement(ComponentJTable, {options: options, datas: datas, ref: "jtable"});
	}
}

//格式化账号
function format_subAccount(cell,row){
	if(row.activationTime!=null){
		var newTime = new Date().getTime();
		if(newTime>row.activationTime){
			return cell +"&ensp;密码过期";
		}
		else {
			return cell +"&ensp;待激活";
		}
	}else{
		return cell;
	}
}

//格式化操作
function format_operation(cell,row){
	//if (row.status == 0) {
	var html = "<a class='text-info' data-id='"+row.userId+"' onclick=romove_subAccountEditModal(this)>移除</a>&ensp;";
	html+= "<a class='text-info'  data-id='"+row.userId+"' data-name='"+row.userName+"' data-dept='"+row.dept+"' data-job='"+row.positon+"' onclick=show_subAccountEditModal(this)>编辑</a>&ensp;";
	if(row.activationTime!=null){
		var newTime = new Date().getTime();
		if(newTime>row.activationTime){
			return html+= "<a class='text-info' data-id='"+row.userId+"' onclick=reSubAccountAdd(this)>再次添加</a>";
		}
		else {
			return html;
		}
	}else{
		return html;
	}
	
	//}

}
//显示子账号编辑窗口
function show_subAccountEditModal(_link){
	$("#subaccoumt-edit-modal").modal("show");
	//显示当前值
	$("#subAccount-Id").val($(_link).data("id"));
	$("#subAccount-name").val($(_link).data("name"));
	$("#subAccount-department").val($(_link).data("dept"));
	$("#subAccount-job").val($(_link).data("job"));
}

