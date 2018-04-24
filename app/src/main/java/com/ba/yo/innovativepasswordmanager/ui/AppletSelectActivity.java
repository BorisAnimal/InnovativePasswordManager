package com.ba.yo.innovativepasswordmanager.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ba.yo.innovativepasswordmanager.AppletSelectMVC;
import com.ba.yo.innovativepasswordmanager.EntitySelectMVC;
import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.controllers.AppletSelectController;

import java.util.ArrayList;

public class AppletSelectActivity extends AppCompatActivity implements AppletSelectMVC.View {

    private ListView listView;
    private ArrayList<AppletEntry> appletList;
    private AppletEntryAdapter apAdapter;
    private AppletSelectMVC.Controller controller;
    private String entityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applet_select);

        //Set up action bar on top of activity: enable "back" button and set title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.select_applet));

        // Check extras and get entity ID from previous activity
        if (getIntent().hasExtra("ENTRY_ID")) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                this.entityId = extras.getString("ENTRY_ID");
            }
        }

        listView = (ListView) findViewById(R.id.applet_selector);

        // Add handler for applet entry clicking
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                String appletId = appletList.get(i).getapId();
                //TODO: handle sending password
                showNotification("We have Entity: " + entityId + ", and Applet: " + appletId);
            }
        });

        // Assign key containers of activity
        appletList = new ArrayList<>();
        controller = new AppletSelectController(this);

        addApplet("hello1", "some_id");
        addApplet("hello2", "some_id_2");
    }

    /**
     * Procedure that is called when activity becomes visible after being covered by some editor-activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        //TODO: handle functionality for "controller.getData()"
    }

    /**
     * Update current list in activity
     */
    private void updateList() {
        apAdapter = new AppletEntryAdapter(this, appletList);
        listView.setAdapter(apAdapter);
    }

    /**
     * Add applet entry to list in activity
     *
     * @param description - description of applet
     * @param id          - hide it in element (such in 'tag' field). Use it in intent when item selected
     */
    @Override
    public void addApplet(String description, String id) {
        appletList.add(new AppletEntry(R.drawable.ic_desktop, description, id));
        updateList();
    }

    /**
     * Delete all elements from applet list in activity
     */
    @Override
    public void clearList() {
        appletList = new ArrayList<>();
        updateList();
    }

    /**
     *
     */
    @Override
    public void onSuccessTransfer() {

    }

    /**
     * Close current activity and go to previous, i.e.
     * to EntitySelectActivity
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
        //TODO: Fab intersection avoidance
    }

    /**
     * Handler for "back" button on top of activity
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
