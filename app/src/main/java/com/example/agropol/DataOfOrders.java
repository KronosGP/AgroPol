package com.example.agropol;

public class DataOfOrders {
    private String species;
    private String variety;
    private int quantity;
    private double price;
    private double sum;
    private int image;

    public DataOfOrders(String species, String variety, int quantity, double price, double sum, int image)
    {
        this.species=species;
        this.variety=variety;
        this.quantity=quantity;
        this.price=price;
        this.sum=sum;
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

    public int getImage() {
        return image;
    }
}
