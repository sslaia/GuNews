package com.blogspot.sslaia.gunews.ui;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.sslaia.gunews.R;
import com.blogspot.sslaia.gunews.adapter.NewsAdapter;
import com.blogspot.sslaia.gunews.viewmodel.NewsListViewModel;
import com.blogspot.sslaia.gunews.viewmodel.NewsListViewModelFactory;
import com.blogspot.sslaia.gunews.webmodel.NewsItem;
import com.blogspot.sslaia.gunews.webmodel.NewsResult;

import java.util.ArrayList;
import java.util.List;

public class HeadlinesFragment extends Fragment
        implements NewsAdapter.OnItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private ArrayList<NewsResult> newsList = new ArrayList<>();
    private Application application;
    private NewsAdapter newsAdapter;
    private RecyclerView recyclerView;
    private NewsListViewModel newsListViewModel;
    private View rootView;
    private SharedPreferences mPrefs;
    private String query = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null || !getArguments().isEmpty()) {
            HeadlinesFragmentArgs args = HeadlinesFragmentArgs.fromBundle(getArguments());
            query = args.getSearchWord();
        } else {
            query = null;
        }

        PreferenceManager.setDefaultValues(getContext(), R.xml.settings_preferences, false);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        mPrefs.registerOnSharedPreferenceChangeListener(this);
        String pageSize = mPrefs.getString(
                getString(R.string.settings_page_size_key),
                getString(R.string.settings_page_size_default));

        String section = null;
        String showFields = "byline,shortUrl,thumbnail";
        String apiKey = getString(R.string.theguardian_api_key);

        NewsListViewModelFactory factory = new NewsListViewModelFactory(application, query, section, showFields, pageSize, apiKey);

        newsListViewModel = ViewModelProviders.of(this, factory).get(NewsListViewModel.class);
        // This line makes sure the viewmodel is only run once
        newsListViewModel.init();
        // instead of this (Fragment) getViewLifeCycleOwner is called to prevent multiple instances
        // of observer are run
        newsListViewModel.getNewsListRepository().observe(getViewLifecycleOwner(), new Observer<NewsItem>() {
            @Override
            public void onChanged(NewsItem newsItems) {
                List<NewsResult> articleList = newsItems.getResponse().getResults();
                newsList.addAll(articleList);
                newsAdapter.notifyDataSetChanged();
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(getActivity(), newsList);
            newsAdapter.setOnItemClickListener(this);

            recyclerView = getView().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(newsAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {

        NewsResult clickedItem = newsList.get(position);
        String apiUrl = clickedItem.getApiUrl();
        String thumbnailUrl = clickedItem.getFields().getThumbnail();

        if (apiUrl == null || apiUrl.isEmpty()) {
            Toast.makeText(getContext(), "Error in getting the web page address", Toast.LENGTH_SHORT).show();
        } else {
            HeadlinesFragmentDirections.HeadlinesToNewsPage action =
                    HeadlinesFragmentDirections.headlinesToNewsPage();
            action.setPageUrl(apiUrl);
            action.setThumbnailUrl(thumbnailUrl);
            Navigation.findNavController(getView()).navigate(action);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String searchQuery) {
                navigateToSearchResults(searchQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // No need to do anything here
        // the view model will take care of the rest
    }

    public void navigateToSearchResults(String searchQuery) {
        HeadlinesFragmentDirections.HeadlinesToSearch action =
                HeadlinesFragmentDirections.headlinesToSearch();
        action.setSearchQuery(searchQuery);
        Navigation.findNavController(getView()).navigate(action);
    }
}