package com.pavelclaudiustefan.shadowapps.toptendevelopers;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends Fragment {


    public UserDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_user_details, container, false);

        Intent intent = getActivity().getIntent();

        String name = intent.getStringExtra("name");
        String profileImageUrl = intent.getStringExtra("profileImageUrl");
        String location = "Location: " + intent.getStringExtra("location");
        String goldBadgesCount = "Gold badges: " + intent.getStringExtra("goldBadgesCount");
        String silverBadgesCount = "Silver badges: " + intent.getStringExtra("silverBadgesCount");
        String bronzeBadgesCount = "Bronze badges: " + intent.getStringExtra("bronzeBadgesCount");

        TextView nameTextView = rootView.findViewById(R.id.name);
        nameTextView.setText(name);

        TextView locationTextView = rootView.findViewById(R.id.location);
        locationTextView.setText(location);

        TextView goldBadgesCountTextView = rootView.findViewById(R.id.goldBadges);
        goldBadgesCountTextView.setText(goldBadgesCount);

        TextView silverBadgesCountTextView = rootView.findViewById(R.id.silverBadges);
        silverBadgesCountTextView.setText(silverBadgesCount);

        TextView bronzeBadgesCountTextview = rootView.findViewById(R.id.bronzeBadges);
        bronzeBadgesCountTextview.setText(bronzeBadgesCount);

        ImageView profileImageView = rootView.findViewById(R.id.profileImage);
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        picasso.load(profileImageUrl)
                .into(profileImageView);

        return rootView;
    }

}
