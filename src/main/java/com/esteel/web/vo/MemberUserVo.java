package com.esteel.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * The persistent class for the TB_MEMBER_USER database table.
 */

public class MemberUserVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long userId;

	private String account;

	private Integer companyId;

	private String email;

	private Integer gender;

	private Integer userGrade;

	private String lastLoginIp;

	private Timestamp lastLoginTime;

	private Integer memberGrade;

	private Integer memberIntegration;

	private String mobile;

	private String password;

	private Timestamp registeredTime;

	private String remarks;

	private Integer securitySetting;

	private Integer tradeCurrency;

	private Integer tradeVarietie;

	private String userName;

	private String userPicture;

	private Integer userStatus;
	
	private String memberName;
	
	private String positon;
	
	private String dept;
	
	public MemberUserVo() {
	}

	public long getUserId() {
		return this.userId;
	}
	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(Integer userGrade) {
		this.userGrade = userGrade;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getMemberGrade() {
		return this.memberGrade;
	}

	public void setMemberGrade(Integer memberGrade) {
		this.memberGrade = memberGrade;
	}

	public Integer getMemberIntegration() {
		return this.memberIntegration;
	}

	public void setMemberIntegration(Integer memberIntegration) {
		this.memberIntegration = memberIntegration;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getRegisteredTime() {
		return this.registeredTime;
	}

	public void setRegisteredTime(Timestamp registeredTime) {
		this.registeredTime = registeredTime;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getSecuritySetting() {
		return this.securitySetting;
	}

	public void setSecuritySetting(Integer securitySetting) {
		this.securitySetting = securitySetting;
	}

	public Integer getTradeCurrency() {
		return this.tradeCurrency;
	}

	public void setTradeCurrency(Integer tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	public Integer getTradeVarietie() {
		return this.tradeVarietie;
	}

	public void setTradeVarietie(Integer tradeVarietie) {
		this.tradeVarietie = tradeVarietie;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPicture() {
		return this.userPicture;
	}

	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}

	public Integer getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	
	public String getPositon() {
		return positon;
	}

	public void setPositon(String positon) {
		this.positon = positon;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((lastLoginIp == null) ? 0 : lastLoginIp.hashCode());
		result = prime * result + ((lastLoginTime == null) ? 0 : lastLoginTime.hashCode());
		result = prime * result + ((memberGrade == null) ? 0 : memberGrade.hashCode());
		result = prime * result + ((memberIntegration == null) ? 0 : memberIntegration.hashCode());
		result = prime * result + ((memberName == null) ? 0 : memberName.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((positon == null) ? 0 : positon.hashCode());
		result = prime * result + ((registeredTime == null) ? 0 : registeredTime.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + ((securitySetting == null) ? 0 : securitySetting.hashCode());
		result = prime * result + ((tradeCurrency == null) ? 0 : tradeCurrency.hashCode());
		result = prime * result + ((tradeVarietie == null) ? 0 : tradeVarietie.hashCode());
		result = prime * result + ((userGrade == null) ? 0 : userGrade.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userPicture == null) ? 0 : userPicture.hashCode());
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberUserVo other = (MemberUserVo) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (lastLoginIp == null) {
			if (other.lastLoginIp != null)
				return false;
		} else if (!lastLoginIp.equals(other.lastLoginIp))
			return false;
		if (lastLoginTime == null) {
			if (other.lastLoginTime != null)
				return false;
		} else if (!lastLoginTime.equals(other.lastLoginTime))
			return false;
		if (memberGrade == null) {
			if (other.memberGrade != null)
				return false;
		} else if (!memberGrade.equals(other.memberGrade))
			return false;
		if (memberIntegration == null) {
			if (other.memberIntegration != null)
				return false;
		} else if (!memberIntegration.equals(other.memberIntegration))
			return false;
		if (memberName == null) {
			if (other.memberName != null)
				return false;
		} else if (!memberName.equals(other.memberName))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (positon == null) {
			if (other.positon != null)
				return false;
		} else if (!positon.equals(other.positon))
			return false;
		if (registeredTime == null) {
			if (other.registeredTime != null)
				return false;
		} else if (!registeredTime.equals(other.registeredTime))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (securitySetting == null) {
			if (other.securitySetting != null)
				return false;
		} else if (!securitySetting.equals(other.securitySetting))
			return false;
		if (tradeCurrency == null) {
			if (other.tradeCurrency != null)
				return false;
		} else if (!tradeCurrency.equals(other.tradeCurrency))
			return false;
		if (tradeVarietie == null) {
			if (other.tradeVarietie != null)
				return false;
		} else if (!tradeVarietie.equals(other.tradeVarietie))
			return false;
		if (userGrade == null) {
			if (other.userGrade != null)
				return false;
		} else if (!userGrade.equals(other.userGrade))
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPicture == null) {
			if (other.userPicture != null)
				return false;
		} else if (!userPicture.equals(other.userPicture))
			return false;
		if (userStatus == null) {
			if (other.userStatus != null)
				return false;
		} else if (!userStatus.equals(other.userStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberUserVo [userId=" + userId + ", account=" + account + ", companyId=" + companyId + ", email="
				+ email + ", gender=" + gender + ", userGrade=" + userGrade + ", lastLoginIp=" + lastLoginIp
				+ ", lastLoginTime=" + lastLoginTime + ", memberGrade=" + memberGrade + ", memberIntegration="
				+ memberIntegration + ", mobile=" + mobile + ", password=" + password + ", registeredTime="
				+ registeredTime + ", remarks=" + remarks + ", securitySetting=" + securitySetting + ", tradeCurrency="
				+ tradeCurrency + ", tradeVarietie=" + tradeVarietie + ", userName=" + userName + ", userPicture="
				+ userPicture + ", userStatus=" + userStatus + ", memberName=" + memberName + ", positon=" + positon
				+ ", dept=" + dept + "]";
	}

	
	
	
}