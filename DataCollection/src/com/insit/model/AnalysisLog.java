package com.insit.model;

import java.sql.Time;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * AnalysisLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="analysis_log"
    ,catalog="CFDC"
)

public class AnalysisLog  implements java.io.Serializable {


    // Fields    

     private String bulletinId;
     private Date analyzeDate;
     private Time analyzeTime;
     private Short status;


    // Constructors

    /** default constructor */
    public AnalysisLog() {
    }

    
    /** full constructor */
    public AnalysisLog(Date analyzeDate, Time analyzeTime, Short status) {
        this.analyzeDate = analyzeDate;
        this.analyzeTime = analyzeTime;
        this.status = status;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="bulletin_id", unique=true, nullable=false, length=30)

    public String getBulletinId() {
        return this.bulletinId;
    }
    
    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="analyze_date", length=10)

    public Date getAnalyzeDate() {
        return this.analyzeDate;
    }
    
    public void setAnalyzeDate(Date analyzeDate) {
        this.analyzeDate = analyzeDate;
    }
    
    @Column(name="analyze_time", length=8)

    public Time getAnalyzeTime() {
        return this.analyzeTime;
    }
    
    public void setAnalyzeTime(Time analyzeTime) {
        this.analyzeTime = analyzeTime;
    }
    
    @Column(name="status")

    public Short getStatus() {
        return this.status;
    }
    
    public void setStatus(Short status) {
        this.status = status;
    }
   








}