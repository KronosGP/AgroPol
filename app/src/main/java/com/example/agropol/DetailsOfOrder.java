package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agropol.DBHelper.DBHelper;

import java.util.ArrayList;

public class DetailsOfOrder extends AppCompatActivity {

    private TextView howId, howClient, howDateOfOrder, howDateOfDelivery,
            howCostOfPlants, howCostOfDelivery, howTotalSum;
    private Button btnComeBack;
    private RecyclerView recyclerView;
    private CatalogOfOrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ItemOfRecyclerViewOrder> itemOfRecyclerViewOrders = new ArrayList<>();
    private int IdUser;
    private int IdRequest;
    private DBHelper AgroPol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_of_order);
        Bundle bundle=getIntent().getExtras();
        IdUser=bundle.getInt("IdUser");
        IdRequest=bundle.getInt("IdOrder");
        System.out.println(IdRequest);
        findViews();
        startSettings();
        createListeners();
        loadData();
    }

    private void loadData() {
        Cursor result = AgroPol.getDate("Select * from client where ID=" + IdUser);
        howClient.setText(result.getString(3) + " " + result.getString(4));

        result = AgroPol.getDate("Select * from request where Id=" + IdRequest);
        howDateOfOrder.setText("\n"+result.getString(3));
        howDateOfDelivery.setText("\n"+result.getString(4)+"\n");
        Double cost = Double.parseDouble(result.getString(2));
        howCostOfPlants.setText(" "+String.valueOf(cost));
        Double delivery = Double.parseDouble(result.getString(7));
        howCostOfDelivery.setText(" "+String.valueOf(delivery));
        howTotalSum.setText("\n"+String.valueOf(cost + delivery));


        result=AgroPol.getDate("Select plant.Species,plant.Variety,details_request.Quantity,plant.Price from details_request,plant where IDRequest ="+IdRequest+" and plant.ID= details_request.IDPlant");
        while(result.isAfterLast()==false)
        {
            //Todo dokończyć wspisanie do listy
            result.moveToNext();
        }


    }

    private void createListeners() {
        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DetailsOfOrder.super.getApplicationContext(),
                                          Orders.class);
                intent.putExtra("IdUser",IdUser);
                startActivity(intent);
            }
        });
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CatalogOfOrderAdapter(itemOfRecyclerViewOrders);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void findViews() {
        howId=findViewById(R.id.how_id);
        howClient=findViewById(R.id.how_client);
        howDateOfOrder=findViewById(R.id.how_date_of_order);
        howDateOfDelivery=findViewById(R.id.how_date_of_delivery);
        howCostOfPlants=findViewById(R.id.how_cost_of_plants);
        howCostOfDelivery=findViewById(R.id.how_cost_of_delivery);
        howTotalSum=findViewById(R.id.how_total_sum);
        btnComeBack=findViewById(R.id.btn_come_back);
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(DetailsOfOrder.this);

    }
}