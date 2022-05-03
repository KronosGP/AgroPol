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
import android.widget.TextView;

import java.util.ArrayList;

public class MakeOrder extends AppCompatActivity {

    private TextView titleOfID, name, surname, adress, email, number;
    private Button btnAddPosition, btnContinue;
    private RecyclerView recyclerView;
    private DataOfOrdersAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfOrders> dataOfOrders = new ArrayList<>();


    private TextView priceOfTransport;
    private Button btnWithoutTransport, btnWithTransport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_make_order);
        findViews();
        createListeners();
        startSettings();
        loadData();
    }

    private void loadData() {
        //wczytanie danych do textView
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DataOfOrdersAdapter(dataOfOrders);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_add_position:
                    {
                        //otwarcie katalogu klienta w trybie dodawania pozycji, trzeba tu jakąś flagę wysłać do
                        //tej intencji
                        Intent intent = new Intent(MakeOrder.super.getApplicationContext(),
                                                   ClientCatalog.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_continue:
                    {
                        //kontynyacja składania zamówienia, otwarcie okna dialogowego z możliwością dostawy
                        openTransportDialog();
                    }break;
                }
            }
        };
        btnAddPosition.setOnClickListener(listener);
        btnContinue.setOnClickListener(listener);
    }

    private void openTransportDialog() {
        Dialog transportDialogWindow = new Dialog(this);
        transportDialogWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        transportDialogWindow.setContentView(R.layout.layout_dialog_transport);
        transportDialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        transportDialogWindow.show();
        findTransportDialogWindowViews(transportDialogWindow);
        createAndAddListeners(transportDialogWindow);
    }

    private void createAndAddListeners(Dialog transportDialogWindow) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    //tutaj w obu przypadkach niezależnie który przycisk wciśniemy otwiera się aktywność
                    //podsumowanie zamówienia, tylko trzeba gdzieś zapamiętać czy zamówienie z dostawą czy odbiór osobisty
                    case R.id.btn_without_transport:
                    {
                        Intent intent = new Intent(MakeOrder.super.getApplicationContext(),
                                                   SummaryOfOrder.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_with_transport:
                    {
                        Intent intent = new Intent(MakeOrder.super.getApplicationContext(),
                                SummaryOfOrder.class);
                        startActivity(intent);
                    }break;
                }

            }
        };
        btnWithoutTransport.setOnClickListener(listener);
        btnWithTransport.setOnClickListener(listener);
    }

    private void findTransportDialogWindowViews(Dialog transportDialogWindow) {
        priceOfTransport=transportDialogWindow.findViewById(R.id.price_of_transport);
        btnWithoutTransport=transportDialogWindow.findViewById(R.id.btn_without_transport);
        btnWithTransport=transportDialogWindow.findViewById(R.id.btn_with_transport);
    }

    private void findViews() {
        name=findViewById(R.id.name);
        surname=findViewById(R.id.surname);
        adress=findViewById(R.id.adress);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        recyclerView=findViewById(R.id.recycler_view);
        titleOfID=findViewById(R.id.title_of_id);
        btnAddPosition=findViewById(R.id.btn_add_position);
        btnContinue=findViewById(R.id.btn_continue);
    }
}