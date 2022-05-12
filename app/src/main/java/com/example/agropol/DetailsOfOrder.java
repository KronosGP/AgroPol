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
    private DataOfOrdersAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfOrders> dataOfOrders = new ArrayList<>();
    private int IdUser;
    private int IdRequest;
    private DBHelper AgroPol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_of_order);
        try {
            Bundle bundle = getIntent().getExtras();
            IdUser = bundle.getInt("IdUser");
            IdRequest = bundle.getInt("IdOrder");
            System.out.println(IdRequest);
            findViews();
            startSettings();
            createListeners();
            loadData();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
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


        result=AgroPol.getDate("Select IDPlant,Quantity from details_request where IDRequest ="+IdRequest);
        //plant.Species,plant.Variety,plant.Price

        while(result.isAfterLast()==false) {
            Cursor result1 = AgroPol.getDate("Select Species,Variety,Price from Plant where ID=" + result.getString(0));
            int Quantity = Integer.parseInt(result.getString(1));
            Double price = Double.parseDouble(result1.getString(2));
            Double sum = Quantity * price;
            String species = result1.getString(0);
            String Variety = result1.getString(1);
            dataOfOrders.add(new DataOfOrders(species, Variety, Quantity, price, sum));
            result.moveToNext();
        }


    }

    private void createListeners() {
        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent= new Intent(DetailsOfOrder.super.getApplicationContext(),
                                          Orders.class);
                intent.putExtra("IdUser",IdUser);
                startActivity(intent);*/
                finish();
            }
        });
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DataOfOrdersAdapter(dataOfOrders);
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