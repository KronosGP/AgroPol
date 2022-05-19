package com.example.agropol.LayoutClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.agropol.MainClasses.DBHelper;
import com.example.agropol.R;
import com.google.android.material.textfield.TextInputEditText;

public class ClientSignIn extends AppCompatActivity {

    private TextInputEditText login, password;
    private Button btnLogIn;
    private DBHelper AgroPol;
    private int IdUser=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_client_sign_in);
        createToolbar();
        findViews();
        createListeners();
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
                        Intent intent = new Intent(ClientSignIn.super.getApplicationContext(),
                                                   EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                    case R.id.btn_logout:
                    {
                        Intent intent = new Intent(ClientSignIn.super.getApplicationContext(),
                                EmployeeOrClient.class);
                        startActivity(intent);
                    }break;
                }
            }
        };
        btnBack.setOnClickListener(listener);
        btnLogout.setOnClickListener(listener);
    }

    private void findViews() {
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        btnLogIn=findViewById(R.id.btn_log_in);
        AgroPol = new DBHelper(ClientSignIn.this);
    }

    private void createListeners() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logIn()==true) {
                Intent intent = new Intent(ClientSignIn.super.getApplicationContext(),
                                           ClientMenu.class);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("HELP_DATA", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("IdUser", IdUser);
                    editor.apply();
                startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Błędne dane logowania!",
                                   Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean logIn() {
        try {
            Cursor result=AgroPol.getDate("Select Count(*) from client where Login like '"+login.getText().toString()+"' and Password like '"+password.getText().toString()+"';");
            if(Integer.parseInt(result.getString(0))==1) {
                IdUser=Integer.parseInt(AgroPol.getDate("Select ID from client where Login like '"+login.getText().toString()+"' and Password like '"+password.getText().toString()+"';").getString(0));
                return true;
            }
            else
                return false;
        }
        catch (SQLiteException ex)
        {
            System.out.println(ex);
        }
        return false;
    }
}