package com.ba.yo.innovativepasswordmanager.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ba.yo.innovativepasswordmanager.ChangeMasterPasswordMVP;
import com.ba.yo.innovativepasswordmanager.EditEntryMVC;
import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.controllers.ChangeMasterPasswordController;

public class EditMasterPasswordActivity extends AppCompatActivity implements ChangeMasterPasswordMVP.View {
    private EditText oldPassword;
    private EditText newPassword;
    private EditText newPasswordRepeat;
    private Button changePassButton;
    private ChangeMasterPasswordMVP.Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_master_password);

        /*
         * Set up action bar on top of activity: enable "back" button and set title
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.change_master_password));

        /*
         * Assign references from activity to variables
         */
        oldPassword = (EditText) findViewById(R.id.oldPassField);
        newPassword = (EditText) findViewById(R.id.passwordField);
        newPasswordRepeat = (EditText) findViewById(R.id.passwordRepeatField);
        changePassButton = (Button) findViewById(R.id.saveButton);

        /*
         * Assign handler for button
         */
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: call editing procedure (also use getOldMP(), getNewMP(), getNewMPRepeat())
            }
        });

        /*
         * Create controller
         */
        controller = new ChangeMasterPasswordController(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        goToEntitySelectActivity();
        return true;
    }

    /**
     * Return to previous activity, i.e. EntitySelect
     */
    @Override
    public void goToEntitySelectActivity() {
        finish();
    }

    /**
     * Get current value from old master password field
     * @return String value of password
     */
    @Override
    public String getOldMP() {
        return oldPassword.getText().toString();
    }

    /**
     * Get current value from master password field
     * @return String value of password
     */
    @Override
    public String getNewMP() {
        return newPassword.getText().toString();
    }

    /**
     * Get current value from master password(repeat) field
     * @return String value of password
     */
    @Override
    public String getNewMPRepeat() {
        return newPasswordRepeat.getText().toString();
    }

    /**
     * Show notification in the bottom of activity as a "Snackbar"
     * @param message - string that user should read
     */
    @Override
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }
}
