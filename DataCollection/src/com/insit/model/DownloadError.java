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
 * DownloadError entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="download_error"
    ,catalog="CFDC"
)

public class DownloadError  implements java.io.Serializable {


    // Fields    

     private Integer errorId;
     private String bulletinId;
     private String stockId;
     private Date promptTime;
     private Date operateTime;
     private String qcRuleId;
     private Short operateStatus;
     private String url;
     private String detailDescribe;
     private Short errorType;
     private String errorTypeDescription;


    // Constructors

    /** default constructor */
    public DownloadError() {
    }

    
    /** full constructor */
    public DownloadError(String bulletinId, String stockId, Date promptTime, Date operateTime, String qcRuleId, Short operateStatus, String url, String detailDescribe, Short errorType, String errorTypeDescription) {
        this.bulletinId = bulletinId;
        this.stockId = stockId;
        this.promptTime = promptTime;
        this.operateTime = operateTime;
        this.qcRuleId = qcRuleId;
        this.operateStatus = operateStatus;
        this.url = url;
        this.detailDescribe = detailDescribe;
        this.errorType = errorType;
        this.errorTypeDescription = errorTypeDescription;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="error_id", unique=true, nullable=false)

    public Integer getErrorId() {
        return this.errorId;
    }
    
    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }
    
    @Column(name="bulletin_id", length=30)

    public String getBulletinId() {
        return this.bulletinId;
    }
    
    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }
    
    @Column(name="stock_id", length=6)

    public String getStockId() {
        return this.stockId;
    }
    
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="prompt_time", length=10)

    public Date getPromptTime() {
        return this.promptTime;
    }
    
    public void setPromptTime(Date promptTime) {
        this.promptTime = promptTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="operate_time", length=10)

    public Date getOperateTime() {
        return this.operateTime;
    }
    
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
    
    @Column(name="qc_rule_id", length=10)

    public String getQcRuleId() {
        return this.qcRuleId;
    }
    
    public void setQcRuleId(String qcRuleId) {
        this.qcRuleId = qcRuleId;
    }
    
    @Column(name="operate_status")

    public Short getOperateStatus() {
        return this.operateStatus;
    }
    
    public void setOperateStatus(Short operateStatus) {
        this.operateStatus = operateStatus;
    }
    
    @Column(name="url", length=200)

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="detail_describe", length=50)

    public String getDetailDescribe() {
        return this.detailDescribe;
    }
    
    public void setDetailDescribe(String detailDescribe) {
        this.detailDescribe = detailDescribe;
    }
    
    @Column(name="error_type")

    public Short getErrorType() {
        return this.errorType;
    }
    
    public void setErrorType(Short errorType) {
        this.errorType = errorType;
    }
    
    @Column(name="error_type_description", length=20)

    public String getErrorTypeDescription() {
        return this.errorTypeDescription;
    }
    
    public void setErrorTypeDescription(String errorTypeDescription) {
        this.errorTypeDescription = errorTypeDescription;
    }
   








}