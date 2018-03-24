package com.pavelclaudiustefan.shadowapps.toptendevelopers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pavelclaudiustefan.shadowapps.toptendevelopers.R;
import com.pavelclaudiustefan.shadowapps.toptendevelopers.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Claudiu on 23-Mar-18.
 */

public class UsersListAdapter extends ArrayAdapter<User> {

    public UsersListAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.users_list_item, parent, false);
        }

        User currentUser = getItem(position);

        TextView titleView = listItemView.findViewById(R.id.name);
        assert currentUser != null;
        titleView.setText(currentUser.getName());


        ImageView imageView = listItemView.findViewById(R.id.thumbnail);

        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        picasso.load(currentUser.getProfileImageUrl())
                .into(imageView);

        return listItemView;
    }

    @Override
    public void addAll(User... items) {
        super.addAll(items);
    }

}
