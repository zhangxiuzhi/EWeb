/**
 * Created by wzj on 2017/12/18.
 */


class ComponentUploadImage extends React.Component {

	constructor(props) {
		super(props);

		this.state = {
			label: "上传照片"
		};
	}

	ui_uploadImage_onChange(url) {}

	render() {

		return React.createElement(UploadImage, { name: 'file', name: this.props.name,
			label: this.props.label ? this.props.label : this.state.label,
			onChange: this.ui_uploadImage_onChange });
	}
}