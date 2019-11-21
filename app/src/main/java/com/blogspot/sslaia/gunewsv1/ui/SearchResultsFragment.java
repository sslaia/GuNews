package com.blogspot.sslaia.gunewsv1.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.blogspot.sslaia.gunewsv1.R;
import com.blogspot.sslaia.gunewsv1.adapter.NewsAdapter;
import com.blogspot.sslaia.gunewsv1.databinding.NewsActivityBinding;
import com.blogspot.sslaia.gunewsv1.helpers.ConnectionLiveData;
import com.blogspot.sslaia.gunewsv1.helpers.ConnectionModel;
import com.blogspot.sslaia.gunewsv1.helpers.Controller;
import com.blogspot.sslaia.gunewsv1.viewmodel.NewsViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class SearchResultsFragment extends Fragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private NewsActivityBinding binding;
    private Activity activity;
    private Context context;

    public SearchResultsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CollapsingToolbarLayout collapsingToolbarLayout = activity.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.menu_search));
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // By putting the databinding inflater here
        // the issues with toolbar/drawer disappearance is solved
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recyclerview, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        String QUERY;

        SearchResultsFragmentArgs args = SearchResultsFragmentArgs.fromBundle(getArguments());
        if (args.getSearchQuery().contains("noQuery")) {
            QUERY = null;
        } else {
            QUERY = args.getSearchQuery();
        }

        PreferenceManager.setDefaultValues(context, R.xml.settings_preferences, false);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.registerOnSharedPreferenceChangeListener(this);

        // Check preferences whether to show images in the news list
        boolean showImages = mPrefs.getBoolean("showImages", true);

        String ORDER_BY = mPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));
        String SECTION = null;
        String SHOW_FIELDS = getString(R.string.show_fields);
        String API_KEY = getString(R.string.theguardian_api_key);

        NewsViewModel model = new NewsViewModel(Controller.create(activity), QUERY, SECTION, ORDER_BY, SHOW_FIELDS, API_KEY);
        NewsAdapter adapter = new NewsAdapter(context, showImages);
        model.getNewsLiveData().observe(getViewLifecycleOwner(), adapter::submitList);
        binding.recyclerView.setAdapter(adapter);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(activity);
        connectionLiveData.observe(getViewLifecycleOwner(), new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connection) {
                if (!connection.getIsConnected()) {
                    NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
                    navController.navigate(R.id.search_to_connection);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                navigateToSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void navigateToSearch(String query) {
        SearchResultsFragmentDirections.SearchToSearch2 action =
                SearchResultsFragmentDirections.searchToSearch2();
        action.setSearchWord(query);
        Navigation.findNavController(activity, R.id.nav_host_fragment).navigate(action);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }
}
