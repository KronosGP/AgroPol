package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ClientMenu extends AppCompatActivity {

    private ConstraintLayout btnPlantCatalog, btnOrder, btnComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_menu);
        findViews();
        createListeners();
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
                        startActivity(intent);
                    }break;
                    case R.id.btn_order:
                    {
                        Intent intent = new Intent(ClientMenu.super.getApplicationContext(),
                                Orders.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_complaint:
                    {

                    }break;
                }
            }
        };
        btnPlantCatalog.setOnClickListener(listener);
        btnOrder.setOnClickListener(listener);
        btnComplaint.setOnClickListener(listener);
    }
}