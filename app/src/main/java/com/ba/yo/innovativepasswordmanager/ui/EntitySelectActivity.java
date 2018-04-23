package com.ba.yo.innovativepasswordmanager.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ba.yo.innovativepasswordmanager.controllers.EntitySelectController;
import com.ba.yo.innovativepasswordmanager.EntitySelectMVC;
import com.ba.yo.innovativepasswordmanager.R;

import java.util.ArrayList;

import retrofit2.http.HEAD;

public class EntitySelectActivity extends AppCompatActivity implements EntitySelectMVC.View {

    //TODO: implement EntitySelectMVC.View interface

    private ListView listView;
    private ArrayList<AuthEntry> authList;
    private AuthEntryAdapter aAdapter;
    private EntitySelectMVC.Controller controller;
    private FloatingActionButton addEntry;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addEntry = (FloatingActionButton) findViewById(R.id.addEntry);
        addEntry.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startActivity(new Intent(EntitySelectActivity.this, EditEntryActivity.class));
            }
        });

        listView = (ListView) findViewById(R.id.entry_selector);
        listView.setOnTouchListener(new android.view.View.OnTouchListener() {
            float height;

            @Override
            public boolean onTouch(android.view.View v, MotionEvent event) {
                int action = event.getAction();
                float height = event.getY();
                if (action == MotionEvent.ACTION_DOWN) {
                    this.height = height;
                } else if (action == MotionEvent.ACTION_UP) {
                    if (this.height < height) {
                        addEntry.show();

                    } else if (this.height > height) {
                        addEntry.hide();
                    }
                }
                return false;
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                showNotification("Send password for entry with id: "+authList.get(i).getaId());
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                Intent intent = new Intent(EntitySelectActivity.this, EditEntryActivity.class);
                intent.putExtra("ENTRY_ID", authList.get(i).getaId());
                startActivity(intent);
                return false;
            }
        });

        authList = new ArrayList<>();
        controller = new EntitySelectController(this);
        controller.getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.getData();
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

    private void updateList() {
        aAdapter = new AuthEntryAdapter(this, authList);
        listView.setAdapter(aAdapter);
    }

    public void addEntity(String description, String id) {
        authList.add(new AuthEntry(R.drawable.ic_key, description, id));
        updateList();
    }

    public void clearList() {
        authList = new ArrayList<>();
        updateList();
    }

    public void showNotification(String notificationText) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, notificationText, Snackbar.LENGTH_LONG);
        mySnackbar.show();
        //TODO: Fab intersection avoidance
    }


}
