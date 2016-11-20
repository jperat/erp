package com.erp.beans;

import java.sql.Timestamp;

public class ProjectProduct {

    private Long      id;
    private Project   project;
    private Product   product;
    private String    batchNumber;
    private String    serialNumber;
    private Long      quantity;
    private Timestamp date;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject( Project project ) {
        this.project = project;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct( Product product ) {
        this.product = product;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber( String batchNumber ) {
        this.batchNumber = batchNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber( String serialNumber ) {
        this.serialNumber = serialNumber;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity( Long quantity ) {
        this.quantity = quantity;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate( Timestamp date ) {
        this.date = date;
    }

}
