package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agropol.DBHelper.DBHelper;

public class SummaryOfOrder extends AppCompatActivity {

    private TextView howClient, howDateOfOrder, howDateOfDelivery,
                     howCostOfPlants, howCostOfDelivery, howTotalSum;
    private Button btnAddOrder, btnCancelOrder;
    private DBHelper AgroPol;
    private int IdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_summary_of_order);
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
                    // w obu przypadkach powrót do aktywności Orders w przypadku przycisku addOrder wpis do bazy
                    // danych
                    case R.id.btn_add_order:
                    {
                        //AgroPol.setData("request",new String[]{},new String[]{});
                        Intent intent = new Intent(SummaryOfOrder.super.getApplicationContext(),
                                                   Orders.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_cancel_order:
                    {
                        Intent intent = new Intent(SummaryOfOrder.super.getApplicationContext(),
                                Orders.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnAddOrder.setOnClickListener(listener);
        btnCancelOrder.setOnClickListener(listener);
    }

    private void findViews() {
        howClient=findViewById(R.id.how_client);
        howDateOfOrder=findViewById(R.id.how_date_of_order);
        howDateOfDelivery=findViewById(R.id.how_date_of_delivery);
        howCostOfPlants=findViewById(R.id.how_cost_of_plants);
        howCostOfDelivery=findViewById(R.id.how_cost_of_delivery);
        howTotalSum=findViewById(R.id.how_total_sum);
        btnAddOrder=findViewById(R.id.btn_add_order);
        btnCancelOrder=findViewById(R.id.btn_cancel_order);
        AgroPol = new DBHelper(SummaryOfOrder.this);
    }
}