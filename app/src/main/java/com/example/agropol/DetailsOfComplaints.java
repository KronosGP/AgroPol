package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsOfComplaints extends AppCompatActivity {

    private TextView howComplaintId, howClient, howDateOfComplaint, howIdOfOrder, howStatus, howDescribe;
    private Button btnComeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_complaints);
        findViews();
        createListeners();
        loadData();
    }

    private void loadData() {
        //wczytanie danych to TextView w tym dane składającego, date reklamacji, id zamówienia,
        //statusu oraz treści
    }

    private void createListeners() {
        btnComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //powót do aktywności katalogu reklamacji
                Intent intent = new Intent(DetailsOfComplaints.super.getApplicationContext(),
                                           Complaints.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        howComplaintId=findViewById(R.id.how_complaint_id);
        howClient=findViewById(R.id.how_client);
        howDateOfComplaint=findViewById(R.id.how_date_of_complaint);
        howIdOfOrder=findViewById(R.id.how_id_of_order);
        howStatus=findViewById(R.id.how_status);
        howDescribe=findViewById(R.id.how_describe);
        btnComeBack.findViewById(R.id.btn_come_back);
    }
}