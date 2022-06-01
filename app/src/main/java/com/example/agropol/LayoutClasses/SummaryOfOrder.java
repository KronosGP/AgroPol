package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.agropol.MainClasses.Order;
import com.example.agropol.MainClasses.Plant;
import com.example.agropol.R;

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
        createToolbar();
        getSharedPreferences();
        findViews();
        createListeners();
        loadDate();
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
                        Intent intent = new Intent(SummaryOfOrder.super.getApplicationContext(),
                                MakeOrder.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IdUser",IdUser);
                        editor.putInt("Flag",0);
                        editor.apply();
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        resetDB();
                        Intent intent = new Intent(SummaryOfOrder.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }


    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
        IdUser = sharedPreferences.getInt("IdUser", 0);
        IdRequest = sharedPreferences.getInt("IdOrder", 0);
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
                        Order order=new Order();
                        order.editOrder(getApplicationContext(),"ID="+IdRequest,new String[]{"Status"},new String[]{"złożono"});

                        Intent intent = new Intent(SummaryOfOrder.super.getApplicationContext(),
                                ClientOrders.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IdUser",IdUser);
                        editor.putInt("Flag",0);
                        editor.apply();
                        startActivity(intent);
                    }break;
                    case R.id.btn_cancel_order:
                    {
                        //dodanie z powrotem do możliwości zakupu sadzonki które były dodane do zamówienia
                        resetDB();

                        Intent intent = new Intent(SummaryOfOrder.super.getApplicationContext(),
                                ClientOrders.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IdUser",IdUser);
                        editor.apply();
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnAddOrder.setOnClickListener(listener);
        btnCancelOrder.setOnClickListener(listener);
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

    private void resetDB() {//funkcja wykorzystywana do przywrócenia sadzonek do sprzedaży po anulowaniu lub wylogowaniu
        Cursor result =AgroPol.getDate("Select * from details_request where IDRequest="+IdRequest);
        while(result.isAfterLast()==false)
        {
            Cursor result1=AgroPol.getDate("Select * from plant where ID="+result.getString(1));
            int update=result1.getInt(3)+result.getInt(2);
            Plant plant=new Plant();
            plant.editPlant(getApplicationContext(),result1.getString(0),result1.getString(1),result1.getString(2),String.valueOf(update),result1.getString(4),result1.getString(5));
            result.moveToNext();
        }
        AgroPol.delData("details_request","IDRequest="+IdRequest);
        AgroPol.delData("request","ID="+IdRequest);
    }
}