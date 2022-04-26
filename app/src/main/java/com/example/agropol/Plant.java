package com.example.agropol;

public class Plant {
    private String species;
    private String variety;
    private long quantity;
    private double price;
    private int image;

    public Plant(String species, String variety, long quantity, double price, int image)
    {
        this.species=species;
        this.variety=variety;
        this.quantity=quantity;
        this.price=price;
        this.image=image;
    }
    public String getSpecies()
    {
        return species;
    }
    public String getVariety()
    {
        return variety;
    }
    public long getQuantity()
    {
        return quantity;
    }
    public double getPrice()
    {
        return price;
    }
    public int getImage()
    {
        return image;
    }
}
