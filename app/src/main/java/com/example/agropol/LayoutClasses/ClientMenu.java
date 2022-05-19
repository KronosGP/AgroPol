package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.agropol.R;

public class ClientMenu extends AppCompatActivity {

    private ConstraintLayout btnPlantCatalog, btnOrder, btnComplaint;

    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_menu);
        createToolbar();
        findViews();
        createListeners();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
        IdUser = sharedPreferences.getInt("IdUser", 0);
        System.out.println(IdUser);
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
                        Intent intent = new Intent(ClientMenu.super.getApplicationContext(),
                                ClientSignIn.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(ClientMenu.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        View decorView = getWindow().getDecorView();
        super.onResume();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
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
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("flag",0);
                        editor.apply();
                        startActivity(intent);
                    }break;
                    case R.id.btn_order:
                    {
                        //wysyłanie flagi do aktywności świadczącej o otwarciu katalogu Orders w trybie
                        //wyświetlania zamówień
                            Intent intent = new Intent(ClientMenu.super.getApplicationContext(),
                                    ClientOrders.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("Flag",0);
                        editor.putInt("IdUser", IdUser);
                        editor.apply();
                            startActivity(intent);
                    }break;
                    case R.id.btn_complaint:
                    {
                        Intent intent = new Intent(ClientMenu.super.getApplicationContext(),
                                ClientComplaints.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IdUser", IdUser);
                        editor.apply();
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