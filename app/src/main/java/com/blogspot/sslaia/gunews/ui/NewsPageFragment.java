package com.blogspot.sslaia.gunews.ui;

import android.app.Application;
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
import com.blogspot.sslaia.gunews.viewmodel.PageViewModel;
import com.blogspot.sslaia.gunews.viewmodel.PageViewModelFactory;
import com.blogspot.sslaia.gunews.webmodel.PageContent;
import com.blogspot.sslaia.gunews.webmodel.PageItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsPageFragment extends Fragment {

    private PageContent pageContents;
    private TextView infoView, titleView, bylineView, bodyView, noteView;
    private ImageView imageView;
    private Application application;
    private String pageUrl;
    private String thumbnailUrl = null;


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
        thumbnailUrl = args.getThumbnailUrl();

        PageViewModelFactory pageFactory = new PageViewModelFactory(application, pageUrl);
        PageViewModel pageViewModel = ViewModelProviders.of(this, pageFactory).get(PageViewModel.class);
        pageViewModel.init();
        pageViewModel.getNewsRepository().observe(NewsPageFragment.this, new Observer<PageItem>() {
            @Override
            public void onChanged(PageItem pageItem) {
                PageContent pageContents = pageItem.getPageResponse().getContent();
                pageList.add(pageContents);

                String date = pageList.get(0).getWebPublicationDate().substring(0, 10);
                String section = pageList.get(0).getSectionName();
                String info = date + " " + section;

                infoView.setText(info);
                titleView.setText(pageList.get(0).getWebTitle());
                String byline = pageList.get(0).getFields().getByline();
                if (byline == null) {
                    bylineView.setVisibility(View.GONE);
                } else {
                    String bylineInfo = "By " + byline;
                    bylineView.setText(bylineInfo);
                }

                String body = pageList.get(0).getFields().getBody();
                bodyView.setText(Html.fromHtml(body));

                noteView.setText(getString(R.string.details_news_notes));

                if (thumbnailUrl == null) {
                    imageView.setVisibility(View.GONE);
                } else {
                    Glide.with(NewsPageFragment.this)
                            .load(thumbnailUrl).centerCrop().into(imageView);
                }
            }
        });
    }
}