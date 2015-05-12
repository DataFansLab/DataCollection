package com.insit.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * DownloadLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "download_log", catalog = "CFDC")
public class DownloadLog implements java.io.Serializable {

	// Fields

	private DownloadLogId id;

	// Constructors

	/** default constructor */
	public DownloadLog() {
	}

	/** full constructor */
	public DownloadLog(DownloadLogId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "downloadTime", column = @Column(name = "download_time", length = 19)),
			@AttributeOverride(name = "event", column = @Column(name = "event", length = 8)),
			@AttributeOverride(name = "dataName", column = @Column(name = "data_name", length = 50)),
			@AttributeOverride(name = "dataType", column = @Column(name = "data_type", nullable = false)),
			@AttributeOverride(name = "downloadDate", column = @Column(name = "download_date", nullable = false, length = 10)),
			@AttributeOverride(name = "downloadStatus", column = @Column(name = "download_status", nullable = false)) })
	public DownloadLogId getId() {
		return this.id;
	}

	public void setId(DownloadLogId id) {
		this.id = id;
	}

}