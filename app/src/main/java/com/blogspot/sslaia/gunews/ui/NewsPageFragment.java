package com.blogspot.sslaia.gunews.ui;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.blogspot.sslaia.gunews.R;
import com.blogspot.sslaia.gunews.viewmodel.SinglePageViewModel;
import com.blogspot.sslaia.gunews.viewmodel.SinglePageViewModelFactory;
import com.blogspot.sslaia.gunews.webmodel.PageContent;
import com.blogspot.sslaia.gunews.webmodel.PageItem;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NewsPageFragment extends Fragment {

    private TextView infoView, titleView, bylineView, bodyView, noteView;
    private ImageView imageView;
    private Application application;
    private String shortUrl;
    private String pageUrl;
    private String requestUrl;
    private String thumbnailUrl;
    private String headline;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_page, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        infoView = getView().findViewById(R.id.details_news_info);
        titleView = getView().findViewById(R.id.details_news_title);
        bylineView = getView().findViewById(R.id.details_news_byline);
        bodyView = getView().findViewById(R.id.details_news_body);
        noteView = getView().findViewById(R.id.details_news_notes);
        imageView = getView().findViewById(R.id.details_news_image);

        final List<PageContent> pageList = new ArrayList<>();

        NewsPageFragmentArgs args = NewsPageFragmentArgs.fromBundle(getArguments());
        pageUrl = args.getPageUrl();
        requestUrl = pageUrl + "?show-fields=headline,byline,body,shortUrl,thumbnail&api-key=" + getString(R.string.theguardian_api_key);
//        thumbnailUrl = args.getThumbnailUrl();

        SinglePageViewModelFactory pageFactory = new SinglePageViewModelFactory(application, requestUrl);
        SinglePageViewModel singlePageViewModel = ViewModelProviders.of(this, pageFactory).get(SinglePageViewModel.class);
        singlePageViewModel.init();
        singlePageViewModel.getSinglePageRepository().observe(NewsPageFragment.this, new Observer<PageItem>() {
            @Override
            public void onChanged(PageItem pageItem) {
                pageList.clear();
                PageContent pageContents = pageItem.getPageResponse().getContent();
                pageList.add(pageContents);

                headline = pageList.get(0).getFields().getHeadline();
                shortUrl = pageList.get(0).getFields().getShortUrl();
                titleView.setText(headline);

                String date = pageList.get(0).getWebPublicationDate().substring(0, 10);
                String section = pageList.get(0).getSectionName();
                String info = date + " " + section;
                infoView.setText(info);

                String body = pageList.get(0).getFields().getBody();
                bodyView.setText(Html.fromHtml(body));

                noteView.setText(getString(R.string.details_news_notes));

                try {
                    String byline = pageList.get(0).getFields().getByline();
                    if (byline == null || byline.isEmpty()) {
                        bylineView.setVisibility(View.GONE);
                    } else {
                        bylineView.setText(byline);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    thumbnailUrl = pageList.get(0).getFields().getThumbnail();
                    if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
                        imageView.setVisibility(View.GONE);
                    } else {
                        Glide.with(getContext())
                                .load(thumbnailUrl)
                                .centerCrop()
                                .into(imageView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Set up the FAB
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_share_white);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareArticle = new Intent(Intent.ACTION_SEND);
                shareArticle.setType("text/plain");
                shareArticle.putExtra(Intent.EXTRA_SUBJECT, headline);
                shareArticle.putExtra(Intent.EXTRA_TEXT, getString(R.string.open_in_browser) + shortUrl + "\n\n" + getString(R.string.app_name) + getString(R.string.app_tag_line));
                if (shareArticle.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(shareArticle);
                }
            }
        });

    }
}