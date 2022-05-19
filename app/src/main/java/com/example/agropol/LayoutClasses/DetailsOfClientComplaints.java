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
import com.example.agropol.R;

public class DetailsOfClientComplaints extends AppCompatActivity {

    private TextView howComplaintId, howClient, howDateOfComplaint, howIdOfOrder, howStatus, howDescribe;
    private Button btnComeBack;
    private int IdUser;
    private int IdComplaint;
    private DBHelper AgroPol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_of_client_complaints);
        createToolbar();
        getSharedPreferences();
        findViews();
        createListeners();
        loadData();
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
                        Intent intent = new Intent(DetailsOfClientComplaints.super.getApplicationContext(),
                                ClientComplaints.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IdUser",IdUser);
                        editor.apply();
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(DetailsOfClientComplaints.super.getApplicationContext(),
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
        IdComplaint = sharedPreferences.getInt("IdComplaint",0);
    }

    private void findViews() {
        howComplaintId = findViewById(R.id.how_complaint_id);
        howClient = findViewById(R.id.how_client);
        howDateOfComplaint = findViewById(R.id.how_date_of_complaint);
        howIdOfOrder = findViewById(R.id.how_id_of_order);
        howStatus = findViewById(R.id.how_status);
        howDescribe = findViewById(R.id.how_describe);
        btnComeBack=findViewById(R.id.btn_come_back);
        AgroPol = new DBHelper(DetailsOfClientComplaints.this);
    }

    private void createListeners() {
        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //powót do aktywności katalogu reklamacji
                Intent intent = new Intent(DetailsOfClientComplaints.super.getApplicationContext(),
                        ClientComplaints.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadData() {
        //wczytanie danych to TextView w tym dane składającego, date reklamacji, id zamówienia,
        //statusu oraz treści
        Cursor result=AgroPol.getDate("Select * from complaint where ID="+IdComplaint);
        howComplaintId.setText("Reklamacja\nID: "+result.getString(0));
        howIdOfOrder.setText(result.getString(2));
        howStatus.setText(result.getString(5));
        howDescribe.setText(result.getString(4));
        howDateOfComplaint.setText("\n"+result.getString(6));
        result=AgroPol.getDate("Select Name,Surname from client where ID="+IdUser);
        howClient.setText(result.getString(0)+" "+result.getString(1));
    }
}