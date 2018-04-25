package com.ba.yo.innovativepasswordmanager.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.WipeDataMVC;
import com.ba.yo.innovativepasswordmanager.controllers.DeleteDataController;

public class DeleteDataActivity extends AppCompatActivity implements WipeDataMVC.View {

    private CheckBox userConfirnment;
    private EditText password;
    private Button delete;
    private TextView label;
    private WipeDataMVC.Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        setTitle(getString(R.string.manage_data));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //create controller
        controller = new DeleteDataController(this);

        /*
         * Get references from activity into variables
         */
        password = (EditText) findViewById(R.id.password_delete);
        delete = (Button) findViewById(R.id.btn_delete);
        label = (TextView) findViewById(R.id.label_delete);
        userConfirnment = (CheckBox) findViewById(R.id.chk_delete);
        setElementsVisibility(false);

        /*
         * Assign handler for confirnment checkbox
         */
        userConfirnment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                controller.confirmWipe(b);
            }
        });

        /*
         * Assign handler for "delete" button
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.fullWipe();
            }
        });
    }

    /**
     * Hide or show elements to make them inaccessible/accessible by user
     *
     * @param state boolean state; True for visible, False otherwise
     */
    public void setElementsVisibility(boolean state) {
        int decision = state ? View.VISIBLE : View.INVISIBLE;
        label.setVisibility(decision);
        password.setVisibility(decision);
        delete.setVisibility(decision);
    }

    /**
     * Handler for "back" button on top of activity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
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
     * Show notification in the bottom of activity as a "Snackbar"
     *
     * @param message - string that user should read
     */
    @Override
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }

    /**
     * Check if user has confirmed full wipe
     * @return True if he did
     */
    @Override
    public boolean allowedToWipe() {
        return userConfirnment.isChecked();
    }

    /**
     * Return value from master password field that will need to be checked in order to do a complete wipe
     * @return String value of password field
     */
    @Override
    public String getMasterPassword() {
        return password.getText().toString();
    }
}
