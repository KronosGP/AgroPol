package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DetailsOfEmployeeComplaints extends AppCompatActivity {

    private TextView howComplaintId, howClient, howDateOfComplaint, howIdOfOrder, howStatus, howDescribe;
    private Button btnComeBack, btnChangeStatus;

    //---------------------------------ChangeStatusWindow------------------------------------//
    private Button btn_positive_complaint, btn_negative_complaint, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_of_employee_complaints);
        findViews();
        createListeners();
        loadData();
    }

    private void loadData() {
        //wczytanie danych do textView
    }

    private void createListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_come_back:
                    {
                        //powrót do aktywności z listą reklamacji
                        Intent intent = new Intent(DetailsOfEmployeeComplaints.super.getApplicationContext(),
                                                   EmployeeComplaints.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_change_status:
                    {
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
        changeStatusWindow.setContentView(R.layout.layout_dialog_change_status_complaint);
        changeStatusWindow.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changeStatusWindow.show();
        findchangeStatusWindowViews(changeStatusWindow);
        createAndAddListeners(changeStatusWindow);

    }

    private void createAndAddListeners(Dialog changeStatusWindow) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=v.getId();
                switch (id)
                {
                    case R.id.btn_positive_complaint:
                    {
                        //update na bazie danych ze statusu złożono na rozparzono pozytywnie
                        //przejście do aktywności z listą reklamacji
                        Intent intent = new Intent(DetailsOfEmployeeComplaints.super.getApplicationContext(),
                                                   EmployeeComplaints.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_negative_complaint:
                    {
                        //update na bazie danych ze statusu złożono na rozparzono pozytywnie
                        //przejście do aktywności z listą reklamacji
                        Intent intent = new Intent(DetailsOfEmployeeComplaints.super.getApplicationContext(),
                                EmployeeComplaints.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_cancel:
                    {
                        changeStatusWindow.dismiss();
                    }break;
                }
            }
        };
        btn_positive_complaint.setOnClickListener(listener);
        btn_negative_complaint.setOnClickListener(listener);
        btn_cancel.setOnClickListener(listener);
    }

    private void findchangeStatusWindowViews(Dialog changeStatusWindow) {
        btn_positive_complaint=changeStatusWindow.findViewById(R.id.btn_positive_complaint);
        btn_negative_complaint=changeStatusWindow.findViewById(R.id.btn_negative_complaint);
        btn_cancel=changeStatusWindow.findViewById(R.id.btn_cancel);
    }

    private void findViews() {
        howComplaintId = findViewById(R.id.how_complaint_id);
        howClient = findViewById(R.id.how_client);
        howDateOfComplaint = findViewById(R.id.how_date_of_complaint);
        howIdOfOrder = findViewById(R.id.how_id_of_order);
        howStatus = findViewById(R.id.how_status);
        howDescribe = findViewById(R.id.how_describe);
        btnComeBack=findViewById(R.id.btn_come_back);
        btnChangeStatus=findViewById(R.id.btn_change_status);
    }
}