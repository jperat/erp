package com.erp.beans;

public class ProductQuantity {

    private Long    id;
    private Product product;
    private Long    quantity;
    private String  batchNumber;
    private String  serialNumber;
    
    public ProductQuantity() {
        
    }
    
    public ProductQuantity (OrderProduct orderProduct) {
        this.product = orderProduct.getProduct();
        this.quantity = orderProduct.getQuantity();
        this.batchNumber = orderProduct.getBatchNumber();
        this.serialNumber = orderProduct.getSerialNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct( Product product ) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity( Long quantity ) {
        this.quantity = quantity;
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
    
    public String toString() {
        if (this.serialNumber.length() > 0)
            return this.quantity+" - "+this.serialNumber;            
        return this.quantity+" - "+this.batchNumber;
    }

}
