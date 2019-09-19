package com.blogspot.sslaia.gunews.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blogspot.sslaia.gunews.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class AboutFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CollapsingToolbarLayout collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Attribution");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}