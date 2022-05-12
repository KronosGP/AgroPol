package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.agropol.DBHelper.DBHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Orders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogOfOrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders = new ArrayList<>();
    private DBHelper AgroPol;
    private int IdUser;
    private int Flag;
    Button btnAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orders);
        findViews();
        startSettings();
        createListeners();
        Bundle bundle=getIntent().getExtras();
        IdUser=bundle.getInt("IdUser");
        Flag=bundle.getInt("Flag");
        loadData();
        //trzeba przekazać znowu flagę w przypadku otwarcia katalogu zamówień z pozycji składania reklamacji
        //tak aby pokazało się okno informacyjnie a następnie aby pokliknięcu na konkretny item
        // zatwierdzało zamówienie którego dotyczyć ma reklamacja, następnie powrót do MakeComplaint
        //showInfoWindow();
    }

    private void showInfoWindow() {
        Dialog showInfoWindow = new Dialog(this);
        showInfoWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        showInfoWindow.setContentView(R.layout.layout_info_dialog);
        showInfoWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showInfoWindow.show();

        Button btnOk=findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoWindow.dismiss();
            }
        });

    }

    private void loadData() {
        //usunięcie pustych zamówień
        Cursor result = AgroPol.getDate("Select * from request where IDClient = " + IdUser);
        while(result.isAfterLast()==false)
        {
            Cursor result1=AgroPol.getDate("Select count(*) from details_request where IDRequest="+result.getString(0));
            if(Integer.parseInt(result1.getString(0))==0)
                AgroPol.delData("request","ID="+result.getString(0));
            result.moveToNext();
        }
        //wczytanie zamówień z bazy danych do recyclerView
            result=AgroPol.getDate("Select * from request where IDClient = " + IdUser);
            try {
                while (result.isAfterLast() == false) {
                    Double price=Double.parseDouble(result.getString(2));
                    Double delivey=Double.parseDouble(result.getString(7));
                    int id=Integer.parseInt(result.getString(0));
                    String data=result.getString(3);
                    Double sum=price+delivey;
                    itemOfRecyclerViewOrders.add(new ItemOfRecyclerViewOrder(id,data,sum));
                    System.out.println(Integer.parseInt(result.getString(0))+" "+ result.getString(3)+" "+sum);
                    result.moveToNext();
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        if(Flag==1){/*showInfoWindow();*/}//coś z przyciskiem nie działa(btnOk)

    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CatalogOfOrderAdapter(itemOfRecyclerViewOrders);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CatalogOfOrderAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                if(Flag==0) {
                        Intent intent = new Intent(Orders.super.getApplicationContext(),
                                DetailsOfOrder.class);
                        intent.putExtra("IdUser", IdUser);
                        intent.putExtra("IdOrder", itemOfRecyclerViewOrders.get(position).getId());
                        startActivity(intent);
               }

                    //tak jak wyżej wspomniałem tutaj flaga która będzie decydowałą czy wyświetlamy szczegóły
                    //zamówienia jak wyżej, czy wybieramy którego zamówienia ma dotyczyć reklamacja
                else{
                    try {

                        AgroPol.setData("complaint",new String[]{"IDClient","IDRequest","Contents","Status","Date_of_Complaint"},new String[]{String.valueOf(IdUser),String.valueOf(itemOfRecyclerViewOrders.get(position).getId())," ","In Make",DataN()});
                        Cursor result=AgroPol.getDate("Select ID from complaint where IDClient="+IdUser);
                        result.moveToLast();
                        Intent intent = new Intent(Orders.super.getApplicationContext(),
                                MakeComplaint.class);
                        intent.putExtra("IdUser",IdUser);
                        intent.putExtra("IdComplaint",result.getInt(0));
                        startActivity(intent);
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex);
                    }

                }
            }
        });
    }

    private void createListeners() {
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    AgroPol.setData("request", new String[]{"IDClient","Price","Date_of_request","Date_of_delivery","Delivery"}, new String[]{String.valueOf(IdUser),"0.0",DataN(),newDate(DataN()),"0.0"});
                    System.out.println(AgroPol.getDate("Select * from request").getString(0));
                    Intent intent = new Intent(Orders.super.getApplicationContext(),
                            MakeOrder.class);
                    intent.putExtra("IdUser", IdUser);
                    startActivity(intent);
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                }
            }
        });
    }

    private String DataN() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar oldDate=Calendar.getInstance();
        String Date=simpleDateFormat.format(oldDate.getTime());
        Calendar c = Calendar.getInstance();
        try{
            //Setting the date to the given date
            c.setTime(simpleDateFormat.parse(Date));
        }catch(ParseException e){
            e.printStackTrace();
        }

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, 0);
        //Date after adding the days to the given date
        String newDate = simpleDateFormat.format(c.getTime());
        return newDate;
    }

    private String newDate(String oldDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try{
            //Setting the date to the given date
            c.setTime(sdf.parse(oldDate));
        }catch(ParseException e){
            e.printStackTrace();
        }

        //Number of Days to add
        c.add(Calendar.DAY_OF_MONTH, 14);
        //Date after adding the days to the given date
        String newDate = sdf.format(c.getTime());
        return newDate;
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        btnAddOrder=findViewById(R.id.btn_add_order);
        AgroPol=new DBHelper(Orders.this);
    }
}