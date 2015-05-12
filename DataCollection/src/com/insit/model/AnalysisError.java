package com.insit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AnalysisError entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "analysis_error", catalog = "CFDC")
public class AnalysisError implements java.io.Serializable {

	// Fields

	private String bulletinId;
	private Boolean cashflowStatus;
	private Boolean balancesheetStatus;
	private Boolean incomestateStatus;
	private Boolean othersStatus;
	private Short conflict;
	private Integer status;

	// Constructors

	/** default constructor */
	public AnalysisError() {
	}

	/** full constructor */
	public AnalysisError(Boolean cashflowStatus, Boolean balancesheetStatus,
			Boolean incomestateStatus, Boolean othersStatus, Short conflict,
			Integer status) {
		this.cashflowStatus = cashflowStatus;
		this.balancesheetStatus = balancesheetStatus;
		this.incomestateStatus = incomestateStatus;
		this.othersStatus = othersStatus;
		this.conflict = conflict;
		this.status = status;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "bulletin_id", unique = true, nullable = false, length = 30)
	public String getBulletinId() {
		return this.bulletinId;
	}

	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}

	@Column(name = "cashflow_status")
	public Boolean getCashflowStatus() {
		return this.cashflowStatus;
	}

	public void setCashflowStatus(Boolean cashflowStatus) {
		this.cashflowStatus = cashflowStatus;
	}

	@Column(name = "balancesheet_status")
	public Boolean getBalancesheetStatus() {
		return this.balancesheetStatus;
	}

	public void setBalancesheetStatus(Boolean balancesheetStatus) {
		this.balancesheetStatus = balancesheetStatus;
	}

	@Column(name = "incomestate_status")
	public Boolean getIncomestateStatus() {
		return this.incomestateStatus;
	}

	public void setIncomestateStatus(Boolean incomestateStatus) {
		this.incomestateStatus = incomestateStatus;
	}

	@Column(name = "others_status")
	public Boolean getOthersStatus() {
		return this.othersStatus;
	}

	public void setOthersStatus(Boolean othersStatus) {
		this.othersStatus = othersStatus;
	}

	@Column(name = "conflict")
	public Short getConflict() {
		return this.conflict;
	}

	public void setConflict(Short conflict) {
		this.conflict = conflict;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}