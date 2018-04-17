package com.ba.yo.innovativepasswordmanager.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ba.yo.innovativepasswordmanager.controllers.LoginController;
import com.ba.yo.innovativepasswordmanager.LoginMVC;
import com.ba.yo.innovativepasswordmanager.R;

public class LoginActivity extends AppCompatActivity implements LoginMVC.View {
    private FloatingActionButton masterButton;
    private LoginMVC.Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        controller = new LoginController(this);

        masterButton = (FloatingActionButton) findViewById(R.id.master_button);
        masterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields()) {
                    String login = ((EditText) findViewById(R.id.master_login)).getText().toString().trim();
                    String password = ((EditText) findViewById(R.id.master_password)).getText().toString().trim();
                    controller.check(login, password);
                    makeTransitionToEntitySelect();
                } else {
                    showNotification("Login and Password fields can not be empty.");
                }
            }
        });
    }

    public boolean checkFields() {
        String login = ((EditText) findViewById(R.id.master_login)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.master_password)).getText().toString().trim();
        return (!login.isEmpty() && !password.isEmpty());
    }

    public void makeTransitionToEntitySelect() {
        Intent intent = new Intent(LoginActivity.this, EntitySelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showNotification(String notificationText) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, notificationText, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }
}
