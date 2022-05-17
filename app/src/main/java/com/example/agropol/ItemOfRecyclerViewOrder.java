package com.example.agropol;

public class ItemOfRecyclerViewOrder {
    private int id;
    private String date;
    private double price;
    private String status;

    ItemOfRecyclerViewOrder(int id, String date, double price, String status)
    {
        this.id=id;
        this.date=date;
        this.price=price;
        this.status=status;
    }

    public int getId()
    {
        return id;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
