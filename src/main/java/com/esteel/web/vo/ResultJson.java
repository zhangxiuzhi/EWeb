package com.esteel.web.vo;

/**
 * 用于接收下拉等功能Json返回
 * @author Administrator
 *
 */
public class ResultJson {
	private String text;
	private long value;
	private String key;
	
	public ResultJson() {
		super();
	}
	public ResultJson(String text, long value, String key) {
		super();
		this.text = text;
		this.value = value;
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "ResultJson [text=" + text + ", value=" + value + ", key=" + key + "]";
	}
	
}
