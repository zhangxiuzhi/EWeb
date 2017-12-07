package com.esteel.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the TB_MEMBER_COMPANY database table.
 * 企业基本信息表
 */
public class MemberCompanyVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long companyId;

	private Integer approvalStatus;

	private Timestamp approvalTime;

	private String approvalUser;

	private Integer approvalUserId;

	private String companyAlias;

	private String companyAliasEn;

	private String companyFax;

	private String companyName;

	private String companyNameEn;

	private String companyPhone;

	private Integer companyStatus;

	private String contactAddress;

	private String countryCode;

	private Integer creditIntegration;

	private Integer isForeignTrade;

	private String logo;

	private String mainBusiness;

	private Integer memberGrade;

	private Integer memberUserId;

	private Integer registeredMethod;

	private Timestamp registeredTime;

	private String registeredUser;

	private Integer registeredUserId;

	private String remarks;

	private Integer seats;

	private Integer tradeCurrency;

	private Integer tradeIntegration;

	private Integer tradeVarietie;

	private Timestamp validTime;

	private String zipCode;

	public MemberCompanyVo() {
	}

	public long getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public Integer getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Timestamp getApprovalTime() {
		return this.approvalTime;
	}

	public void setApprovalTime(Timestamp approvalTime) {
		this.approvalTime = approvalTime;
	}

	public String getApprovalUser() {
		return this.approvalUser;
	}

	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	public Integer getApprovalUserId() {
		return this.approvalUserId;
	}

	public void setApprovalUserId(Integer approvalUserId) {
		this.approvalUserId = approvalUserId;
	}

	public String getCompanyAlias() {
		return this.companyAlias;
	}

	public void setCompanyAlias(String companyAlias) {
		this.companyAlias = companyAlias;
	}

	public String getCompanyAliasEn() {
		return this.companyAliasEn;
	}

	public void setCompanyAliasEn(String companyAliasEn) {
		this.companyAliasEn = companyAliasEn;
	}

	public String getCompanyFax() {
		return this.companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyNameEn() {
		return this.companyNameEn;
	}

	public void setCompanyNameEn(String companyNameEn) {
		this.companyNameEn = companyNameEn;
	}

	public String getCompanyPhone() {
		return this.companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public Integer getCompanyStatus() {
		return this.companyStatus;
	}

	public void setCompanyStatus(Integer companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getCreditIntegration() {
		return this.creditIntegration;
	}

	public void setCreditIntegration(Integer creditIntegration) {
		this.creditIntegration = creditIntegration;
	}

	public Integer getIsForeignTrade() {
		return this.isForeignTrade;
	}

	public void setIsForeignTrade(Integer isForeignTrade) {
		this.isForeignTrade = isForeignTrade;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getMainBusiness() {
		return this.mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public Integer getMemberGrade() {
		return this.memberGrade;
	}

	public void setMemberGrade(Integer memberGrade) {
		this.memberGrade = memberGrade;
	}

	public Integer getMemberUserId() {
		return this.memberUserId;
	}

	public void setMemberUserId(Integer memberUserId) {
		this.memberUserId = memberUserId;
	}

	public Integer getRegisteredMethod() {
		return this.registeredMethod;
	}

	public void setRegisteredMethod(Integer registeredMethod) {
		this.registeredMethod = registeredMethod;
	}

	public Timestamp getRegisteredTime() {
		return this.registeredTime;
	}

	public void setRegisteredTime(Timestamp registeredTime) {
		this.registeredTime = registeredTime;
	}

	public String getRegisteredUser() {
		return this.registeredUser;
	}

	public void setRegisteredUser(String registeredUser) {
		this.registeredUser = registeredUser;
	}

	public Integer getRegisteredUserId() {
		return this.registeredUserId;
	}

	public void setRegisteredUserId(Integer registeredUserId) {
		this.registeredUserId = registeredUserId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getSeats() {
		return this.seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public Integer getTradeCurrency() {
		return this.tradeCurrency;
	}

	public void setTradeCurrency(Integer tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	public Integer getTradeIntegration() {
		return this.tradeIntegration;
	}

	public void setTradeIntegration(Integer tradeIntegration) {
		this.tradeIntegration = tradeIntegration;
	}

	public Integer getTradeVarietie() {
		return this.tradeVarietie;
	}

	public void setTradeVarietie(Integer tradeVarietie) {
		this.tradeVarietie = tradeVarietie;
	}

	public Timestamp getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}