/**
 * Created by wzj on 2017/12/18.
 */


class ComponentUploadImage extends React.Component {

	constructor(props) {
		super(props);

		this.scrf_header = $("meta[name='_csrf_header']").attr("content");
		this.scrf_token = $("meta[name='_csrf']").attr("content");

		this.state = {
			label: "上传照片"
		};

		this.uploadImage_onChange = this.uploadImage_onChange.bind(this);
		this.ajaxUploadImage_onChange  = this.ajaxUploadImage_onChange.bind(this);
	}


	uploadImage_onChange(e) {


	}

	ajaxUploadImage_onChange(elemFileId,e){
		console.log(elemFileId,e)
		var ele = e.target;
		$.ajaxFileUpload({
			url : '/user/uploadFile',
			secureuri : false,
			fileElementId : elemFileId,// file标签的id
			dataType : 'json',
			async:false,
			beforeSend: function(xhr){
				xhr.setRequestHeader(header, token);
			},
			success : function(data,msg) {
				console.log(data)
				alert(123);



			}.bind(this)
		});



	}


	render() {

		return React.createElement(UploadImage, { name: 'file',
			id: this.props.inputId,
			name: this.props.inputName,
			label: this.props.label ? this.props.label : this.state.label,
			scrf_header:this.scrf_header,
			scrf_token:this.scrf_token,
			ajax:this.props.ajax,
			onChange: this.props.ajax ? this.ajaxUploadImage_onChange : this.uploadImage_onChange });
	}
}