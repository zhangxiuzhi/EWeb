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
			{ dataField: "name", dataName: "姓名", width: 160 },
			{ dataField: "department", dataName: "部门",  },
			{ dataField: "job", dataName: "职务",  },
			{ dataField: "email", dataName: "邮箱",  },
			{ dataField: "indexId", dataName: "操作", width: 120 , dataFormat: format_operation}
		];
		this.state = {
			data:[
				{indexId:"1",account:"100.00",name:"王震甲",department:"技术",job:"UI",email:""},
				{indexId:"2",account:"100.00",name:"",department:"",job:"",email:""},
				{indexId:"3",account:"100.00",name:"",department:"",job:"",email:""},
				{indexId:"4",account:"100.00",name:"",department:"",job:"",email:""},
				{indexId:"5",account:"100.00",name:"",department:"",job:"",email:""},
				{indexId:"6",account:"100.00",name:"",department:"",job:"",email:""},
				{indexId:"7",account:"100.00",name:"",department:"",job:"",email:""}
			]
		}
	}


	render() {
		var datas = this.state.data;
		var options = {
			url:"",
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
		return "<a class='text-info' onclick=show_subAccountEditModal('"+JSON.stringify(row)+"')>编辑</a>";
	//}

}

//显示子账号编辑窗口
function show_subAccountEditModal(rowData){
	var data = JSON.parse(rowData);
	$("#subaccoumt-edit-modal").modal("show");
	//显示当前值
	$("#subAccount-name").val(data.name);
	$("#subAccount-department").val(data.department);
	$("#subAccount-job").val(data.job);
}

