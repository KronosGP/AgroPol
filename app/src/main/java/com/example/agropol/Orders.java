package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.agropol.DBHelper.DBHelper;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogOfOrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders = new ArrayList<>();
    private DBHelper AgroPol;
    private int IdUser;
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
        loadData();
    }

    private void loadData() {
        //wczytanie zamówień z bazy danych do recyclerView
            Cursor result = AgroPol.getDate("Select ID,Date_of_request,Price from request where IDClient = " + IdUser);
            System.out.println(result.isAfterLast());
            try {
                while (result.isAfterLast() == false) {
                    itemOfRecyclerViewOrders.add(new ItemOfRecyclerViewOrder(Integer.parseInt(result.getString(0)), result.getString(1), Double.parseDouble(result.getString(2))));
                    result.moveToNext();
                }
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CatalogOfOrderAdapter(itemOfRecyclerViewOrders);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CatalogAdapter.OnItemClickListener() {
            @Override
            public void onShowClick(int position) {
                Intent intent = new Intent(Orders.super.getApplicationContext(),
                        DetailsOfOrder.class);
                intent.putExtra("IdOrder",itemOfRecyclerViewOrders.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void createListeners() {
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Orders.super.getApplicationContext(),
                            MakeOrder.class);
                    AgroPol.setData("request", new String[]{"IDClient"}, new String[]{String.valueOf(IdUser)});
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

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        btnAddOrder=findViewById(R.id.btn_add_order);
        AgroPol=new DBHelper(Orders.this);
    }
}