package com.ba.yo.innovativepasswordmanager.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ba.yo.innovativepasswordmanager.R;

public class ManageDataSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data_select);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.manage_data));

        Button btn_dump_data = (Button) findViewById(R.id.btn_dump_data);
        Button btn_delete_data = (Button) findViewById(R.id.btn_delete_data);

        btn_dump_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ManageDataSelectActivity.this, DumpDataActivity.class));
            }
        });

        btn_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ManageDataSelectActivity.this, DeleteDataActivity.class));
            }
        });
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
}
