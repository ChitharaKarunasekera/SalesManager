package com.example.salesmanagerapp.model;

public class OrderModel {
    private int id;
    private int customerId;
    private String date;
    private double orderValue;
    private double paymentAmount;
    private double balanceAmount;

    public OrderModel() {
    }

    public OrderModel(int id, int customerId, String date, double orderValue, double paymentAmount, double balanceAmount) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.orderValue = orderValue;
        this.paymentAmount = paymentAmount;
        this.balanceAmount = balanceAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}

