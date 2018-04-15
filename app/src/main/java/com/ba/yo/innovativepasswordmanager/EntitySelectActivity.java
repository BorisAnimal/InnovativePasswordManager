package com.ba.yo.innovativepasswordmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class EntitySelectActivity extends AppCompatActivity {

    //TODO: implement EntitySelectMVC.View interface

    private ListView listView;
    private AuthEntryAdapter aAdapter;
    private EntitySelectMVC.Controller esController;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton addEntry = (FloatingActionButton) findViewById(R.id.addEntry);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EntitySelectActivity.this, EditEntryActivity.class));
            }
        });

        listView = (ListView) findViewById(R.id.entry_selector);
        ArrayList<AuthEntry> authList = new ArrayList<>();
        for(int i = 0;i<100;i++) {
            authList.add(new AuthEntry(R.drawable.ic_key, "Entry", "test"));
        }

        aAdapter = new AuthEntryAdapter(this, authList);
        listView.setAdapter(aAdapter);

        
        listView.setOnTouchListener(new View.OnTouchListener() {
            float height;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float height = event.getY();
                if(action == MotionEvent.ACTION_DOWN){
                    this.height = height;
                }else if(action == MotionEvent.ACTION_UP){
                    if(this.height < height){
                        addEntry.show();

                    }else if(this.height > height){
                        addEntry.hide();
                    }
                }
                return false;
            }

        });
        esController = new EntitySelectController(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entity_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_master_password) {
            startActivity(new Intent(EntitySelectActivity.this, EditMasterPasswordActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
