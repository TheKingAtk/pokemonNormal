package com.theking.pokemonnormal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.nav_open,R.string.nav_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView  = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
        PokemonFragment pokemonFragment = new PokemonFragment();
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container,pokemonFragment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.p);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        ActionBar actionBar = getSupportActionBar();

        switch (item.getItemId()) {
            case R.id.pokemon :
                fragment = new PokemonFragment();
                actionBar.setTitle(R.string.p);
                break;
            case R.id.types :
                fragment = new TypeFragment();
                actionBar.setTitle(R.string.types);
                break;
            case R.id.item:
                fragment = new ItemFragment();
                actionBar.setTitle(R.string.i);
                break;
            case R.id.regions:
                fragment=new RegionsFragment();
                actionBar.setTitle(R.string.pokemonRegions);
                break;
            case R.id.location:
                fragment =new LocationsFragment();
                actionBar.setTitle(R.string.l);
                break;
            case R.id.fav :
                fragment =new PokemonFavFragment();
                actionBar.setTitle(R.string.favourites);
                break;
        }
        FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment);
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        ft.commit();
        return true;
    }
}
