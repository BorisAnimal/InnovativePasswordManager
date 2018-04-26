package com.ba.yo.innovativepasswordmanager.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import com.ba.yo.innovativepasswordmanager.AuthEntryAdapterCallback;
import com.ba.yo.innovativepasswordmanager.EntitySelectMVC;
import com.ba.yo.innovativepasswordmanager.R;
import com.ba.yo.innovativepasswordmanager.controllers.EntitySelectController;

import java.util.ArrayList;

public class EntitySelectActivity extends AppCompatActivity implements EntitySelectMVC.View, AuthEntryAdapterCallback {

    private ListView listView;
    private ArrayList<AuthEntry> authList;
    private AuthEntryAdapter aAdapter;
    private EntitySelectMVC.Controller controller;
    private FloatingActionButton addEntry;
    private int previousVisibleItem;
    private TextView labelEmpty;
    private ImageView imgEmpty;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_select);

        /**
         * Construct top menu (ActionBar)
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
         *  Assign button handlers
         */
        addEntry = (FloatingActionButton) findViewById(R.id.addEntry);
        addEntry.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startActivity(new Intent(EntitySelectActivity.this, EditEntryActivity.class));
            }
        });

        listView = (ListView) findViewById(R.id.entry_selector);

        /*
         * Assign handler for Fab button behaviour on list scroll
         */
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

        /*
         * Setup values
         */
        authList = new ArrayList<>();
        labelEmpty = (TextView) findViewById(R.id.message_empty_text);
        imgEmpty = (ImageView) findViewById(R.id.message_empty_img);
        labelEmpty.setText(Html.fromHtml(getString(R.string.empty_entity_list_message)));

        /*
         * Create controller
         */
        controller = new EntitySelectController(this);
        controller.getData();
        //if(ProcessLifecycleOwner.get().getLifecycle().)
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new FocusObserver(this));
    }

    /**
     * Show or hide "No entries" message, used when entry list is empty. So user would know that there is no errors.
     *
     * @param state boolean value; True for visisble, False for invisible
     */
    private void setEmptyMessageNotificationVisibility(boolean state) {
        labelEmpty.setVisibility(state ? View.VISIBLE : View.GONE);
        imgEmpty.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    /**
     * Called when activity is shown again after being hidden by some editor activity
     */
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

    /**
     * Handle option menu actions
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_master_password) {
            startActivity(new Intent(EntitySelectActivity.this, EditMasterPasswordActivity.class));
            return true;

        } else if (id == R.id.manage_data) {
            startActivity(new Intent(EntitySelectActivity.this, ManageDataSelectActivity.class));
            return true;

        } else if (id == R.id.blacklist) {
            showNotification("Feature in development.");
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Update data for the list in activity
     */
    private void updateList() {
        aAdapter = new AuthEntryAdapter(this, authList);
        aAdapter.setCallback(this);
        listView.setAdapter(aAdapter);
        setEmptyMessageNotificationVisibility(aAdapter.isEmpty());
    }

    /**
     * Add new entity to current list in activity
     *
     * @param description - description of the entity
     * @param id          - hide it in element. Use it in intent when item selected
     */
    public void addEntity(String description, String id) {
        authList.add(new AuthEntry(R.drawable.ic_key, description, id));
        updateList();
    }

    /**
     * Delete all items from current list in activity
     */
    public void clearList() {
        authList.clear();
        updateList();
    }

    /**
     * Show notification in the bottom of activity as a "Snackbar"
     *
     * @param message - string that user should read
     */
    public void showNotification(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar mySnackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        mySnackbar.show();
        //TODO: Fab intersection avoidance
    }

    /**
     * Show confirmation for entry deletion
     *
     * @param name desription of the entity
     * @param id   id of the entity
     */
    public void deleteEntity(String name, String id) {
        AlertDialog confirm = AskOption(name, id);
        confirm.show();
    }

    /**
     * Construct confirmation dialog for entity deletion procedure
     *
     * @param entityDescription description of the entity
     * @param id                id of the entity
     * @return constructed window ui
     */
    private AlertDialog AskOption(String entityDescription, final String id) {
        return new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage(Html.fromHtml("Do you want to delete <b>" + entityDescription + "</b>?"))
                .setIcon(R.drawable.ic_key)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        controller.deleteAccount(id);

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
     * Make transition to EditEntity activity, that happens when user selects certain Auth entry menu.
     * When entry is selected its ID is taken to next activity by which editor will identify entity on server.
     *
     * @param id ID of selected entity
     */
    public void goToEditEntityActivity(String id) {
        Intent intent = new Intent(EntitySelectActivity.this, EditEntryActivity.class);
        intent.putExtra("ENTRY_ID", id);
        startActivity(intent);
    }

    /**
     * Make transition to AppletSelect activity, that happens when user selects certain Auth entry.
     * When entry is selected its ID is taken to next activity by which applet-selector will determine user intention.
     *
     * @param id ID of selected applet
     */
    public void goToAppletSelectActivity(String id) {
        Intent intent = new Intent(EntitySelectActivity.this, AppletSelectActivity.class);
        intent.putExtra("ENTRY_ID", id);
        startActivity(intent);
    }
}
