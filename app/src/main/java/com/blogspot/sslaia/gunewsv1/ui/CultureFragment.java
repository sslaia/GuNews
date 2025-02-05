package com.blogspot.sslaia.gunewsv1.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.sslaia.gunewsv1.R;
import com.blogspot.sslaia.gunewsv1.adapter.NewsAdapter;
import com.blogspot.sslaia.gunewsv1.databinding.NewsActivityBinding;
import com.blogspot.sslaia.gunewsv1.helpers.ConnectionLiveData;
import com.blogspot.sslaia.gunewsv1.helpers.ConnectionModel;
import com.blogspot.sslaia.gunewsv1.helpers.Controller;
import com.blogspot.sslaia.gunewsv1.viewmodel.NewsViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class CultureFragment extends Fragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private NewsActivityBinding binding;
    private Activity activity;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CollapsingToolbarLayout collapsingToolbarLayout = activity.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.menu_culture));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recyclerview, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        PreferenceManager.setDefaultValues(context, R.xml.settings, false);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefs.registerOnSharedPreferenceChangeListener(this);

        // Check preferences whether to show images in the news list
        boolean showImages = mPrefs.getBoolean("showImages", true);

        String ORDER_BY = mPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));
        String SECTION = null;
        String QUERY = getString(R.string.query_culture);
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
                    navController.navigate(R.id.culture_to_connection);
                }
            }
        });
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
