package com.ba.yo.innovativepasswordmanager;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class EditEntryActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        if(getIntent().hasExtra("ENTRY_ID")){
            setTitle(getString(R.string.edit_entry));
            id = getIntent().getStringExtra("ENTRY_ID");
            Button saveButton = (Button) findViewById(R.id.addButton);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNotification("Current entry ID: "+id);
                }
            });
        }else{
            setTitle(getString(R.string.add_entry));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public void showNotification(String notificationText) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, notificationText, Snackbar.LENGTH_LONG);
        mySnackbar.show();
    }
}
