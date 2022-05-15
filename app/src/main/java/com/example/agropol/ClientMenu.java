package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ClientMenu extends AppCompatActivity {

    private ConstraintLayout btnPlantCatalog, btnOrder, btnComplaint;

    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_menu);
        findViews();
        createListeners();
        Bundle bundle=getIntent().getExtras();
        IdUser=bundle.getInt("IdUser");
        System.out.println(IdUser);
    }

    private void findViews() {
        btnPlantCatalog=findViewById(R.id.btn_plant_catalog);
        btnOrder=findViewById(R.id.btn_order);
        btnComplaint=findViewById(R.id.btn_complaint);
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_plant_catalog:
                    {
                        Intent intent = new Intent(ClientMenu.super.getApplicationContext(),
                                ClientCatalog.class);
                        intent.putExtra("flag","0");
                        startActivity(intent);
                    }break;
                    case R.id.btn_order:
                    {
                        //wysyłanie flagi do aktywności świadczącej o otwarciu katalogu Orders w trybie
                        //wyświetlania zamówień
                            Intent intent = new Intent(ClientMenu.super.getApplicationContext(),
                                    ClientOrders.class);
                            intent.putExtra("IdUser", IdUser);
                            intent.putExtra("Flag",0);
                            startActivity(intent);
                    }break;
                    case R.id.btn_complaint:
                    {
                        Intent intent = new Intent(ClientMenu.super.getApplicationContext(),
                                ClientComplaints.class);
                        intent.putExtra("IdUser",IdUser);
                        startActivity(intent);

                    }break;
                }
            }
        };
        btnPlantCatalog.setOnClickListener(listener);
        btnOrder.setOnClickListener(listener);
        btnComplaint.setOnClickListener(listener);
    }
}