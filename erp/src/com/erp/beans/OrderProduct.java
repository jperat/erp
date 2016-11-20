package com.erp.beans;

public class OrderProduct {

    private Long    id;
    private Order   order;
    private Product product;
    private String  batchNumber;
    private String  serialNumber;
    private Long    quantity;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder( Order order ) {
        this.order = order;
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

}
