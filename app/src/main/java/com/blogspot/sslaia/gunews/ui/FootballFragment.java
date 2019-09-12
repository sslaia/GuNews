package com.blogspot.sslaia.gunews.ui;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.sslaia.gunews.MainActivity;
import com.blogspot.sslaia.gunews.R;
import com.blogspot.sslaia.gunews.adapter.NewsAdapter;
import com.blogspot.sslaia.gunews.viewmodel.NewsViewModel;
import com.blogspot.sslaia.gunews.viewmodel.NewsViewModelFactory;
import com.blogspot.sslaia.gunews.webmodel.NewsItem;
import com.blogspot.sslaia.gunews.webmodel.NewsResult;

import java.util.ArrayList;
import java.util.List;

public class FootballFragment extends Fragment
        implements NewsAdapter.OnItemClickListener {

    // Possible query key words:
    // show-fields (separated by commas: headline,byline,shortUrl,thumbnail)
    // section (separated by commas: society,technology,science,football)
    // page-size (int: the amount of news items per page)
    // page (the page no to be display)
    // from-date (dd/mm/yyyy: from a given date)
    // apiKey (api key)
    String requestUrl = "https://content.guardianapis.com/search?show-fields=headline%2Cbyline%2CshortUrl%2Cthumbnail&section=football&page-size=15&api-key=";
    private ArrayList<NewsResult> newsArrayList = new ArrayList<>();
    private Application application;
    private NewsAdapter newsAdapter;
    private RecyclerView recyclerView;
    private NewsViewModel newsListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String newsUrl = requestUrl + getString(R.string.theguardian_api_key);

        NewsViewModelFactory factory = new NewsViewModelFactory(application, newsUrl);

        newsListViewModel = ViewModelProviders.of(this, factory).get(NewsViewModel.class);
        newsListViewModel.init();
        newsListViewModel.getNewsRepository().observe(getViewLifecycleOwner(), new Observer<NewsItem>() {
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
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {

        NewsResult clickedItem = newsArrayList.get(position);
        String apiUrl = clickedItem.getApiUrl();
        String thumbnailUrl = clickedItem.getFields().getThumbnail();

        if (apiUrl != null) {
            String pageUrl = apiUrl + "?show-fields=byline%2Cbody&api-key=" + getString(R.string.theguardian_api_key);

            FootballFragmentDirections.ActionNavFootballToNewsPageFragment action =
                    FootballFragmentDirections.actionNavFootballToNewsPageFragment();
            action.setPageUrl(pageUrl);
            action.setThumbnailUrl(thumbnailUrl);
            Navigation.findNavController(getView()).navigate(action);
        }
    }
}