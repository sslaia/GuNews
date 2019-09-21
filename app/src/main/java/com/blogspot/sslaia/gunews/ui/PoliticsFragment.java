package com.blogspot.sslaia.gunews.ui;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.blogspot.sslaia.gunews.model.web.NewsItem;
import com.blogspot.sslaia.gunews.model.web.NewsResult;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class PoliticsFragment extends Fragment
        implements NewsAdapter.OnItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private ArrayList<NewsResult> newsArrayList = new ArrayList<>();
    private Application application;
    private NewsAdapter newsAdapter;
    private RecyclerView recyclerView;
    private NewsListViewModel newsListViewModel;
    private View rootView;
    private SharedPreferences mPrefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CollapsingToolbarLayout collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Politics");
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // the if statement here solves the problem of empty view when going back (OnBackPressed)
        // from other fragment screen
        // Credit: Cesar Garcia (https://stackoverflow.com/questions/45431311/recyclerview-is-empty-when-back-pressed-from-fragment/45431805#45431805)
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PreferenceManager.setDefaultValues(getContext(), R.xml.settings_preferences, false);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        mPrefs.registerOnSharedPreferenceChangeListener(this);
//        String orderBy = mPrefs.getString(
//                getString(R.string.settings_order_by_key),
//                getString(R.string.settings_order_by_default));
        String pageSize = mPrefs.getString(
                getString(R.string.settings_page_size_key),
                getString(R.string.settings_page_size_default));

        String query = null;
        String orderBy = "newest";
        String section = "politics";
        String showFields = "byline,shortUrl,thumbnail";
        int page = 1;
        String apiKey = getString(R.string.theguardian_api_key);

        NewsListViewModelFactory factory = new NewsListViewModelFactory(application, query, section, orderBy, showFields, page, pageSize, apiKey);

        newsListViewModel = ViewModelProviders.of(this, factory).get(NewsListViewModel.class);
        newsListViewModel.init();
        newsListViewModel.getNewsListRepository().observe(getViewLifecycleOwner(), new Observer<NewsItem>() {
            @Override
            public void onChanged(NewsItem newsItems) {
                List<NewsResult> newsArticles = newsItems.getResponse().getResults();
                newsArrayList.addAll(newsArticles);
                newsAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = getView().findViewById(R.id.recycler_view);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(getActivity(), newsArrayList);
            newsAdapter.setOnItemClickListener(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(newsAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {

        NewsResult clickedItem = newsArrayList.get(position);
        String apiUrl = clickedItem.getApiUrl();
        String thumbnailUrl = clickedItem.getFields().getThumbnail();

        if (apiUrl == null || apiUrl.isEmpty()) {
            Toast.makeText(getContext(), "Error in getting the web page address", Toast.LENGTH_SHORT).show();
        } else {
            PoliticsFragmentDirections.PoliticsToNewsPage action =
                    PoliticsFragmentDirections.politicsToNewsPage();
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
            public boolean onQueryTextSubmit(String searchWord) {
                navigateToSearchResults(searchWord);
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

    public void navigateToSearchResults(String searchWord) {
        PoliticsFragmentDirections.PoliticsToHeadlines action =
                PoliticsFragmentDirections.politicsToHeadlines();
        action.setSearchWord(searchWord);
        Navigation.findNavController(rootView).navigate(action);
    }
}