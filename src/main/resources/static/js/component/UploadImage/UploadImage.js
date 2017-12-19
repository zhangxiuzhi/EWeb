/**
 * Created by wzj on 2017/12/18.
 */
/**
 * Created by wzj on 2017/12/18.
 */

class UploadImage extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			value:"aa",
			hasImage: false,
			label: props.label ? props.label : "上传照片"
		};

		//this.changeFile = this.changeFile.bind(this);
		this.remove = this.remove.bind(this);
	}

	remove(event) {
		//window.URL.revokeObjectURL();
		this.refs.image.src = "";
		this.setState({
			hasImage: false,
			text: this.props.label ? this.props.label : "上传照片"
		});
	}

	changeFile(event) {
		//如果为异步上传文件
		if(this.props.ajax){
			if (this.props.onChange) {
				this.props.onChange(this.refs.inputFile.id,event);
			}
		}else{
			var file = event.target.files[0]; // 获取到input-file的文件对象
			var url = window.URL.createObjectURL(file);
			this.refs.image.src = url;
			this.setState({
				hasImage: true,
				text: "替换照片"
			});
			if (this.props.onChange) {
				this.props.onChange(url);
			}
		}
	}

	render() {
		const classes = classNames("react-uploadImage", {
			"hasImage": this.state.hasImage
		});

		return React.createElement(
			"div",
			{ className: classes },
			React.createElement("a", { href: "javascript:;", className: "removeImage fa fa-remove", ref: "remove", onClick: this.remove }),
			React.createElement(
				"a",
				{ href: "javascript:;", className: "btn-file" },
				React.createElement(
					"span",
					{ ref: "text" },
					this.state.label
				),
				React.createElement("input", {onChange:e => this.changeFile(e), id:this.props.id, name: this.props.name, type: "file", ref:"inputFile" })
			),
			React.createElement("div", { className: "viewImgMask" }),
			React.createElement("img", { src: "", className: "viewImg", ref: "image" })


		);
	}
}