package com.example.agropol;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.agropol.DBHelper.DBHelper;

import java.time.LocalDate;
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
        Cursor result =AgroPol.getDate("Select ID,Date_of_request,Price,Status,Date_of_delivery from request where Status not like 'tworzenie'");

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
            if(date.isEqual(dataN) || date.isBefore(dataN))
                AgroPol.editData("request","ID="+ID,new String[]{"Status"},new String[]{"Dostarczono"});
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