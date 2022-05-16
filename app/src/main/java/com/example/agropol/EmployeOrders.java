package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.agropol.DBHelper.DBHelper;

import java.util.ArrayList;

public class EmployeOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CatalogOfOrderEmployeeAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders = new ArrayList<>();
    private DBHelper AgroPol;

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
        Cursor result =AgroPol.getDate("Select ID,Date_of_request,Price,Status from request");
        while(result.isAfterLast()==false)
        {
            int ID=result.getInt(0);
            String Date=result.getString(1);
            Double Price=result.getDouble(2);
            String Status=result.getString(3);
            itemOfRecyclerViewOrders.add(new ItemOfRecyclerViewOrder(ID,Date,Price,Status
            ));
            result.moveToNext();
        }
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

    private void findViews() {
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(EmployeOrders.this);
    }


}