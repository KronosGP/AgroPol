package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.agropol.MainClasses.Complaint;
import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.MainClasses.Order;
import com.example.agropol.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ClientOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogOfOrderClientAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders = new ArrayList<>();
    private DBHelper AgroPol;
    Order order;
    private int IdUser;
    private int Flag;
    Button btnAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_orders);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
        IdUser = sharedPreferences.getInt("IdUser", 0);
        Flag =sharedPreferences.getInt("Flag",0);
       createToolbar();
        findViews();
        startSettings();
        createListeners();
        loadData();
        //trzeba przekazać znowu flagę w przypadku otwarcia katalogu zamówień z pozycji składania reklamacji
        //tak aby pokazało się okno informacyjnie a następnie aby pokliknięcu na konkretny item
        // zatwierdzało zamówienie którego dotyczyć ma reklamacja, następnie powrót do MakeComplaint
        //showInfoWindow();
    }

    private void createToolbar() {
        ImageView btnBack=findViewById(R.id.btn_back);
        ImageView btnLogout=findViewById(R.id.btn_logout);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= v.getId();
                switch (id)
                {
                    case R.id.btn_back:
                    {
                        if(Flag==0) {
                            Intent intent = new Intent(ClientOrders.super.getApplicationContext(),
                                    ClientMenu.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(ClientOrders.super.getApplicationContext(),
                                    ClientComplaints.class);
                            startActivity(intent);
                        }
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(ClientOrders.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        View decorView = getWindow().getDecorView();
        super.onResume();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showInfoWindow() {
        Dialog showInfoWindow = new Dialog(this);
        showInfoWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        showInfoWindow.setContentView(R.layout.layout_info_dialog);
        showInfoWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showInfoWindow.show();

        Button btnOk=showInfoWindow.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoWindow.dismiss();
            }
        });

    }

    private void loadData() {
        //usunięcie pustych zamówień
        AgroPol.delData("request","Status like 'tworzenie' and IDClient="+IdUser);
        //wczytanie zamówień z bazy danych do recyclerView
        itemOfRecyclerViewOrders = order.loadOrder(getApplicationContext(), itemOfRecyclerViewOrders, IdUser);

        if(Flag==1){showInfoWindow();}

    }


    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CatalogOfOrderClientAdapter(itemOfRecyclerViewOrders);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CatalogOfOrderClientAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                if(Flag==0) {
                        Intent intent = new Intent(ClientOrders.super.getApplicationContext(),
                                DetailsOfClientOrder.class);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("IdUser",IdUser);
                    editor.putInt("IdOrder",itemOfRecyclerViewOrders.get(position).getId());
                    editor.apply();
                        startActivity(intent);
               }

                    //tak jak wyżej wspomniałem tutaj flaga która będzie decydowałą czy wyświetlamy szczegóły
                    //zamówienia jak wyżej, czy wybieramy którego zamówienia ma dotyczyć reklamacja
                else{
                    try {
                        //Tworzenie reklamacji
                        Complaint complaint=new Complaint();
                        complaint.addComplaint(getApplicationContext(),String.valueOf(IdUser),String.valueOf(itemOfRecyclerViewOrders.get(position).getId())," ","In Make",DataN());

                        Cursor result=AgroPol.getDate("Select ID from complaint where IDClient="+IdUser);
                        result.moveToLast();
                        Intent intent = new Intent(ClientOrders.super.getApplicationContext(),
                                MakeComplaint.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IdUser",IdUser);
                        editor.putInt("IdComplaint",result.getInt(0));
                        editor.apply();
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
                    //Tworzenie zamówienia
                    order.AddOrder(getApplicationContext(),String.valueOf(IdUser),"0.0",DataN(),"","0.0","tworzenie");
                    Intent intent = new Intent(ClientOrders.super.getApplicationContext(),
                            MakeOrder.class);

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("IdUser",IdUser);
                    editor.apply();
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

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        btnAddOrder=findViewById(R.id.btn_add_order);
        AgroPol=new DBHelper(ClientOrders.this);
        order=new Order();
    }
}