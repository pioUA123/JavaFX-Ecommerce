package com.example.oopii_finalproject.Objects;

public class Payment {
    private final int paymentId;
    private final int orderId;
    private final String method;
    private final String paymentStatus;
    private final String txnRef;

    public Payment(int paymentId, int orderId, String method, String paymentStatus, String txnRef) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.method = method;
        this.paymentStatus = paymentStatus;
        this.txnRef = txnRef;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getMethod() {
        return method;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getTxnRef() {
        return txnRef;
    }
}
