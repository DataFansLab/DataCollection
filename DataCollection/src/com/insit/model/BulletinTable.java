package com.insit.model;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


/**
 * BulletinTable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="bulletin_table"
    ,catalog="CFDC"
)

public class BulletinTable  implements java.io.Serializable {


    // Fields    

     private String bulletinId;
     private String url;
     private String stockId;
     private String bulletinName;
     private String bulletinType;
     private Date accountDate;
     private String bulletinYear;
     private Date releaseDate;
     private Date downloadDate;
     private String hashcode;
     private String path;
     private Short downloadStatus;
     private Date analysisTime;
     private Short analysisStatus;


    // Constructors

    /** default constructor */
    public BulletinTable() {
    }

    
    /** full constructor */
    public BulletinTable(String url, String stockId, String bulletinName, String bulletinType, Date accountDate, String bulletinYear, Date releaseDate, Date downloadDate, String hashcode, String path, Short downloadStatus, Date analysisTime, Short analysisStatus) {
        this.url = url;
        this.stockId = stockId;
        this.bulletinName = bulletinName;
        this.bulletinType = bulletinType;
        this.accountDate = accountDate;
        this.bulletinYear = bulletinYear;
        this.releaseDate = releaseDate;
        this.downloadDate = downloadDate;
        this.hashcode = hashcode;
        this.path = path;
        this.downloadStatus = downloadStatus;
        this.analysisTime = analysisTime;
        this.analysisStatus = analysisStatus;
    }

   
    // Property accessors
    @Id
    @GeneratedValue(generator = "paymentableGenerator")    
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    
    @Column(name="bulletin_id", unique=true, nullable=false, length=30)

    public String getBulletinId() {
        return this.bulletinId;
    }
    
    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }
    
    @Column(name="URL", length=200)

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="stock_id", length=9)

    public String getStockId() {
        return this.stockId;
    }
    
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
    
    @Column(name="bulletin_name", length=200)

    public String getBulletinName() {
        return this.bulletinName;
    }
    
    public void setBulletinName(String bulletinName) {
        this.bulletinName = bulletinName;
    }
    
    @Column(name="bulletin_type", length=20)

    public String getBulletinType() {
        return this.bulletinType;
    }
    
    public void setBulletinType(String bulletinType) {
        this.bulletinType = bulletinType;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="account_date", length=10)

    public Date getAccountDate() {
        return this.accountDate;
    }
    
    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }
    
    @Column(name="bulletin_year", length=4)

    public String getBulletinYear() {
        return this.bulletinYear;
    }
    
    public void setBulletinYear(String bulletinYear) {
        this.bulletinYear = bulletinYear;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="release_date", length=10)

    public Date getReleaseDate() {
        return this.releaseDate;
    }
    
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="download_date", length=10)

    public Date getDownloadDate() {
        return this.downloadDate;
    }
    
    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }
    
    @Column(name="hashcode", length=32)

    public String getHashcode() {
        return this.hashcode;
    }
    
    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
    
    @Column(name="path", length=100)

    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    @Column(name="download_status")

    public Short getDownloadStatus() {
        return this.downloadStatus;
    }
    
    public void setDownloadStatus(Short downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="analysis_time", length=10)

    public Date getAnalysisTime() {
        return this.analysisTime;
    }
    
    public void setAnalysisTime(Date analysisTime) {
        this.analysisTime = analysisTime;
    }
    
    @Column(name="analysis_status")

    public Short getAnalysisStatus() {
        return this.analysisStatus;
    }
    
    public void setAnalysisStatus(Short analysisStatus) {
        this.analysisStatus = analysisStatus;
    }
   








}