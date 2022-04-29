package com.theking.pokemonnormal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegionsFragment extends ListFragment {
    locations l;
    public RegionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/").addConverterFactory(GsonConverterFactory.create()).build();
        pokeApiClient poke = retrofit.create(pokeApiClient.class);
        Map<String,String> map=new HashMap<>();
        int offset=0;
        map.put("limit",String.valueOf(20));
        map.put("offset",String.valueOf(offset));
        Call<locations> call = poke.getData("region",map);
        call.enqueue(new Callback<locations>() {
            @Override
            public void onResponse(Call<locations> call, Response<locations> response) {
                l = response.body();
                MyCustomAdapter arrayAdapter = new MyCustomAdapter(inflater.getContext(),l.results,"regions");
                setListAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<locations> call, Throwable t) {
                Toast toast = Toast.makeText(inflater.getContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT);
                toast.show();
                Log.e(TAG, "onFailure: ",t);
            }
        });
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}
