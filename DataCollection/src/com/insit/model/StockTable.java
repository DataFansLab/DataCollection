package com.insit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * StockTable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "stock_table", catalog = "CFDC")
public class StockTable implements java.io.Serializable {

	// Fields

	private String stockId;
	private String companyId;
	private String stockName;
	private String stockType;
	private String industry;
	private String exchange;
	private String status;
	private String block;
	private String listDate;
	private String changeDate;
	private Short priority;
	private Short frequence;

	// Constructors

	/** default constructor */
	public StockTable() {
	}

	/** full constructor */
	public StockTable(String companyId, String stockName, String stockType,
			String industry, String exchange, String status, String block,
			String listDate, String changeDate, Short priority, Short frequence) {
		this.companyId = companyId;
		this.stockName = stockName;
		this.stockType = stockType;
		this.industry = industry;
		this.exchange = exchange;
		this.status = status;
		this.block = block;
		this.listDate = listDate;
		this.changeDate = changeDate;
		this.priority = priority;
		this.frequence = frequence;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "stock_id", unique = true, nullable = false, length = 6)
	public String getStockId() {
		return this.stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	@Column(name = "company_id", length = 9)
	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Column(name = "stock_name", length = 20)
	public String getStockName() {
		return this.stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name = "stock_type", length = 3)
	public String getStockType() {
		return this.stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	@Column(name = "industry", length = 1)
	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@Column(name = "exchange", length = 2)
	public String getExchange() {
		return this.exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	@Column(name = "status", length = 4)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "block", length = 6)
	public String getBlock() {
		return this.block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	@Column(name = "list_date", length = 10)
	public String getListDate() {
		return this.listDate;
	}

	public void setListDate(String listDate) {
		this.listDate = listDate;
	}

	@Column(name = "change_date", length = 10)
	public String getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	@Column(name = "priority")
	public Short getPriority() {
		return this.priority;
	}

	public void setPriority(Short priority) {
		this.priority = priority;
	}

	@Column(name = "frequence")
	public Short getFrequence() {
		return this.frequence;
	}

	public void setFrequence(Short frequence) {
		this.frequence = frequence;
	}

}