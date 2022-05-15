package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsOfEmployeeOrders extends AppCompatActivity {

    private TextView howId, howClient, howDateOfOrder, howDateOfDelivery,
            howCostOfPlants, howCostOfDelivery, howTotalSum, howStatus;
    private Button btnComeBack, btnChangeStatus;
    private RecyclerView recyclerView;
    private DataOfOrdersAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfOrders> dataOfOrders = new ArrayList<>();
    private int mainImage;

    //---------------------------------ChangeStatusWindowViews-----------------------------------//
    private CalendarView calendar;
    private Button btnCancel, btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_of_employee_orders);
        findViews();
        startSettings();
        createListeners();
        loadData();
    }

    private void loadData() {
        //wczytanie danych do textview i recyclerview
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id)
                {
                    case R.id.btn_come_back:
                    {
                        Intent intent = new Intent(DetailsOfEmployeeOrders.super.getApplicationContext(),
                                                   EmployeOrders.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_change_status:
                    {
                        openChangeStatusWindow();
                    }break;
                }
            }
        };
        btnComeBack.setOnClickListener(listener);
        btnChangeStatus.setOnClickListener(listener);
    }

    private void openChangeStatusWindow() {
        Dialog changeStatusWindow = new Dialog(this);
        changeStatusWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        changeStatusWindow.setContentView(R.layout.layout_dialog_change_status_order);
        changeStatusWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changeStatusWindow.show();
        findchangeStatusWindowViews(changeStatusWindow);
        createAndAddListeners(changeStatusWindow);

    }

    private void createAndAddListeners(Dialog changeStatusWindow) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_accept:
                    {
                        //zczytanie daty dostawy/odbioru z CalendarView oraz walidacja danych np
                        //czy zaznaczona data nie jest wsteczna w porównaniu z dzisiejszą oraz
                        //czy nie jest dłuższa niż np 30 dni zrób te walidacje ja potem jak to oprogramujessz
                        //zrobie jakieś wyświetlanie tych komunikatów.
                        //następnie wczytanie daty do bazy danych oraz powrót to aktywności z zamówieniami.
                        Intent intent = new Intent(DetailsOfEmployeeOrders.super.getApplicationContext(),
                                                   EmployeOrders.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_cancel:
                    {
                        changeStatusWindow.dismiss();
                    }break;
                }
            }
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }

    private void findchangeStatusWindowViews(Dialog changeStatusWindow) {
        calendar=changeStatusWindow.findViewById(R.id.calendar);
        btnAccept=changeStatusWindow.findViewById(R.id.btn_accept);
        btnCancel=changeStatusWindow.findViewById(R.id.btn_cancel);

    }

    //funkcja ustawiająca rysunek w recyclerview ze itemami zamówienia
    private void getMainImage(String species) {
        switch (species)
        {
            case "Papryka":mainImage=R.drawable.image_pepper;break;
            case "Fasola":mainImage=R.drawable.image_beans;break;
            case "Bakłażan":mainImage=R.drawable.image_aubergine;break;
            case "Kapusta pekińska":mainImage=R.drawable.image_cabbagepekin;break;
            case "Ogórek":mainImage=R.drawable.image_cucumber;break;
            case "Marchewka":mainImage=R.drawable.image_carrot;break;
            case "Pietruszka":mainImage=R.drawable.image_parsley;break;
            case "Dynia":mainImage=R.drawable.image_pumkin;break;
            case "Rzodkiweka":mainImage=R.drawable.image_radish;break;
            case "Pomidor":mainImage=R.drawable.image_tomato;break;
        }
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
        btnChangeStatus=findViewById(R.id.btn_change_status);
        recyclerView=findViewById(R.id.recycler_view);
    }
}