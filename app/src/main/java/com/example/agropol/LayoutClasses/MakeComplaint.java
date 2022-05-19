package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agropol.MainClasses.Complaint;
import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.R;

public class MakeComplaint extends AppCompatActivity {

    private TextView titleOfComplaintId, titleOfOrderID;
    private TextView name, surname, adress, email, number;
    private EditText howComplaint;
    private Button btnAddComplaint;
    private int IdUser;
    private int IdComplaint;
    private DBHelper AgroPol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_make_complaint);
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
                        Intent intent = new Intent(MakeComplaint.super.getApplicationContext(),
                                ClientComplaints.class);
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IdUser",IdUser);
                        editor.apply();
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(MakeComplaint.super.getApplicationContext(),
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
        IdComplaint =sharedPreferences.getInt("IdComplaint",0);
    }

    private void findViews() {
        titleOfComplaintId=findViewById(R.id.title_of_complaint_id);
        titleOfOrderID=findViewById(R.id.title_of_order_id);
        name=findViewById(R.id.name);
        surname=findViewById(R.id.surname);
        adress=findViewById(R.id.adress);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        howComplaint=findViewById(R.id.how_complaint);
        btnAddComplaint=findViewById(R.id.btn_add_complaint);
        AgroPol=new DBHelper(MakeComplaint.this);
    }

    private void createListeners() {
        btnAddComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //walidacja treści reklamacji, czy oby nie jest pusta, po czym dodanie do bazy danych oraz
                //powrót do aktywności z listą reklamacji
                if (!howComplaint.getText().toString().equals("")) {
                    //Zmiana Statusu oraz teści reklamacji
                    Complaint complaint=new Complaint();
                    complaint.editComplaint(getApplicationContext(),"ID="+IdComplaint, new String[]{"Contents", "Status"}, new String[]{howComplaint.getText().toString(), "złożono"});
                    Intent intent=new Intent(MakeComplaint.super.getApplicationContext(), ClientComplaints.class);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("IdUser",IdUser);
                    editor.apply();
                    startActivity(intent);
                }

            }
        });
    }

    private void loadData() {
        // wczytanie id reklamacji, id zamówienia oraz danych osobowych
        try {
            Cursor result = AgroPol.getDate("Select * from complaint where ID=" + IdComplaint);
            Cursor result2 = AgroPol.getDate("Select Name, Surname,Address,Email,Tel from Client where ID=" + IdUser);
            while (result.isAfterLast() == false) {
                titleOfComplaintId.setText("Id reklamacji: "+result.getString(0));
                titleOfOrderID.setText("Id zamówienia: "+result.getString(2));
                name.setText(result2.getString(0));
                surname.setText(result2.getString(1));
                adress.setText(result2.getString(2));
                email.setText(result2.getString(3));
                number.setText(result2.getString(4));
                result.moveToNext();
            }
        }
        catch (Exception ex)
        {
            System.out.println(4+" "+ex);
        }

    }
}