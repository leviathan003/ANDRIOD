package com.example.myapplication;

public class OrderThumbnail {
    private String invoice,date;

    public OrderThumbnail(String invoice, String date) {
        this.invoice = invoice;
        this.date = date;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getDate() {
        return date;
    }
}
