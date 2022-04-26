package com.example.agropol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.agropol.DBHelper.DBHelper;
import com.google.android.material.textfield.TextInputEditText;

public class EmployeeSignIn extends AppCompatActivity {

    private TextInputEditText login, password;
    private Button btnLogIn;
    private DBHelper AgroPol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_employee_sign_in);
        findViews();
        createListeners();
    }

    private void createListeners() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeSignIn.super.getApplicationContext(),
                                           EmployeeMenu.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        btnLogIn=findViewById(R.id.btn_log_in);
    }
}