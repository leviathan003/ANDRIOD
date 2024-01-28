package com.example.myapplication;

public class Order {
    private String invoice,order_date,prod_name,price,qty_ordered;

    public Order(String invoice, String order_date, String prod_name, String price, String qty_ordered) {
        this.invoice = invoice;
        this.order_date = order_date;
        this.prod_name = prod_name;
        this.price = price;
        this.qty_ordered = qty_ordered;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getProd_name() {
        return prod_name;
    }

    public String getPrice() {
        return price;
    }

    public String getQty_ordered() {
        return qty_ordered;
    }
}
