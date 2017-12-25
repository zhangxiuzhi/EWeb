package com.esteel.web.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TB_MEMBER_COMPANY_ATTACH database table.
 * ��ҵ��Ա����
 */
public class MemberCompanyAttachVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long companyAttachedId;

	private String authorizationPath;

	private String businessLicenseCode;

	private String businessLicensePath;

	private String certificateOneCode;

	private String certificateOnePath;

	private String certificateThreeCode;

	private String certificateThreePath;

	private String certificateTwoCode;

	private String certificateTwoPath;

	private Integer companyId;

	private String companyNature;

	private Integer companyType;

	private String foreignTradePath;

	private String importExportCode;

	private Integer isThreeCertificate;

	private String legalIdCard;

	private String legalIdPath;

	private String openingPermitCode;

	private String openingPermitPath;

	private String organizationCode;

	private String organizationPath;

	private String registeredAddress;

	private String registeredCity;

	private String registeredDistrict;

	private String registeredProvince;

	private String socialCreditCode;

	private String socialCreditPath;

	private String taxRegistrationCode;

	private String taxRegistrationPath;

	private String threeCertificateCode;

	private String threeCertificatePath;

	private Timestamp updateTime;

	private String updateUser;

	public MemberCompanyAttachVo() {
	}

	public long getCompanyAttachedId() {
		return this.companyAttachedId;
	}

	public void setCompanyAttachedId(long companyAttachedId) {
		this.companyAttachedId = companyAttachedId;
	}

	public String getAuthorizationPath() {
		return this.authorizationPath;
	}

	public void setAuthorizationPath(String authorizationPath) {
		this.authorizationPath = authorizationPath;
	}

	public String getBusinessLicenseCode() {
		return this.businessLicenseCode;
	}

	public void setBusinessLicenseCode(String businessLicenseCode) {
		this.businessLicenseCode = businessLicenseCode;
	}

	public String getBusinessLicensePath() {
		return this.businessLicensePath;
	}

	public void setBusinessLicensePath(String businessLicensePath) {
		this.businessLicensePath = businessLicensePath;
	}

	public String getCertificateOneCode() {
		return this.certificateOneCode;
	}

	public void setCertificateOneCode(String certificateOneCode) {
		this.certificateOneCode = certificateOneCode;
	}

	public String getCertificateOnePath() {
		return this.certificateOnePath;
	}

	public void setCertificateOnePath(String certificateOnePath) {
		this.certificateOnePath = certificateOnePath;
	}

	public String getCertificateThreeCode() {
		return this.certificateThreeCode;
	}

	public void setCertificateThreeCode(String certificateThreeCode) {
		this.certificateThreeCode = certificateThreeCode;
	}

	public String getCertificateThreePath() {
		return this.certificateThreePath;
	}

	public void setCertificateThreePath(String certificateThreePath) {
		this.certificateThreePath = certificateThreePath;
	}

	public String getCertificateTwoCode() {
		return this.certificateTwoCode;
	}

	public void setCertificateTwoCode(String certificateTwoCode) {
		this.certificateTwoCode = certificateTwoCode;
	}

	public String getCertificateTwoPath() {
		return this.certificateTwoPath;
	}

	public void setCertificateTwoPath(String certificateTwoPath) {
		this.certificateTwoPath = certificateTwoPath;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyNature() {
		return this.companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}

	public Integer getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public String getForeignTradePath() {
		return this.foreignTradePath;
	}

	public void setForeignTradePath(String foreignTradePath) {
		this.foreignTradePath = foreignTradePath;
	}

	public String getImportExportCode() {
		return this.importExportCode;
	}

	public void setImportExportCode(String importExportCode) {
		this.importExportCode = importExportCode;
	}

	public Integer getIsThreeCertificate() {
		return this.isThreeCertificate;
	}

	public void setIsThreeCertificate(Integer isThreeCertificate) {
		this.isThreeCertificate = isThreeCertificate;
	}

	public String getLegalIdCard() {
		return this.legalIdCard;
	}

	public void setLegalIdCard(String legalIdCard) {
		this.legalIdCard = legalIdCard;
	}

	public String getLegalIdPath() {
		return this.legalIdPath;
	}

	public void setLegalIdPath(String legalIdPath) {
		this.legalIdPath = legalIdPath;
	}

	public String getOpeningPermitCode() {
		return this.openingPermitCode;
	}

	public void setOpeningPermitCode(String openingPermitCode) {
		this.openingPermitCode = openingPermitCode;
	}

	public String getOpeningPermitPath() {
		return this.openingPermitPath;
	}

	public void setOpeningPermitPath(String openingPermitPath) {
		this.openingPermitPath = openingPermitPath;
	}

	public String getOrganizationCode() {
		return this.organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationPath() {
		return this.organizationPath;
	}

	public void setOrganizationPath(String organizationPath) {
		this.organizationPath = organizationPath;
	}

	public String getRegisteredAddress() {
		return this.registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getRegisteredCity() {
		return this.registeredCity;
	}

	public void setRegisteredCity(String registeredCity) {
		this.registeredCity = registeredCity;
	}

	public String getRegisteredDistrict() {
		return this.registeredDistrict;
	}

	public void setRegisteredDistrict(String registeredDistrict) {
		this.registeredDistrict = registeredDistrict;
	}

	public String getRegisteredProvince() {
		return this.registeredProvince;
	}

	public void setRegisteredProvince(String registeredProvince) {
		this.registeredProvince = registeredProvince;
	}

	public String getSocialCreditCode() {
		return this.socialCreditCode;
	}

	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}

	public String getSocialCreditPath() {
		return this.socialCreditPath;
	}

	public void setSocialCreditPath(String socialCreditPath) {
		this.socialCreditPath = socialCreditPath;
	}

	public String getTaxRegistrationCode() {
		return this.taxRegistrationCode;
	}

	public void setTaxRegistrationCode(String taxRegistrationCode) {
		this.taxRegistrationCode = taxRegistrationCode;
	}

	public String getTaxRegistrationPath() {
		return this.taxRegistrationPath;
	}

	public void setTaxRegistrationPath(String taxRegistrationPath) {
		this.taxRegistrationPath = taxRegistrationPath;
	}

	public String getThreeCertificateCode() {
		return this.threeCertificateCode;
	}

	public void setThreeCertificateCode(String threeCertificateCode) {
		this.threeCertificateCode = threeCertificateCode;
	}

	public String getThreeCertificatePath() {
		return this.threeCertificatePath;
	}

	public void setThreeCertificatePath(String threeCertificatePath) {
		this.threeCertificatePath = threeCertificatePath;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Override
	public String toString() {
		return "MemberCompanyAttachVo [companyAttachedId=" + companyAttachedId + ", authorizationPath="
				+ authorizationPath + ", businessLicenseCode=" + businessLicenseCode + ", businessLicensePath="
				+ businessLicensePath + ", certificateOneCode=" + certificateOneCode + ", certificateOnePath="
				+ certificateOnePath + ", certificateThreeCode=" + certificateThreeCode + ", certificateThreePath="
				+ certificateThreePath + ", certificateTwoCode=" + certificateTwoCode + ", certificateTwoPath="
				+ certificateTwoPath + ", companyId=" + companyId + ", companyNature=" + companyNature
				+ ", companyType=" + companyType + ", foreignTradePath=" + foreignTradePath + ", importExportCode="
				+ importExportCode + ", isThreeCertificate=" + isThreeCertificate + ", legalIdCard=" + legalIdCard
				+ ", legalIdPath=" + legalIdPath + ", openingPermitCode=" + openingPermitCode + ", openingPermitPath="
				+ openingPermitPath + ", organizationCode=" + organizationCode + ", organizationPath="
				+ organizationPath + ", registeredAddress=" + registeredAddress + ", registeredCity=" + registeredCity
				+ ", registeredDistrict=" + registeredDistrict + ", registeredProvince=" + registeredProvince
				+ ", socialCreditCode=" + socialCreditCode + ", socialCreditPath=" + socialCreditPath
				+ ", taxRegistrationCode=" + taxRegistrationCode + ", taxRegistrationPath=" + taxRegistrationPath
				+ ", threeCertificateCode=" + threeCertificateCode + ", threeCertificatePath=" + threeCertificatePath
				+ ", updateTime=" + updateTime + ", updateUser=" + updateUser + "]";
	}
	
}