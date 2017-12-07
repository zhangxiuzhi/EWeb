package com.esteel.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the TB_LOG_VERIFY_CODE database table.
 * 
 */
public class LogVerifyCodeVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long verifyId;

	private Timestamp sendTime;

	private Timestamp validTime;

	private String verifyCode;

	private Integer verifyStatus;

	private String verifyTarget;

	private Integer verifyType;
	private String verifyContent;

	public LogVerifyCodeVo() {
	}

	public long getVerifyId() {
		return this.verifyId;
	}
	
	public String getVerifyContent() {
		return verifyContent;
	}

	public void setVerifyContent(String verifyContent) {
		this.verifyContent = verifyContent;
	}

	public void setVerifyId(long verifyId) {
		this.verifyId = verifyId;
	}

	public Timestamp getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public Timestamp getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}

	public String getVerifyCode() {
		return this.verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Integer getVerifyStatus() {
		return this.verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyTarget() {
		return this.verifyTarget;
	}

	public void setVerifyTarget(String verifyTarget) {
		this.verifyTarget = verifyTarget;
	}

	public Integer getVerifyType() {
		return this.verifyType;
	}

	public void setVerifyType(Integer verifyType) {
		this.verifyType = verifyType;
	}

	@Override
	public String toString() {
		return "LogVerifyCodeVo [verifyId=" + verifyId + ", sendTime=" + sendTime + ", validTime=" + validTime
				+ ", verifyCode=" + verifyCode + ", verifyStatus=" + verifyStatus + ", verifyTarget=" + verifyTarget
				+ ", verifyType=" + verifyType + ", verifyContent=" + verifyContent + "]";
	}

	
}