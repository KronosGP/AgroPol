package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.agropol.DBHelper.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class DetailsOfEmployeeOrders extends AppCompatActivity {

    private TextView howId, howClient, howDateOfOrder, howDateOfDelivery,
            howCostOfPlants, howCostOfDelivery, howTotalSum, howStatus;
    private Button btnComeBack, btnChangeStatus;
    private RecyclerView recyclerView;
    private DataOfOrdersAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataOfOrders> dataOfOrders = new ArrayList<>();
    private int mainImage;
    private int IdRequest;
    private int IdItem;
    private DBHelper AgroPol;
    private String saveData;

    //---------------------------------ChangeStatusWindowViews-----------------------------------//
    private CalendarView calendar;
    private Button btnCancel, btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_of_employee_orders);
        Bundle bundle=getIntent().getExtras();
        IdRequest=bundle.getInt("IdRequest");
        IdItem=bundle.getInt("IdItem");
        findViews();
        startSettings();
        createListeners();
        loadData();

    }

    private void loadData() {
        //wczytanie danych do textview i recyclerview
        Cursor result=AgroPol.getDate("Select * from request where ID="+IdRequest);
        //howId.setText(result.getString(0));
        howDateOfOrder.setText("\n"+result.getString(3));
        howDateOfDelivery.setText("\n"+result.getString(4)+"\n");
        howCostOfPlants.setText(result.getString(2));
        howCostOfDelivery.setText(result.getString(7));
        howTotalSum.setText("\n"+String.valueOf(result.getDouble(2)+result.getDouble(7)+0.0));
        howStatus.setText(result.getString(6));
        //Dane klienta
        result=AgroPol.getDate("Select Name,Surname from client where ID="+result.getString(1));
        howClient.setText(result.getString(0)+" "+result.getString(1));
        //zamówienie
        result=AgroPol.getDate("Select * from details_request where IDRequest="+IdRequest);
        while(result.isAfterLast()==false)
        {
            Cursor result1=AgroPol.getDate("Select * from plant where ID="+result.getString(1));
            String species=result1.getString(1);
            String variety=result1.getString(2);
            int quantity=result.getInt(2);
            double price=result1.getDouble(4);
            double sum=quantity*price;
            int image=result1.getInt(5);
            dataOfOrders.add(new DataOfOrders(species,variety,quantity,price,sum,image));
            result.moveToNext();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar oldDate=Calendar.getInstance();
        saveData=simpleDateFormat.format(oldDate.getTime());
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id)
                {
                    case R.id.btn_come_back:
                    {
                        /*Intent intent = new Intent(DetailsOfEmployeeOrders.super.getApplicationContext(),
                                                   EmployeOrders.class);
                        startActivity(intent);*/
                        finish();
                    }break;
                    case R.id.btn_change_status:
                    {
                        String status =howStatus.getText().toString();
                        if(status.equals("złożono"))
                        openChangeStatusWindow();
                    }break;
                }
            }
        };
        btnComeBack.setOnClickListener(listener);
        btnChangeStatus.setOnClickListener(listener);
    }

    private void openChangeStatusWindow() {
        Dialog changeStatusWindow = new Dialog(this);
        changeStatusWindow.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        changeStatusWindow.setContentView(R.layout.layout_dialog_change_status_order);
        changeStatusWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changeStatusWindow.show();
        findchangeStatusWindowViews(changeStatusWindow);
        createAndAddListeners(changeStatusWindow);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String data=sdf.format(calendar.getDate());
                Calendar c = Calendar.getInstance();
                try{
                    //Setting the date to the given date
                    c.setTime(sdf.parse(data));
                }catch(ParseException e){
                    e.printStackTrace();
                }

                //Number of Days to add
                c.add(Calendar.DAY_OF_MONTH, 30);
                //Date after adding the days to the given date
                String newDate = sdf.format(c.getTime());


                LocalDate now=LocalDate.of(Integer.parseInt(data.split("-")[0]),Integer.parseInt(data.split("-")[1])-1,Integer.parseInt(data.split("-")[2]));
                LocalDate now2=LocalDate.of(Integer.parseInt(newDate.split("-")[0]),Integer.parseInt(newDate.split("-")[1])-1,Integer.parseInt(newDate.split("-")[2]));
                LocalDate later=LocalDate.of(year,month,dayOfMonth);

                String temp;
                if(month+1<10)
                    temp=String.valueOf(year)+"-0"+String.valueOf(month+1);
                else temp=String.valueOf(year)+"-"+String.valueOf(month+1);
                if(dayOfMonth<10)
                    temp=temp+"-0"+dayOfMonth;
                else temp = temp+"-"+dayOfMonth;

                if((now.isBefore(later) || now.isEqual(later)) && now2.isAfter(later)) {
                    saveData=temp;
                    btnAccept.setClickable(true);
                }
                else {
                    btnAccept.setClickable(false);
                }
            }
        });
    }

    private void createAndAddListeners(Dialog changeStatusWindow) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_accept:
                    {
                        //zczytanie daty dostawy/odbioru z CalendarView oraz walidacja danych np
                        //czy zaznaczona data nie jest wsteczna w porównaniu z dzisiejszą oraz
                        //czy nie jest dłuższa niż np 30 dni zrób te walidacje ja potem jak to oprogramujessz
                        //zrobie jakieś wyświetlanie tych komunikatów.
                        //następnie wczytanie daty do bazy danych oraz powrót to aktywności z zamówieniami.

                        /*Intent intent = new Intent(DetailsOfEmployeeOrders.super.getApplicationContext(),
                                                   EmployeOrders.class);
                        startActivity(intent);*/
                        AgroPol.editData("Request","ID="+IdRequest,new String[]{"Date_of_delivery","Status"},new String[]{saveData,"Zamówienie gotowe"});
                        Intent wynik=new Intent();
                        wynik.putExtra("ID",IdItem);
                        setResult(1,wynik);
                        finish();
                    }break;
                    case R.id.btn_cancel:
                    {
                        changeStatusWindow.dismiss();
                    }break;
                }
            }
        };
        btnAccept.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
    }

    private void findchangeStatusWindowViews(Dialog changeStatusWindow) {
        calendar=changeStatusWindow.findViewById(R.id.calendar);
        btnAccept=changeStatusWindow.findViewById(R.id.btn_accept);
        btnCancel=changeStatusWindow.findViewById(R.id.btn_cancel);

    }

    //funkcja ustawiająca rysunek w recyclerview ze itemami zamówienia
    private void getMainImage(String species) {
        switch (species)
        {
            case "Papryka":mainImage=R.drawable.image_pepper;break;
            case "Fasola":mainImage=R.drawable.image_beans;break;
            case "Bakłażan":mainImage=R.drawable.image_aubergine;break;
            case "Kapusta pekińska":mainImage=R.drawable.image_cabbagepekin;break;
            case "Ogórek":mainImage=R.drawable.image_cucumber;break;
            case "Marchewka":mainImage=R.drawable.image_carrot;break;
            case "Pietruszka":mainImage=R.drawable.image_parsley;break;
            case "Dynia":mainImage=R.drawable.image_pumkin;break;
            case "Rzodkiweka":mainImage=R.drawable.image_radish;break;
            case "Pomidor":mainImage=R.drawable.image_tomato;break;
        }
    }

    private void startSettings() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DataOfOrdersAdapter(dataOfOrders);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void findViews() {
        howId=findViewById(R.id.how_id);
        howClient=findViewById(R.id.how_client);
        howDateOfOrder=findViewById(R.id.how_date_of_order);
        howDateOfDelivery=findViewById(R.id.how_date_of_delivery);
        howCostOfPlants=findViewById(R.id.how_cost_of_plants);
        howCostOfDelivery=findViewById(R.id.how_cost_of_delivery);
        howTotalSum=findViewById(R.id.how_total_sum);
        howStatus=findViewById(R.id.how_status);
        btnComeBack=findViewById(R.id.btn_come_back);
        btnChangeStatus=findViewById(R.id.btn_change_status);
        recyclerView=findViewById(R.id.recycler_view);
        AgroPol=new DBHelper(DetailsOfEmployeeOrders.this);
    }
}