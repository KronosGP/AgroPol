package com.example.agropol.MainClasses;

import android.content.Context;
import android.database.Cursor;

import com.example.agropol.LayoutClasses.PlantItems;

import java.util.ArrayList;

public class Plant {

    public  void addPlant(Context context, String s, String v, String q, String p, String i)
    {
        DBHelper dbHelper = new DBHelper(context);
        String[] col = {"Species", "Variety", "Quantity", "Price", "Image"};
        String[] value = {s,v,q,p,i};
        dbHelper.setData("plant", col, value);
    }
    public  void editPlant(Context context, String id, String s, String v, String q, String p, String i)
    {
        DBHelper dbHelper = new DBHelper(context);
        String[] col = {"Species", "Variety", "Quantity", "Price", "Image"};
        String[] value = {s,v,q,p,i};
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
