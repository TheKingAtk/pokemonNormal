package com.theking.pokemonnormal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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
public class TypeFragment extends Fragment {
    private locations l;
    private Context context;
    private pokemonWithTypeActivity pokemon;
    public TypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       context = inflater.getContext();
        return inflater.inflate(R.layout.fragment_type,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/").addConverterFactory(GsonConverterFactory.create()).build();
        pokeApiClient poke = retrofit.create(pokeApiClient.class);
        Map<String,String> map=new HashMap<>();
        int offset=0;
        View view = getView();
        Button button = view.findViewById(R.id.close1);
        button.setVisibility(View.GONE);
        ListView listView = view.findViewById(R.id.pokemonOfType);
        ProgressBar prog = view.findViewById(R.id.prog);
        map.put("limit",String.valueOf(20));
        map.put("offset",String.valueOf(offset));
        Call<locations> call = poke.getData("type",map);
        call.enqueue(new Callback<locations>() {
            @Override
            public void onResponse(Call<locations> call, Response<locations> response) {
                l = response.body();
                prog.setVisibility(View.GONE);


                MyCustomAdapter arrayAdapter = new MyCustomAdapter(context,l.results,"types");

                AdapterView.OnItemClickListener item = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        button.setVisibility(View.VISIBLE);
                        FragmentTransaction ft=getChildFragmentManager().beginTransaction();
                        pokemon = new pokemonWithTypeActivity(l.results.get(position).getUrl(),l.results.get(position).getName());
                        pokemon.setEnterTransition(new Fade());
                        setExitTransition(new Fade());
                        listView.setOnItemClickListener(null);
                        ft.add(R.id.container,pokemon);
                        ft.commit();

                    }
                };

                View.OnClickListener click =new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction ft =getChildFragmentManager().beginTransaction();
                        ft.remove(pokemon);
                        button.setVisibility(View.GONE);
                        listView.setOnItemClickListener(item);
                        ft.commit();

                    }
                };
                button.setOnClickListener(click);
                listView.setOnItemClickListener(item);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<locations> call, Throwable t) {
                Toast toast = Toast.makeText(context,t.getLocalizedMessage(),Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
