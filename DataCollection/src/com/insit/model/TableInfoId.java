package com.insit.model;
// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * TableInfoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class TableInfoId  implements java.io.Serializable {


    // Fields    

     private String bulletinId;
     private Short tableType;


    // Constructors

    /** default constructor */
    public TableInfoId() {
    }

    
    /** full constructor */
    public TableInfoId(String bulletinId, Short tableType) {
        this.bulletinId = bulletinId;
        this.tableType = tableType;
    }

   
    // Property accessors

    @Column(name="bulletin_id", nullable=false, length=30)

    public String getBulletinId() {
        return this.bulletinId;
    }
    
    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }

    @Column(name="table_type", nullable=false)

    public Short getTableType() {
        return this.tableType;
    }
    
    public void setTableType(Short tableType) {
        this.tableType = tableType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TableInfoId) ) return false;
		 TableInfoId castOther = ( TableInfoId ) other; 
         
		 return ( (this.getBulletinId()==castOther.getBulletinId()) || ( this.getBulletinId()!=null && castOther.getBulletinId()!=null && this.getBulletinId().equals(castOther.getBulletinId()) ) )
 && ( (this.getTableType()==castOther.getTableType()) || ( this.getTableType()!=null && castOther.getTableType()!=null && this.getTableType().equals(castOther.getTableType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBulletinId() == null ? 0 : this.getBulletinId().hashCode() );
         result = 37 * result + ( getTableType() == null ? 0 : this.getTableType().hashCode() );
         return result;
   }   





}