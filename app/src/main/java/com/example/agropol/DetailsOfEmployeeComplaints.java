package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.agropol.DBHelper.DBHelper;

public class DetailsOfEmployeeComplaints extends AppCompatActivity {

    private TextView howComplaintId, howClient, howDateOfComplaint, howIdOfOrder, howStatus, howDescribe;
    private Button btnComeBack, btnChangeStatus;
    private int IdComplaint;
    private DBHelper AgroPol;

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
        Bundle bundle=getIntent().getExtras();
        IdComplaint=bundle.getInt("IdComplaint");
        Cursor result = AgroPol.getDate("Select * from complaint where Id="+IdComplaint);
        Cursor result2=AgroPol.getDate("Select Name,Surname from Client where Id="+result.getInt(1));
        howClient.setText(result2.getString(0)+" "+result2.getString(1));
        howDateOfComplaint.setText(result.getString(6));
        howIdOfOrder.setText(result.getString(2));
        howStatus.setText(result.getString(5));
        howDescribe.setText(result.getString(4));

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
                        /*Intent intent = new Intent(DetailsOfEmployeeComplaints.super.getApplicationContext(),
                                                   EmployeeComplaints.class);
                        startActivity(intent);*/
                        finish();
                    }break;
                    case R.id.btn_change_status:
                    {
                        String status=howStatus.getText().toString();
                        if(status.equals("W Przygotowaniu"))
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
                        AgroPol.editData("complaint","Id="+IdComplaint,new String[]{"Status"},new String[]{"Przyjęto"});
                        Intent intent = new Intent(DetailsOfEmployeeComplaints.super.getApplicationContext(),
                                                   EmployeeComplaints.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_negative_complaint:
                    {
                        //update na bazie danych ze statusu złożono na rozparzono pozytywnie
                        //przejście do aktywności z listą reklamacji
                        AgroPol.editData("complaint","Id="+IdComplaint,new String[]{"Status"},new String[]{"Odrzucono"});
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
        AgroPol=new DBHelper(DetailsOfEmployeeComplaints.this);
    }
}