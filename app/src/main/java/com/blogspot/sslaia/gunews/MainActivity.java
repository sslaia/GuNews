package com.blogspot.sslaia.gunews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.blogspot.sslaia.gunews.ui.AboutFragment;
import com.blogspot.sslaia.gunews.ui.HeadlinesFragmentDirections;
import com.blogspot.sslaia.gunews.ui.SearchResultsFragment;
import com.blogspot.sslaia.gunews.ui.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_headlines, R.id.nav_culture, R.id.nav_education,
                R.id.nav_family, R.id.nav_hrights, R.id.nav_opinion, R.id.nav_politics,
                R.id.nav_sport, R.id.nav_technology, R.id.nav_wellbeing,
                R.id.nav_about,R.id.nav_search_results)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;
            case R.id.action_share:
                Intent menuShare = new Intent(Intent.ACTION_SEND);
                menuShare.setType("text/plain");
                menuShare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                menuShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
                if (menuShare.resolveActivity(getPackageManager()) != null) {
                    startActivity(menuShare);
                }
                return true;
            case R.id.action_feedback:
                Intent menuFeedback = new Intent(Intent.ACTION_SENDTO);
                menuFeedback.setData(Uri.parse(getString(R.string.feedback_mailto)));
                menuFeedback.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
                if (menuFeedback.resolveActivity(getPackageManager()) != null) {
                    startActivity(menuFeedback);
                }
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

}
