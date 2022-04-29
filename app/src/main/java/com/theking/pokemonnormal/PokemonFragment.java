package com.theking.pokemonnormal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
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

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

import static com.wdullaer.swipeactionadapter.SwipeDirection.DIRECTION_NORMAL_LEFT;
import static com.wdullaer.swipeactionadapter.SwipeDirection.DIRECTION_NORMAL_RIGHT;

public class PokemonFragment extends Fragment {
    private locations l;
    private Context context;
    private Button close ;
    private pokemonDetailsActivity p;
    public PokemonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = inflater.getContext();
        return inflater.inflate(R.layout.fragment_pokemon,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/").addConverterFactory(GsonConverterFactory.create()).build();
        pokeApiClient poke = retrofit.create(pokeApiClient.class);
        Map<String, String> map = new HashMap<>();
        int offset = 0;
        View v = getView();
        assert v != null;
        /*pokemonDatabase dbh = new pokemonDatabase(context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues values = new ContentValues();*/
        ListView listView = v.findViewById(R.id.pokemonTypes);
        close = v.findViewById(R.id.close);
        close.setVisibility(View.GONE);
        map.put("limit", String.valueOf(964));
        ProgressBar prog = v.findViewById(R.id.prog);
        map.put("offset", String.valueOf(offset));
        Call<locations> call = poke.getData("pokemon", map);
        v = getView();

        call.enqueue(new Callback<locations>() {

            @Override
            public void onResponse(Call<locations> call, Response<locations> response) {
                l = response.body();
                prog.setVisibility(View.GONE);
                MyCustomAdapter arrayAdapter = new MyCustomAdapter(context, l.results, "pokemon");
                AdapterView.OnItemClickListener item = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FrameLayout container = view.findViewById(R.id.container);
                        p = new pokemonDetailsActivity(l.results.get(position).getUrl(),l.results.get(position).toString());
                        ViewCompat.setTransitionName(view.findViewById(R.id.pokemonName),"Common");
                        p.setSharedElementEnterTransition(new Slide());
                        p.setEnterTransition(new Fade());
                        setExitTransition(new Fade());
                        p.setSharedElementReturnTransition(new Slide());
                        FragmentTransaction ft = getChildFragmentManager().beginTransaction().addSharedElement(view.findViewById(R.id.pokemonName),"Common");
                        ft.add(R.id.container, p);
                        ft.commit();
                        close.setVisibility(View.VISIBLE);
                        listView.setOnItemClickListener(null);
                    }
                };
                View.OnClickListener click = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction ft =getChildFragmentManager().beginTransaction();
                        ft.remove(p);
                        close.setVisibility(View.GONE);
                        listView.setOnItemClickListener(item);

                        ft.commit();
                    }
                };

                close.setOnClickListener(click);
                SwipeActionAdapter adapter = new SwipeActionAdapter(arrayAdapter);
                adapter.setListView(listView);
                adapter.setFarSwipeFraction((float)0);
                listView.setAdapter(adapter);
                /*adapter
                        .addBackground(DIRECTION_NORMAL_LEFT,R.layout.swipe_layout)

                        .addBackground(DIRECTION_NORMAL_RIGHT,R.layout.swipe_layout);
                adapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener(){
                    @Override
                    public boolean hasActions(int position, SwipeDirection direction){
                        if(direction.isLeft()) return true; // Change this to false to disable left swipes
                        if(direction.isRight()) return true;
                        return false;
                    }

                    @Override
                    public boolean shouldDismiss(int position, SwipeDirection direction){
                        // Only dismiss an item when swiping normal left
                        return false;
                    }

                    @Override
                    public void onSwipe(int[] position, SwipeDirection[] direction) {

                    }


                    @Override
                    public void onSwipeStarted(ListView listView, int position, SwipeDirection direction) {
                        // User is swiping

                    }

                    @Override
                    public void onSwipeEnded(ListView listView, int position, SwipeDirection direction) {
                        SQLiteOpenHelper helper = new pokemonDatabase(getContext());
                        try {
                            SQLiteDatabase db=helper.getWritableDatabase();
                            ContentValues contentValues=new ContentValues();
                            contentValues.put("ID",position);
                            contentValues.put("NAME",l.results.get(position).getName());
                            contentValues.put("URLl",l.results.get(position).getUrl());
                            db.insert("FAVOURITES", null, contentValues);
                            db.close();
                            Toast.makeText(context,l.results.get(position).getName(),Toast.LENGTH_SHORT).show();
                        }catch (Exception e) {
                            Toast.makeText(context,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                });*/

                listView.setOnItemClickListener(item);

            }

            @Override
            public void onFailure(Call<locations> call, Throwable t) {
                Toast toast = Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT);
                toast.show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }


}
