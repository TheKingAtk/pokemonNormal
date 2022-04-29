package com.theking.pokemonnormal;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class tabManager extends FragmentPagerAdapter {
    Context context;
    public tabManager(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0 :
                return new PokemonFragment();
            case 2:
                return new ItemFragment();
            case 4:
                return new LocationsFragment();
            case 1 :
                return new TypeFragment();
            case 3 :
                return new RegionsFragment();
        }
        return new PokemonFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0 :
              return  context.getResources().getText(R.string.p);
            case 2:
                return context.getResources().getText(R.string.i);
            case 4:
                return context.getResources().getText(R.string.l);
            case 1:
                return context.getResources().getText(R.string.types);
            case 3 :
                return context.getResources().getText(R.string.pokemonRegions);
        }
        return null;
    }
}
