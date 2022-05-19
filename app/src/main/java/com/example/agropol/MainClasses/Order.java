package com.example.agropol.MainClasses;

import android.content.Context;
import android.database.Cursor;

import com.example.agropol.LayoutClasses.DataOfOrders;
import com.example.agropol.LayoutClasses.ItemOfRecyclerViewOrder;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    int IdClient;
    float Price;
    String Date_of_request;
    String Date_of_delivery;
    String Status;
    float Delivery;
    public void AddOrder(Context context,String Id,String p,String DoR,String DoD,String s,String d)
    {
        DBHelper dbHelper= new DBHelper(context);
        String[] col={"IDClient","Price","Date_of_request","Date_of_delivery","Status","Delivery"};
        String[] val={Id,p,DoR,DoD,s,d};
        dbHelper.setData("request",col,val);
    }

    public void EditOrder(Context context,String where ,String[] col,String[] val)
    {
        DBHelper dbHelper= new DBHelper(context);
        dbHelper.editData("request",where,col,val);
    }

    public ArrayList<ItemOfRecyclerViewOrder> loadOrder(Context context ,ArrayList<ItemOfRecyclerViewOrder> orderItems,int IdUser)
    {
        DBHelper dbHelper=new DBHelper(context);
        Cursor result;
        if(IdUser==0)
            result=dbHelper.getDate("Select * from request");
        else
            result=dbHelper.getDate("Select * from request where IDClient = " + IdUser);
        while (result.isAfterLast() == false) {
            Double price=Double.parseDouble(result.getString(2));
            Double delivey=Double.parseDouble(result.getString(7));
            int id=Integer.parseInt(result.getString(0));
            String data=result.getString(3);
            Double sum=Math.round((price+delivey) * 100.0) / 100.0;
            String status=result.getString(6);
            orderItems.add(new ItemOfRecyclerViewOrder(id,data,sum,status));
            String dataC=result.getString(4);
            try {
                LocalDate dataN = LocalDate.now();
                LocalDate date = LocalDate.of(Integer.parseInt(dataC.split("-")[0]), Integer.parseInt(dataC.split("-")[1]), Integer.parseInt(dataC.split("-")[2]));
                //Zmiana statusu zamówienia z Zamówienie gotowe na dostarczone
                if (date.isEqual(dataN) || date.isBefore(dataN))
                    dbHelper.editData("request", "ID=" + id, new String[]{"Status"}, new String[]{"zrealizowano"});
            }
            catch (Exception ex){}
            result.moveToNext();
        }
        return orderItems;
    }


    public void AddDetailsOrder(Context context,String IdR,String IdP,String Q)
    {
        DBHelper dbHelper= new DBHelper(context);
        String[] col={"IDRequest", "IDPlant", "Quantity"};
        String[] val={IdR,IdP,Q};
        dbHelper.setData("details_request",col,val);
    }

    public ArrayList<DataOfOrders> loadDetailsOrder(Context context , ArrayList<DataOfOrders> orderItems, int IdRequest)
    {
        DBHelper dbHelper=new DBHelper(context);
        Cursor result=dbHelper.getDate("Select * from details_request where IDRequest="+IdRequest);
        while(result.isAfterLast()==false)
        {
            Cursor result1=dbHelper.getDate("Select * from plant where ID="+result.getString(1));
            String species=result1.getString(1);
            String variety=result1.getString(2);
            int quantity=result.getInt(2);
            double price=result1.getDouble(4);
            double sum=Math.round(quantity* price * 100.0) / 100.0;
            int image=result1.getInt(5);
            orderItems.add(new DataOfOrders(species,variety,quantity,price,sum,image));
            result.moveToNext();
        }
        return orderItems;
    }
}
