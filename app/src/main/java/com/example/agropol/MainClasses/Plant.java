package com.example.agropol.MainClasses;

import android.content.Context;
import android.database.Cursor;

import com.example.agropol.LayoutClasses.PlantItems;

import java.util.ArrayList;

public class Plant {

    private String species;
    private String variety;
    private int quantity;
    private double price;
    private int image;

    public Plant(String species, String variety, int quantity, double price, int image) {
        this.species = species;
        this.variety = variety;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public  void addPlant(Context context)
    {
        DBHelper dbHelper = new DBHelper(context);
        String[] col = {"Species", "Variety", "Quantity", "Price", "Image"};
        String[] value = {species,variety,String.valueOf(quantity), String.valueOf(price), String.valueOf(image)};
        dbHelper.setData("plant", col, value);
    }
    public  void editPlant(Context context, String id)
    {
        DBHelper dbHelper = new DBHelper(context);
        String[] col = {"Species", "Variety", "Quantity", "Price", "Image"};
        String[] value = {species, variety, String.valueOf(quantity), String.valueOf(price), String.valueOf(image)};
        dbHelper.editData("plant", "Id=" + id, col, value);
    }
    public ArrayList<PlantItems> loadPlants(Context context, ArrayList<PlantItems> plantItems) {
        DBHelper dbHelper = new DBHelper(context);
        Cursor result = dbHelper.getDate("Select * from plant");
        while (result.isAfterLast() == false) {
            plantItems.add(new PlantItems(Integer.parseInt(result.getString(0)), result.getString(1), result.getString(2), (long) Integer.parseInt(result.getString(3)), Double.parseDouble(result.getString(4)), Integer.parseInt(result.getString(5))));
            result.moveToNext();
        }
        return plantItems;
    }
}
