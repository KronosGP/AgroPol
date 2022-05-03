package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Orders extends AppCompatActivity {


    Button btnAddOrder;
    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_orders);
        findViews();
        createListeners();
        /*Bundle bundle=getIntent().getExtras();
        IdUser=bundle.getInt("IdUser");*/
    }

    private void createListeners() {
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Orders.super.getApplicationContext(),
                                           MakeOrder.class);
                //intent.putExtra("IdUser",IdUser);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        btnAddOrder=findViewById(R.id.btn_add_order);
    }
}