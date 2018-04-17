package com.ba.yo.innovativepasswordmanager.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ba.yo.innovativepasswordmanager.controllers.EditEntryController;
import com.ba.yo.innovativepasswordmanager.EditEntryMVC;
import com.ba.yo.innovativepasswordmanager.R;

public class EditEntryActivity extends AppCompatActivity implements EditEntryMVC.View {
    private EditEntryMVC.Controller controller;
    private Button goButton;
    private EditText loginEd;
    private EditText passEd;
    private EditText descEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.add_entry));

        goButton = findViewById(R.id.addButton);
        loginEd = findViewById(R.id.loginField);
        passEd = findViewById(R.id.passwordField);
        descEd = findViewById(R.id.descrField);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.commitEntry(loginEd.getText() + "", passEd.getText() + "", descEd.getText() + "");
            }
        });

        controller = new EditEntryController(this);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }

    @Override
    public void setPassword(String pass) {
        passEd.setText(pass);
    }
}
