package com.blogspot.sslaia.gunewsv1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

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
                R.id.nav_headlines, R.id.nav_culture, R.id.nav_family,
                R.id.nav_hrights, R.id.nav_politics, R.id.nav_sport,
                R.id.nav_technology, R.id.nav_connection, R.id.nav_search,
                R.id.action_gunews, R.id.action_about, R.id.action_settings)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent menuShare = new Intent(Intent.ACTION_SEND);
            menuShare.setType("text/plain");
            menuShare.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            menuShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
            startActivity(menuShare);
        } else if (id == R.id.action_feedback) {
            Intent menuFeedback = new Intent(Intent.ACTION_SENDTO);
            menuFeedback.setData(Uri.parse(getString(R.string.feedback_mailto)));
            menuFeedback.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
            startActivity(menuFeedback);
        } else if (id == R.id.action_about) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_about);
        } else if (id == R.id.action_gunews) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_gunews);
        } else {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_settings);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
