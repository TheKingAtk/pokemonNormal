package com.theking.pokemonnormal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class pokemonDetailsActivity extends Fragment {
    private pokemon p ;
    String url;
    Context context;
    String Name;
    public pokemonDetailsActivity(String url,String name) {
        this.url = url;
        Name=name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStat) {
        context=inflater.getContext();
        return inflater.inflate(R.layout.activity_pokemon_details2,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pokemon_details2);
        //Toolbar toolbar= findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //ActionBar actionBar=getSupportActionBar();
        View view = getView();
        TextView name = view.findViewById(R.id.pokemonName);
        TextView id = view.findViewById(R.id.pokemonId);
        TextView height = view.findViewById(R.id.height);
        TextView weight = view.findViewById(R.id.weight);
        ListView abilties = view.findViewById(R.id.abilitiesList);
        ListView types = view.findViewById(R.id.typesList);
        ImageView sprite = view.findViewById(R.id.sprite);
        ProgressBar pg = view.findViewById(R.id.prog);
        //Intent intent = getIntent();
        //String url = intent.getStringExtra("url");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/").addConverterFactory(GsonConverterFactory.create()).build();
        pokeApiClient poke = retrofit.create(pokeApiClient.class);
        name.setText(Name);
        setExitTransition(new Fade());
        Call<pokemon> call = poke.getPokemon(url);
        call.enqueue(new Callback<pokemon>() {
            @Override
            public void onResponse(Call<pokemon> call, Response<pokemon> response) {
                pg.setVisibility(View.GONE);
                p=response.body();

                id.setText(String.valueOf(p.getId()));
                height.setText(String.valueOf(p.getHeight()));
                weight.setText(String.valueOf(p.getWeight()));
                ArrayAdapter<pokemonAbilities> arrayAdapter = new ArrayAdapter<pokemonAbilities>(context,R.layout.item_layout,p.getAbilities());
                ArrayAdapter<Types> arrayAdapter1 = new ArrayAdapter<>(context,R.layout.item_layout,p.getTypes());
                int position= p.getId();
                abilties.setAdapter(arrayAdapter);
                types.setAdapter(arrayAdapter1);
                if(position<807) {
                    Picasso.get().load("https://pokeres.bastionbot.org/images/pokemon/"+(position)+".png").
                            error(R.drawable.no_image).into(sprite);
                }
                else{
                    Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+(position)+".png").
                            error(R.drawable.no_image).into(sprite);
                };
            }

            @Override
            public void onFailure(Call<pokemon> call, Throwable t) {
                Toast toast = Toast.makeText(context,t.getLocalizedMessage(),Toast.LENGTH_SHORT);
                toast.show();
                pg.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: ",t);
            }
        });
    }
}
