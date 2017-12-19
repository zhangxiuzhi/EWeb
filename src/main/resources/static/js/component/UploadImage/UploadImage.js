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
			hasImage: false,
			label: props.label ? props.label : "上传照片"
		};

		this.upload = this.upload.bind(this);
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

	upload(event) {
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
				React.createElement("input", { name: this.props.name, type: "file", onChange: this.upload })
			),
			React.createElement("div", { className: "viewImgMask" }),
			React.createElement("img", { src: "", className: "viewImg", ref: "image" })
		);
	}
}