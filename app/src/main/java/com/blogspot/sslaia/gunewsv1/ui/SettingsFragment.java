package com.blogspot.sslaia.gunewsv1.ui;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.blogspot.sslaia.gunewsv1.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        CollapsingToolbarLayout collapsingToolbarLayout = getActivity().findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
    }
}
