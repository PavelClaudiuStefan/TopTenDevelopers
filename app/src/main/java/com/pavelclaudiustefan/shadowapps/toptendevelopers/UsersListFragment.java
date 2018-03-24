package com.pavelclaudiustefan.shadowapps.toptendevelopers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pavelclaudiustefan.shadowapps.toptendevelopers.adapters.UsersListAdapter;
import com.pavelclaudiustefan.shadowapps.toptendevelopers.loaders.UsersListLoader;
import com.pavelclaudiustefan.shadowapps.toptendevelopers.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<User>>{

    private static int USERS_LOADER_ID = 1;

    private View rootView;
    private ListView listView;
    private TextView emptyStateTextView;

    private UsersListAdapter usersListAdapter;
    private ArrayList<User> users = new ArrayList<>();

    public UsersListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.users_list, container, false);

        listView = rootView.findViewById(R.id.list_view);

        //Only visible if no users are found
        emptyStateTextView = rootView.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyStateTextView);

        usersListAdapter = new UsersListAdapter(getActivity(), users);
        listView.setAdapter(usersListAdapter);

        // Fetch data
        getLoaderManager().initLoader(USERS_LOADER_ID, null, this);


        // Setup the item click listener
        // TODO - send user data more efficient
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UserDetailsActivity.class);
                User user = users.get(position);
                intent.putExtra("name", String.valueOf(user.getName()));
                intent.putExtra("profileImageUrl", String.valueOf(user.getProfileImageUrl()));
                intent.putExtra("location", String.valueOf(user.getLocation()));
                intent.putExtra("goldBadgesCount", String.valueOf(user.getGoldBadgesCount()));
                intent.putExtra("silverBadgesCount", String.valueOf(user.getSilverBadgesCount()));
                intent.putExtra("bronzeBadgesCount", String.valueOf(user.getBronzeBadgesCount()));
                startActivity(intent);
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootView;
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse("https://api.stackexchange.com/2.2/users");
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("pagesize", "10");
        uriBuilder.appendQueryParameter("order", "desc");
        uriBuilder.appendQueryParameter("sort", "reputation");
        uriBuilder.appendQueryParameter("site", "stackoverflow");

        return new UsersListLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No users found." It's not visible if any user is added to the adapter
        emptyStateTextView.setText(R.string.no_users);

        if (users != null && !users.isEmpty()) {
            usersListAdapter.addAll(users);
        }

        if (users == null) {
            displayError();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        usersListAdapter.clear();
    }

    private void refreshList() {
        // TODO
    }

    private void displayError() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo == null || !networkInfo.isConnected()) {
            emptyStateTextView.setText(R.string.no_internet_connection);
        } else {
            emptyStateTextView.setText(R.string.unknown_error);
        }
    }
}
