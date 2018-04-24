package com.ba.yo.innovativepasswordmanager.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.WipeDataMVC;

public class DeleteDataActivity extends AppCompatActivity implements WipeDataMVC.View {

    private EditText password;
    private Button delete;
    private TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        setTitle(getString(R.string.manage_data));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*
         * Get references from activity into variables
         */
        CheckBox chk_delete = (CheckBox) findViewById(R.id.chk_delete);
        password = (EditText) findViewById(R.id.password_delete);
        delete = (Button) findViewById(R.id.btn_delete);
        label = (TextView) findViewById(R.id.label_delete);
        setElementsVisibility(false);

        /*
         * Assign handler for confirnment checkbox
         */
        chk_delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setElementsVisibility(b);
            }
        });

        /*
         * Assign handler for "delete" button
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: action on button click
                //using password.getText()
            }
        });
    }

    /**
     * Hide or show elements to make them inaccessible/accessible by user
     * @param state boolean state; True for visible, False otherwise
     */
    private void setElementsVisibility(boolean state){
        int decision = state?View.VISIBLE:View.INVISIBLE;
        label.setVisibility(decision);
        password.setVisibility(decision);
        delete.setVisibility(decision);
    }

    /**
     * Handler for "back" button on top of activity
     * @param item
     * @return
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
     * @param message - string that user should read
     */
    @Override
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }
}
