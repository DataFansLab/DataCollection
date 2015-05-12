package com.insit.model;
// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TableInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="table_info"
    ,catalog="CFDC"
)

public class TableInfo  implements java.io.Serializable {


    // Fields    

     private TableInfoId id;
     private String unit;
     private Short pdPage;
     private String time;
     private Short monthRange;
     private Short currencyType;
     private String content;


    // Constructors

    /** default constructor */
    public TableInfo() {
    }

	/** minimal constructor */
    public TableInfo(TableInfoId id) {
        this.id = id;
    }
    
    /** full constructor */
    public TableInfo(TableInfoId id, String unit, Short pdPage, String time, Short monthRange, Short currencyType, String content) {
        this.id = id;
        this.unit = unit;
        this.pdPage = pdPage;
        this.time = time;
        this.monthRange = monthRange;
        this.currencyType = currencyType;
        this.content = content;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="bulletinId", column=@Column(name="bulletin_id", nullable=false, length=30) ), 
        @AttributeOverride(name="tableType", column=@Column(name="table_type", nullable=false) ) } )

    public TableInfoId getId() {
        return this.id;
    }
    
    public void setId(TableInfoId id) {
        this.id = id;
    }
    
    @Column(name="unit", length=30)

    public String getUnit() {
        return this.unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    @Column(name="pd_page")

    public Short getPdPage() {
        return this.pdPage;
    }
    
    public void setPdPage(Short pdPage) {
        this.pdPage = pdPage;
    }
    
    @Column(name="time", length=30)

    public String getTime() {
        return this.time;
    }
    
    public void setTime(String time) {
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
   








}