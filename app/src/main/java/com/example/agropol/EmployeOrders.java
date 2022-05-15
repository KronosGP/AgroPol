package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        findViews();
        startSettings();
        loadData();
    }
    private void loadData() {
//        wczytanie zamówień do recyclerView
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
            }
        });
    }

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
    }


}