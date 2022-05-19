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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_employe_orders);
        createToolbar();
        findViews();
        startSettings();
        loadData();
    }

    @Override
    protected void onResume() {
        View decorView = getWindow().getDecorView();
        super.onResume();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
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

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
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
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        //wczytanie zamówień do recyclerView
        Order order=new Order();
        itemOfRecyclerViewOrders = order.loadOrder(getApplicationContext(), itemOfRecyclerViewOrders, 0);
    }
}