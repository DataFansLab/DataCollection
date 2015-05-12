package com.insit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * QcRule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "qc_rule", catalog = "CFDC")
public class QcRule implements java.io.Serializable {

	// Fields

	private String qcRuleId;
	private String businessType;
	private String qcType;
	private String qcDescription;
	private String sysDescription;
	private String scriptPath;

	// Constructors

	/** default constructor */
	public QcRule() {
	}

	/** full constructor */
	public QcRule(String businessType, String qcType, String qcDescription,
			String sysDescription, String scriptPath) {
		this.businessType = businessType;
		this.qcType = qcType;
		this.qcDescription = qcDescription;
		this.sysDescription = sysDescription;
		this.scriptPath = scriptPath;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "qc_rule_id", unique = true, nullable = false, length = 10)
	public String getQcRuleId() {
		return this.qcRuleId;
	}

	public void setQcRuleId(String qcRuleId) {
		this.qcRuleId = qcRuleId;
	}

	@Column(name = "business_type", length = 3)
	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "qc_type", length = 8)
	public String getQcType() {
		return this.qcType;
	}

	public void setQcType(String qcType) {
		this.qcType = qcType;
	}

	@Column(name = "qc_description", length = 200)
	public String getQcDescription() {
		return this.qcDescription;
	}

	public void setQcDescription(String qcDescription) {
		this.qcDescription = qcDescription;
	}

	@Column(name = "sys_description", length = 8)
	public String getSysDescription() {
		return this.sysDescription;
	}

	public void setSysDescription(String sysDescription) {
		this.sysDescription = sysDescription;
	}

	@Column(name = "script_path", length = 100)
	public String getScriptPath() {
		return this.scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

}