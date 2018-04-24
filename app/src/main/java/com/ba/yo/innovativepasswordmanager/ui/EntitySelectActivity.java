package com.ba.yo.innovativepasswordmanager.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ba.yo.innovativepasswordmanager.AuthEntryAdapterCallback;
import com.ba.yo.innovativepasswordmanager.controllers.EntitySelectController;
import com.ba.yo.innovativepasswordmanager.EntitySelectMVC;
import com.ba.yo.innovativepasswordmanager.R;

import java.util.ArrayList;

public class EntitySelectActivity extends AppCompatActivity implements EntitySelectMVC.View, AuthEntryAdapterCallback {

    //TODO: implement EntitySelectMVC.View interface

    private ListView listView;
    private ArrayList<AuthEntry> authList;
    private AuthEntryAdapter aAdapter;
    private EntitySelectMVC.Controller controller;
    private FloatingActionButton addEntry;
    private int previousVisibleItem;

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

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (previousVisibleItem != firstVisibleItem) {
                    if (previousVisibleItem < firstVisibleItem) {
                        addEntry.hide();
                    } else {
                        addEntry.show();
                    }

                    previousVisibleItem = firstVisibleItem;
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                goToAppletSelectActivity(authList.get(i).getaId());
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
        aAdapter.setCallback(this);
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

    /**
     * Show notification in the bottom of activity as a "Snackbar"
     * @param message - string that user should read
     */
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
        //TODO: Fab intersection avoidance
    }

    /**
     * Show confirmation
     * @param name
     * @param id
     */
    public void deleteEntity(String name, String id){
        AlertDialog confirm = AskOption(name, id);
        confirm.show();
    }

    /**
     * Construct confirmation dialog for entity deletion procedure
     * @param entityDescription
     * @param id
     * @return
     */
    private AlertDialog AskOption(String entityDescription, String id)
    {
        return new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to delete \""+entityDescription+"\"?")
                .setIcon(R.drawable.ic_key)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //DELETE ENTITY WITH ID->String
                        //controller.delete(id);
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

    }

    /**
     * Make transition to EditEntity field, that happens when user selects certain Auth entry.
     * When entry is selected its ID is taken to next activity by which editor will identify entity on server.
     * @param id ID of selected entity
     */
    public void goToEditEntityActivity(String id){
        Intent intent = new Intent(EntitySelectActivity.this, EditEntryActivity.class);
        intent.putExtra("ENTRY_ID", id);
        startActivity(intent);
    }
    public void goToAppletSelectActivity(String id){
        Intent intent = new Intent(EntitySelectActivity.this, AppletSelectActivity.class);
        intent.putExtra("ENTRY_ID", id);
        startActivity(intent);
    }
}
