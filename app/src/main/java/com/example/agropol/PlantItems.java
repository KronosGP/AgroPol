package com.example.agropol;

import android.content.Context;

import com.example.agropol.DBHelper.DBHelper;

public class PlantItems {
    private  int id;
    private String species;
    private String variety;
    private long quantity;
    private double price;
    private int image;

    public PlantItems(int id, String species, String variety, long quantity, double price, int image)
    {
        this.id=id;
        this.species=species;
        this.variety=variety;
        this.quantity=quantity;
        this.price=price;
        this.image=image;
    }
    public void setId(int i) {this.id=i;}
    public void setSpecies(String s)
    {
        this.species=s;
    }
    public void setVariety(String v)
    {
        this.variety=v;
    }
    public void setQuantity(long q)
    {
        this.quantity=q;
    }
    public void setPrice(double p)
    {
        this.price=p;
    }
    public void setImage(int i)
    {
        this.image=i;
    }

    public int getId(){return id;}
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

    public  void addPlant(Context context, String s, String v, String q, String p, String i)
    {
        DBHelper dbHelper = new DBHelper(context);
        String[] col = {"Species", "Variety", "Quantity", "Price", "Image"};
        String[] value = {s,v,q,p,i};
        dbHelper.setData("plant", col, value);
    }
}
