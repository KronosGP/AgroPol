package com.example.agropol;

public class ItemOfRecyclerViewOrder {
    private int id;
    private String date;
    private double price;

    ItemOfRecyclerViewOrder(int id, String date, double price)
    {
        this.id=id;
        this.date=date;
        this.price=price;
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
}
