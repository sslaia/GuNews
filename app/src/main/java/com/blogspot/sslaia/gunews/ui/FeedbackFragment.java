package com.blogspot.sslaia.gunews.ui;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.sslaia.gunews.R;
import com.blogspot.sslaia.gunews.adapter.NewsAdapter;
import com.blogspot.sslaia.gunews.viewmodel.NewsViewModel;
import com.blogspot.sslaia.gunews.viewmodel.NewsViewModelFactory;
import com.blogspot.sslaia.gunews.webmodel.NewsItem;
import com.blogspot.sslaia.gunews.webmodel.NewsResult;

import java.util.ArrayList;
import java.util.List;

public class FeedbackFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}