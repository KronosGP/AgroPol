package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployeeMenu extends AppCompatActivity {

    private ConstraintLayout btnPlantCatalog, btnViewOrder, btnViewComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_employee_menu);
        findViews();
        createListeners();
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
                        Intent intent = new Intent(EmployeeMenu.super.getApplicationContext(),
                                Catalog.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_view_order:
                    {

                    }break;
                    case R.id.btn_view_complaint:
                    {

                    }break;
                }
            }
        };
        btnPlantCatalog.setOnClickListener(listener);
        btnViewOrder.setOnClickListener(listener);
        btnViewComplaint.setOnClickListener(listener);
    }

    private void findViews() {
        btnPlantCatalog=findViewById(R.id.btn_plant_catalog);
        btnViewOrder=findViewById(R.id.btn_view_order);
        btnViewComplaint=findViewById(R.id.btn_view_complaint);
    }
}