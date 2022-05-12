package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.agropol.DBHelper.DBHelper;

public class DetailsOfComplaints extends AppCompatActivity {

    private TextView howComplaintId, howClient, howDateOfComplaint, howIdOfOrder, howStatus, howDescribe;
    private Button btnComeBack;
    private int IdUser;
    private int IdComplaint;
    private DBHelper AgroPol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_complaints);
        Bundle bundle =getIntent().getExtras();
        IdUser=bundle.getInt("IdUser");
        IdComplaint=bundle.getInt("IdComplaint");
        findViews();
        createListeners();
        loadData();
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

    private void createListeners() {
        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //powót do aktywności katalogu reklamacji
                /*Intent intent = new Intent(DetailsOfComplaints.super.getApplicationContext(),
                                           Complaints.class);
                startActivity(intent);*/
                finish();
            }
        });
    }

    private void findViews() {
            howComplaintId = findViewById(R.id.how_complaint_id);
            howClient = findViewById(R.id.how_client);
            howDateOfComplaint = findViewById(R.id.how_date_of_complaint);
            howIdOfOrder = findViewById(R.id.how_id_of_order);
            howStatus = findViewById(R.id.how_status);
            howDescribe = findViewById(R.id.how_describe);
            btnComeBack=findViewById(R.id.btn_come_back);
            AgroPol = new DBHelper(DetailsOfComplaints.this);
    }
}