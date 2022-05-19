package com.example.agropol.LayoutClasses;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.MainClasses.Order;
import com.example.agropol.R;

import java.util.ArrayList;

public class EmployeOrders extends AppCompatActivity {


    private RecyclerView recyclerView;
    private CatalogOfOrderEmployeeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders = new ArrayList<>();
    private DBHelper AgroPol;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                Bundle bundle = data.getExtras();
                itemOfRecyclerViewOrders.get(bundle.getInt("ID")).setStatus("Zamówienie gotowe");
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
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
                        finish();
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(EmployeOrders.super.getApplicationContext(),
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_employe_orders);
        createToolbar();
        findViews();
        startSettings();
        loadData();
    }
    private void loadData() {
//        wczytanie zamówień do recyclerView
        /*Cursor result =AgroPol.getDate("Select ID,Date_of_request,Price,Status,Date_of_delivery from request where Status not like 'tworzenie'");

        while(result.isAfterLast()==false)
        {
            int ID=result.getInt(0);
            String Date=result.getString(1);
            Double Price=result.getDouble(2);
            String Status=result.getString(3);
            itemOfRecyclerViewOrders.add(new ItemOfRecyclerViewOrder(ID,Date,Price,Status
            ));
            String dataC=result.getString(4);
            LocalDate dataN= LocalDate.now();
            LocalDate date=LocalDate.of(Integer.parseInt(dataC.split("-")[0]),Integer.parseInt(dataC.split("-")[1]),Integer.parseInt(dataC.split("-")[2]));
            //Zmiana statusu zamówienia z Zamówienie gotowe na Dostarczone
            if(date.isEqual(dataN) || date.isBefore(dataN))
                AgroPol.editData("request","ID="+ID,new String[]{"Status"},new String[]{"Dostarczono"});
            result.moveToNext();
        }*/
        Order order=new Order();
        itemOfRecyclerViewOrders=order.loadOrder(getApplicationContext(),itemOfRecyclerViewOrders,0);
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CatalogOfOrderEmployeeAdapter(itemOfRecyclerViewOrders);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CatalogOfOrderEmployeeAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                //wyświetlenie w nowej aktywności szczegółów zamówienia
                    Intent intent = new Intent(EmployeOrders.super.getApplicationContext(), DetailsOfEmployeeOrders.class);
                    intent.putExtra("IdRequest", itemOfRecyclerViewOrders.get(position).getId());
                    intent.putExtra("IdItem",position);
                    startActivityForResult(intent,1);
            }
        });
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(EmployeOrders.this);
    }


}