/**
 * 会员中心-子账号管理-列表配置
 * Created by wzj on 2017/12/8.
 */

class SubAccountList extends React.Component{

	constructor(props) {
		super(props);

		this.thead = [
			{ dataField: "indexId", dataName: "序号",width: 60 },
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
	}


	render() {
		var datas = this.state.data;
		var options = {
			url:"/company/findMembers?_csrf=b7aac0fa-df63-4570-bb02-901ac2f51c03",
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

	return cell;
}

//格式化操作
function format_operation(cell,row){
	//if (row.status == 0) {
	var html = "<a class='text-info' onclick=show_subAccountEditModal('"+row.userId+"')>移除</a>";
	html+= "<a class='text-info' onclick=show_subAccountEditModal('"+row.userId+"')>编辑</a>";
	html+= "<a class='text-info' onclick=show_subAccountEditModal('"+row.userId+"')>编辑</a>";
	return html;
	//}

}

//显示子账号编辑窗口
function show_subAccountEditModal(id){
	alert(id)
	/*var data = JSON.parse(rowData);
	$("#subaccoumt-edit-modal").modal("show");
	//显示当前值
	$("#subAccount-name").val(data.name);
	$("#subAccount-department").val(data.department);
	$("#subAccount-job").val(data.job);*/
}

