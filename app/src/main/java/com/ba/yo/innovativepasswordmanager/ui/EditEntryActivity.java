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

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditEntryActivity extends AppCompatActivity implements EditEntryMVC.View {
    private EditEntryMVC.Controller controller;
    @BindView(R.id.addButton)
    private Button goButton;
    @BindView(R.id.loginField)
    private EditText loginEd;
    @BindView(R.id.passwordField)
    private EditText passEd;
    @BindView(R.id.descrField)
    private EditText descEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.add_entry));

        ButterKnife.bind(this);
        controller = new EditEntryController(this);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.commitEntry(loginEd.getText() + "", passEd.getText() + "", descEd.getText() + "");
            }
        });
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
