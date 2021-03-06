package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.R;

import java.util.ArrayList;

public class DetailsOfClientOrder extends AppCompatActivity {

    private TextView howId, howClient, howDateOfOrder, howDateOfDelivery,
            howCostOfPlants, howCostOfDelivery, howTotalSum, howStatus;
    private Button btnComeBack;
    private RecyclerView recyclerView;
    private DataOfOrdersAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfOrders> dataOfOrders = new ArrayList<>();
    private int IdUser;
    private int IdRequest;
    private DBHelper AgroPol;
    int mainImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_of_client_order);
        createToolbar();
        startOperations();
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
                        Intent intent = new Intent(DetailsOfClientOrder.super.getApplicationContext(),
                                                   ClientOrders.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IdUser",IdUser);
                        editor.apply();
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(DetailsOfClientOrder.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    private void startOperations() {
        try {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
            IdUser = sharedPreferences.getInt("IdUser", 0);
            IdRequest =sharedPreferences.getInt("IdOrder",0);
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
        //trzeba zczyta?? z bazy danych
        howStatus.setText(String.valueOf(result.getString(6)));


        result=AgroPol.getDate("Select IDPlant,Quantity from details_request where IDRequest ="+IdRequest);

        while(result.isAfterLast()==false) {
            Cursor result1 = AgroPol.getDate("Select Species,Variety,Price from Plant where ID=" + result.getString(0));
            int Quantity = Integer.parseInt(result.getString(1));
            Double price = Double.parseDouble(result1.getString(2));
            Double sum = Quantity * price;
            String species = result1.getString(0);
            String Variety = result1.getString(1);
            getMainImage(species);
            dataOfOrders.add(new DataOfOrders(species, Variety, Quantity, price, sum, mainImage));
            result.moveToNext();
        }
    }

    private void getMainImage(String species) {
        switch (species)
        {
            case "Papryka":mainImage=R.drawable.image_pepper;break;
            case "Fasola":mainImage=R.drawable.image_beans;break;
            case "Bak??a??an":mainImage=R.drawable.image_aubergine;break;
            case "Kapusta Peki??ska":mainImage=R.drawable.image_cabbagepekin;break;
            case "Og??rek":mainImage=R.drawable.image_cucumber;break;
            case "Marchewka":mainImage=R.drawable.image_carrot;break;
            case "Pietruszka":mainImage=R.drawable.image_parsley;break;
            case "Dynia":mainImage=R.drawable.image_pumkin;break;
            case "Rzodkiweka":mainImage=R.drawable.image_radish;break;
            case "Pomidor":mainImage=R.drawable.image_tomato;break;
        }
    }

    private void createListeners() {
        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        howStatus=findViewById(R.id.how_status);
        btnComeBack=findViewById(R.id.btn_come_back);
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(DetailsOfClientOrder.this);

    }
}