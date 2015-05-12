package com.insit.model;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * MissingBulletinId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class MissingBulletinId  implements java.io.Serializable {


    // Fields    

     private String stockId;
     private String bulletinType;
     private Date accountDate;


    // Constructors

    /** default constructor */
    public MissingBulletinId() {
    }

    
    /** full constructor */
    public MissingBulletinId(String stockId, String bulletinType, Date accountDate) {
        this.stockId = stockId;
        this.bulletinType = bulletinType;
        this.accountDate = accountDate;
    }

   
    // Property accessors

    @Column(name="stock_id", length=6)

    public String getStockId() {
        return this.stockId;
    }
    
    public void setStockId(String stockId) {
        this.stockId = stockId;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MissingBulletinId) ) return false;
		 MissingBulletinId castOther = ( MissingBulletinId ) other; 
         
		 return ( (this.getStockId()==castOther.getStockId()) || ( this.getStockId()!=null && castOther.getStockId()!=null && this.getStockId().equals(castOther.getStockId()) ) )
 && ( (this.getBulletinType()==castOther.getBulletinType()) || ( this.getBulletinType()!=null && castOther.getBulletinType()!=null && this.getBulletinType().equals(castOther.getBulletinType()) ) )
 && ( (this.getAccountDate()==castOther.getAccountDate()) || ( this.getAccountDate()!=null && castOther.getAccountDate()!=null && this.getAccountDate().equals(castOther.getAccountDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getStockId() == null ? 0 : this.getStockId().hashCode() );
         result = 37 * result + ( getBulletinType() == null ? 0 : this.getBulletinType().hashCode() );
         result = 37 * result + ( getAccountDate() == null ? 0 : this.getAccountDate().hashCode() );
         return result;
   }   





}