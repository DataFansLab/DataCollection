package com.insit.model;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * ModifyTableInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="modify_table_info"
    ,catalog="CFDC"
)

public class ModifyTableInfo  implements java.io.Serializable {


    // Fields    

     private ModifyTableInfoId id;
     private String unit;
     private Date time;
     private Short monthRange;
     private Short currencyType;
     private String content;
     private Short pdPage;
     private Short jsonStatus;


    // Constructors

    /** default constructor */
    public ModifyTableInfo() {
    }

	/** minimal constructor */
    public ModifyTableInfo(ModifyTableInfoId id) {
        this.id = id;
    }
    
    /** full constructor */
    public ModifyTableInfo(ModifyTableInfoId id, String unit, Date time, Short monthRange, Short currencyType, String content, Short pdPage, Short jsonStatus) {
        this.id = id;
        this.unit = unit;
        this.time = time;
        this.monthRange = monthRange;
        this.currencyType = currencyType;
        this.content = content;
        this.pdPage = pdPage;
        this.jsonStatus = jsonStatus;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="bulletinId", column=@Column(name="bulletin_id", nullable=false, length=30) ), 
        @AttributeOverride(name="tableType", column=@Column(name="table_type", nullable=false) ) } )

    public ModifyTableInfoId getId() {
        return this.id;
    }
    
    public void setId(ModifyTableInfoId id) {
        this.id = id;
    }
    
    @Column(name="unit", length=30)

    public String getUnit() {
        return this.unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="time", length=10)

    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    @Column(name="month_range")

    public Short getMonthRange() {
        return this.monthRange;
    }
    
    public void setMonthRange(Short monthRange) {
        this.monthRange = monthRange;
    }
    
    @Column(name="currency_type")

    public Short getCurrencyType() {
        return this.currencyType;
    }
    
    public void setCurrencyType(Short currencyType) {
        this.currencyType = currencyType;
    }
    
    @Column(name="content", length=65535)

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Column(name="pd_page")

    public Short getPdPage() {
        return this.pdPage;
    }
    
    public void setPdPage(Short pdPage) {
        this.pdPage = pdPage;
    }
    
    @Column(name="json_status")

    public Short getJsonStatus() {
        return this.jsonStatus;
    }
    
    public void setJsonStatus(Short jsonStatus) {
        this.jsonStatus = jsonStatus;
    }
   








}