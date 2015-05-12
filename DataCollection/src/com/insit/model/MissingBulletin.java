package com.insit.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MissingBulletin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "missing_bulletin", catalog = "CFDC")
public class MissingBulletin implements java.io.Serializable {

	// Fields

	private Integer id;
	private String stockId;
	private String bulletinType;
	private Date accountDate;

	// Constructors

	/** default constructor */
	public MissingBulletin() {
	}

	/** full constructor */
	public MissingBulletin(String stockId, String bulletinType, Date accountDate) {
		this.stockId = stockId;
		this.bulletinType = bulletinType;
		this.accountDate = accountDate;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "stock_id", length = 6)
	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	@Column(name = "bulletin_type", length = 20)
	public String getBulletinType() {
		return this.bulletinType;
	}

	public void setBulletinType(String bulletinType) {
		this.bulletinType = bulletinType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "account_date", length = 10)
	public Date getAccountDate() {
		return this.accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

}