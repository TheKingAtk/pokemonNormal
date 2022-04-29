package com.theking.pokemonnormal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class pokemonWithTypeActivity extends Fragment {

    private pokemon p;
    private Context context;
    private String url;
    private String name;
    private pokemonDetailsActivity po;

    public pokemonWithTypeActivity(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=inflater.getContext();
        return inflater.inflate(R.layout.activity_pokemon_with_type,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view =getView();


        TextView textView= view.findViewById(R.id.type);
        textView.setText(name);
        ListView listView = view.findViewById(R.id.pokemonWithType);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/").addConverterFactory(GsonConverterFactory.create()).build();
        Button close = view.findViewById(R.id.close);
        close.setVisibility(View.GONE);
        pokeApiClient poke = retrofit.create(pokeApiClient.class);

        ProgressBar pg =view.findViewById(R.id.prog);
        Call<pokemon> call = poke.getPokemon(url);
        call.enqueue(new Callback<pokemon>() {
            @Override
            public void onResponse(Call<pokemon> call, Response<pokemon> response) {
                pg.setVisibility(View.GONE);

                MyCustomAdapter1 arrayAdapter = new MyCustomAdapter1(context,response.body().getPokemon_1());
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(),"bla",Toast.LENGTH_SHORT).show();
                    }
                });
                //MyCustomAdapter arrayAdapter=new MyCustomAdapter()
                p=response.body();
                AdapterView.OnItemClickListener item = (parent, view1, position, id) -> {
                    po = new pokemonDetailsActivity(response.body().getPokemon_1().get(position).getPokemon_2().getUrl(),response.body().getPokemon_1().get(position).toString());
                    FragmentTransaction ft=getChildFragmentManager().beginTransaction();
                    po.setEnterTransition(new Fade());
                    setExitTransition(new Fade());
                    ft.add(R.id.container,po);
                    ft.commit();
                    close.setVisibility(View.VISIBLE);
                    listView.setOnItemClickListener(null);
                };
                //listView.setOnItemClickListener(item);
                View.OnClickListener click = v -> {
                    FragmentTransaction ft=getChildFragmentManager().beginTransaction();
                    ft.remove(po);
                    ft.commit();
                    close.setVisibility(View.GONE);
                    listView.setOnItemClickListener(item);

                };
                close.setOnClickListener(click);

                listView.setAdapter(arrayAdapter);


            }

            @Override
            public void onFailure(Call<pokemon> call, Throwable t) {
                Toast toast = Toast.makeText(context,t.getLocalizedMessage(),Toast.LENGTH_SHORT);
                toast.show();
                pg.setVisibility(View.GONE);
            }
        });

    }
}
