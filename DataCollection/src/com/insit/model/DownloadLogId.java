package com.insit.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DownloadLogId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class DownloadLogId implements java.io.Serializable {

	// Fields

	private Timestamp downloadTime;
	private String event;
	private String dataName;
	private Integer dataType;
	private Date downloadDate;
	private Short downloadStatus;

	// Constructors

	/** default constructor */
	public DownloadLogId() {
	}

	/** minimal constructor */
	public DownloadLogId(Integer dataType, Date downloadDate,
			Short downloadStatus) {
		this.dataType = dataType;
		this.downloadDate = downloadDate;
		this.downloadStatus = downloadStatus;
	}

	/** full constructor */
	public DownloadLogId(Timestamp downloadTime, String event, String dataName,
			Integer dataType, Date downloadDate, Short downloadStatus) {
		this.downloadTime = downloadTime;
		this.event = event;
		this.dataName = dataName;
		this.dataType = dataType;
		this.downloadDate = downloadDate;
		this.downloadStatus = downloadStatus;
	}

	// Property accessors

	@Column(name = "download_time", length = 19)
	public Timestamp getDownloadTime() {
		return this.downloadTime;
	}

	public void setDownloadTime(Timestamp downloadTime) {
		this.downloadTime = downloadTime;
	}

	@Column(name = "event", length = 8)
	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Column(name = "data_name", length = 50)
	public String getDataName() {
		return this.dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	@Column(name = "data_type", nullable = false)
	public Integer getDataType() {
		return this.dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "download_date", nullable = false, length = 10)
	public Date getDownloadDate() {
		return this.downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	@Column(name = "download_status", nullable = false)
	public Short getDownloadStatus() {
		return this.downloadStatus;
	}

	public void setDownloadStatus(Short downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DownloadLogId))
			return false;
		DownloadLogId castOther = (DownloadLogId) other;

		return ((this.getDownloadTime() == castOther.getDownloadTime()) || (this
				.getDownloadTime() != null
				&& castOther.getDownloadTime() != null && this
				.getDownloadTime().equals(castOther.getDownloadTime())))
				&& ((this.getEvent() == castOther.getEvent()) || (this
						.getEvent() != null && castOther.getEvent() != null && this
						.getEvent().equals(castOther.getEvent())))
				&& ((this.getDataName() == castOther.getDataName()) || (this
						.getDataName() != null
						&& castOther.getDataName() != null && this
						.getDataName().equals(castOther.getDataName())))
				&& ((this.getDataType() == castOther.getDataType()) || (this
						.getDataType() != null
						&& castOther.getDataType() != null && this
						.getDataType().equals(castOther.getDataType())))
				&& ((this.getDownloadDate() == castOther.getDownloadDate()) || (this
						.getDownloadDate() != null
						&& castOther.getDownloadDate() != null && this
						.getDownloadDate().equals(castOther.getDownloadDate())))
				&& ((this.getDownloadStatus() == castOther.getDownloadStatus()) || (this
						.getDownloadStatus() != null
						&& castOther.getDownloadStatus() != null && this
						.getDownloadStatus().equals(
								castOther.getDownloadStatus())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getDownloadTime() == null ? 0 : this.getDownloadTime()
						.hashCode());
		result = 37 * result
				+ (getEvent() == null ? 0 : this.getEvent().hashCode());
		result = 37 * result
				+ (getDataName() == null ? 0 : this.getDataName().hashCode());
		result = 37 * result
				+ (getDataType() == null ? 0 : this.getDataType().hashCode());
		result = 37
				* result
				+ (getDownloadDate() == null ? 0 : this.getDownloadDate()
						.hashCode());
		result = 37
				* result
				+ (getDownloadStatus() == null ? 0 : this.getDownloadStatus()
						.hashCode());
		return result;
	}

}