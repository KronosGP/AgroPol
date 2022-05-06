package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.agropol.DBHelper.DBHelper;

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

    private DBHelper AgroPol;
    private int IdUser;
    private int IdRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_make_order);
        findViews();
        createListeners();
        startSettings();
        Bundle bundle=getIntent().getExtras();
        IdUser=bundle.getInt("IdUser");
        loadData();
    }
    private void loadData() {
        Cursor result = AgroPol.getDate("Select * from client where ID="+IdUser);
        name.setText(result.getString(3));
        surname.setText(result.getString(4));
        email.setText(result.getString(5));
        number.setText(result.getString(6));
        adress.setText(result.getString(7));
        try {
            result = AgroPol.getDate("Select * from request where IDClient=" + IdUser);
            result.moveToLast();
            System.out.println(result.getString(0));
            IdRequest = Integer.parseInt(result.getString(0));
            titleOfID.setText("Id zamówienia: " + String.valueOf(IdRequest));
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        result=AgroPol.getDate("Select IDPlant,Quantity from details_request where IDRequest ="+IdRequest);
        //plant.Species,plant.Variety,plant.Price

        while(result.isAfterLast()==false)
        {
            Cursor result1=AgroPol.getDate("Select Species,Variety,Price from Plant where ID="+result.getString(0));
            int Quantity=Integer.parseInt(result.getString(1));
            Double price=Double.parseDouble(result1.getString(2));
            Double sum=Quantity*price;
            String species=result1.getString(0);
            String Variety=result1.getString(1);
            try{
            dataOfOrders.add(new DataOfOrders(species,Variety,Quantity,price,sum));
        }
        catch (Exception ex)
            {
                System.out.println(ex);
            }
            result.moveToNext();
        }


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
                        intent.putExtra("flag", 1);
                        intent.putExtra("IdRequest", IdRequest);
                        intent.putExtra("IdUser",IdUser);
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
                        try {
                            AgroPol.editData("request", "Id=" + IdRequest, new String[]{"Delivery"}, new String[]{"0.0"});
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                        Intent intent = new Intent(MakeOrder.super.getApplicationContext(),
                                SummaryOfOrder.class);
                        intent.putExtra("IdOrder",IdRequest);
                        intent.putExtra("IdUser",IdUser);
                        startActivity(intent);
                    }break;
                    case R.id.btn_with_transport:
                    {
                        try {
                            AgroPol.editData("request", "Id=" + IdRequest, new String[]{"Delivery"}, new String[]{"5.0"});
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                        Intent intent = new Intent(MakeOrder.super.getApplicationContext(),
                                SummaryOfOrder.class);
                        intent.putExtra("IdOrder",IdRequest);
                        intent.putExtra("IdUser",IdUser);
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
        AgroPol =new DBHelper(MakeOrder.this);
    }
}