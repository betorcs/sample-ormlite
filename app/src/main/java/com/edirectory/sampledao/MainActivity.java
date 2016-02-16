package com.edirectory.sampledao;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.edirectory.sampledao.ui.PeopleListFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PeopleListFragment fragment = (PeopleListFragment) getSupportFragmentManager().findFragmentByTag("people");
        if (fragment == null) {
            fragment = new PeopleListFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, "people")
                .commit();
    }


}
