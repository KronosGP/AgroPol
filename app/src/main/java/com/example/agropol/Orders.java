package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogOfOrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders = new ArrayList<>();
    Button btnAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orders);
        findViews();
        startSettings();
        createListeners();
        loadData();
    }

    private void loadData() {
        //wczytanie zamówień z bazy danych do recyclerView
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
                //trzeba przekazać dane na temat konkretnego zamówienia do DetailsOFOrder aby móc
                //je tam wyświetlić
                Intent intent = new Intent(Orders.super.getApplicationContext(),
                                           DetailsOfOrder.class);
                startActivity(intent);
            }
        });
    }

    private void createListeners() {
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Orders.super.getApplicationContext(),
                                           MakeOrder.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        btnAddOrder=findViewById(R.id.btn_add_order);
    }
}