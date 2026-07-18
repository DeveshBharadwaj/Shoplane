package com.cfs.Ecomm.dto;

import java.util.Date;
import java.util.List;

public class OrderDto {

    private Long id;

    private double totalamount;

    private String status;

    private Date orderDate;

    private String userName;

    private String email;

    private List<OrderItemDto> orderItems;

    public OrderDto(Long id, double totalamount, String status, Date orderDate, String userName, String email, List<OrderItemDto> orderItems) {
        this.id = id;
        this.totalamount = totalamount;
        this.status = status;
        this.orderDate = orderDate;
        this.userName = userName;
        this.email = email;
        this.orderItems = orderItems;
    }

    public OrderDto(Long id, double totalamount, String status, Date orderDate, List<OrderItemDto> orderItems) {
        this.id = id;
        this.totalamount = totalamount;
        this.status = status;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }


}
