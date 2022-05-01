package com.example.agropol;

public class DataOfOrders {
    private String species;
    private String variety;
    private int quantity;
    private double price;
    private double sum;

    DataOfOrders(String species, String variety, int quantity, double price, double sum)
    {
        this.species=species;
        this.variety=variety;
        this.quantity=quantity;
        this.price=price;
        this.sum=sum;
    }

    public String getSpecies()
    {
        return species;
    }
    public String getVariety()
    {
        return variety;
    }
    public int getQuantity()
    {
        return quantity;
    }
    public double getPrice()
    {
        return price;
    }
    public double getSum()
    {
        return sum;
    }
}
