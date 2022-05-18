package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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
    private int IdRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_summary_of_order);
        Bundle bundle=getIntent().getExtras();
        IdRequest=bundle.getInt("IdOrder");
        IdUser=bundle.getInt("IdUser");
        findViews();
        createListeners();
        loadDate();
    }

    private void loadDate() {
        Cursor result =AgroPol.getDate("Select * from client where ID="+IdUser);
        howClient.setText(result.getString(3)+" "+result.getString(4));
        result=AgroPol.getDate("Select * from request where ID="+IdRequest);
        howDateOfOrder.setText("\n"+result.getString(3));
        howDateOfDelivery.setText("\n"+result.getString(4)+"\n");
        howCostOfPlants.setText(result.getString(2)+" zł");
        howCostOfDelivery.setText(result.getString(7)+" zł");
        Double sum=Double.parseDouble(result.getString(2))+Double.parseDouble(result.getString(7));
        howTotalSum.setText("\n"+sum+" zł");
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
                        //zmiana statusu zamówienia
                        AgroPol.editData("request","ID="+IdRequest,new String[]{"Status"},new String[]{"W Przygotowaniu"});
                        Intent intent = new Intent(SummaryOfOrder.super.getApplicationContext(),
                                                   ClientOrders.class);
                        intent.putExtra("IdUser",IdUser);
                        startActivity(intent);
                    }break;
                    case R.id.btn_cancel_order:
                    {
                        //dodanie z powrotem do możliwości zakupu sadzonki które były dodane do zamówienia
                        Cursor result =AgroPol.getDate("Select * from details_request where IDRequest="+IdRequest);
                        while(result.isAfterLast()==false)
                        {
                            Cursor result1=AgroPol.getDate("Select * from plant where ID="+result.getString(1));
                            int update=result1.getInt(3)+result.getInt(2);
                            AgroPol.editData("plant","ID="+result.getString(1),new String[]{"Quantity"},new String[]{String.valueOf(update)});
                            result.moveToNext();
                        }
                        AgroPol.delData("details_request","IDRequest="+IdRequest);
                        AgroPol.delData("request","ID="+IdRequest);

                        Intent intent = new Intent(SummaryOfOrder.super.getApplicationContext(),
                                ClientOrders.class);
                        intent.putExtra("IdUser",IdUser);
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