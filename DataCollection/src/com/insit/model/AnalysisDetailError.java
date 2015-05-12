package com.insit.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * AnalysisDetailError entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "analysis_detail_error", catalog = "CFDC")
public class AnalysisDetailError implements java.io.Serializable {

	// Fields

	private AnalysisDetailErrorId id;
	private Short pdPage;
	private Short locateInfo;
	private Short fieldconflict;
	private Short fieldnameQc;

	// Constructors

	/** default constructor */
	public AnalysisDetailError() {
	}

	/** minimal constructor */
	public AnalysisDetailError(AnalysisDetailErrorId id) {
		this.id = id;
	}

	/** full constructor */
	public AnalysisDetailError(AnalysisDetailErrorId id, Short pdPage,
			Short locateInfo, Short fieldconflict, Short fieldnameQc) {
		this.id = id;
		this.pdPage = pdPage;
		this.locateInfo = locateInfo;
		this.fieldconflict = fieldconflict;
		this.fieldnameQc = fieldnameQc;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "bulletinId", column = @Column(name = "bulletin_id", nullable = false, length = 30)),
			@AttributeOverride(name = "tableType", column = @Column(name = "table_type", nullable = false)) })
	public AnalysisDetailErrorId getId() {
		return this.id;
	}

	public void setId(AnalysisDetailErrorId id) {
		this.id = id;
	}

	@Column(name = "pd_page")
	public Short getPdPage() {
		return this.pdPage;
	}

	public void setPdPage(Short pdPage) {
		this.pdPage = pdPage;
	}

	@Column(name = "locate_info")
	public Short getLocateInfo() {
		return this.locateInfo;
	}

	public void setLocateInfo(Short locateInfo) {
		this.locateInfo = locateInfo;
	}

	@Column(name = "fieldconflict")
	public Short getFieldconflict() {
		return this.fieldconflict;
	}

	public void setFieldconflict(Short fieldconflict) {
		this.fieldconflict = fieldconflict;
	}

	@Column(name = "fieldname_qc")
	public Short getFieldnameQc() {
		return this.fieldnameQc;
	}

	public void setFieldnameQc(Short fieldnameQc) {
		this.fieldnameQc = fieldnameQc;
	}

}