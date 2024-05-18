package com.example.salesmanagerapp.model;

public class Order {
    private String customerName;
    private String date;
    private double orderValue;
    private double paymentAmount;
    private double balanceAmount;

    public Order(String customerName, String date, double orderValue, double paymentAmount, double balanceAmount) {
        this.customerName = customerName;
        this.date = date;
        this.orderValue = orderValue;
        this.paymentAmount = paymentAmount;
        this.balanceAmount = balanceAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDate() {
        return date;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }
}