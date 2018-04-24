package com.ba.yo.innovativepasswordmanager.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ba.yo.innovativepasswordmanager.AuthEntryAdapterCallback;
import com.ba.yo.innovativepasswordmanager.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class AuthEntryAdapter extends ArrayAdapter<AuthEntry> {
    private Context aContext;
    private List<AuthEntry> authList = new ArrayList<>();
    private AuthEntryAdapterCallback callback;

    public AuthEntryAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<AuthEntry> list) {
        super(context, 0 , list);
        aContext = context;
        authList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(aContext).inflate(R.layout.list_entry,parent,false);

        final AuthEntry currentEntry = authList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        image.setImageResource(currentEntry.getaImageDrawable());


        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentEntry.getaName());

        final Button prop_button = (Button) listItem.findViewById(R.id.prop_button);
        prop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), view, Gravity.CENTER_VERTICAL);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if(id==R.id.entity_edit){
                            if(callback!=null){
                                callback.goToEditEntityActivity(currentEntry.getaId());
                            }
                        }
                        if(id==R.id.entity_delete){
                            if(callback!=null){
                                callback.deleteEntity(currentEntry.getaName(), currentEntry.getaId());
                            }
                        }
                        return false;
                    }
                });

                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_entry, popup.getMenu());
                popup.show();
            }
        });


        return listItem;
    }

    public void setCallback(AuthEntryAdapterCallback callback){

        this.callback = callback;
    }

}
