package com.insit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * AffairsAssign entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="affairs_assign"
    ,catalog="CFDC"
)

public class AffairsAssign  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String companyId;
     private String docId;
     private String emailAddr;


    // Constructors

    /** default constructor */
    public AffairsAssign() {
    }

    
    /** full constructor */
    public AffairsAssign(String companyId, String docId, String emailAddr) {
        this.companyId = companyId;
        this.docId = docId;
        this.emailAddr = emailAddr;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="company_id", length=20)

    public String getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    
    @Column(name="doc_id", length=10)

    public String getDocId() {
        return this.docId;
    }
    
    public void setDocId(String docId) {
        this.docId = docId;
    }
    
    @Column(name="email_addr", length=40)

    public String getEmailAddr() {
        return this.emailAddr;
    }
    
    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }
   








}