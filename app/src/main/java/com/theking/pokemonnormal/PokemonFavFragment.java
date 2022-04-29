package com.theking.pokemonnormal;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PokemonFavFragment extends Fragment {
    private locations l;
    private Context context;
    private Button close ;
    private pokemonDetailsActivity p;
    public PokemonFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = inflater.getContext();
        return inflater.inflate(R.layout.fragment_pokemon_fav, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        DBHelper pdh = new DBHelper(context);
        SQLiteDatabase db = pdh.getReadableDatabase();
        try {
           Cursor cursor = pdh.getFullTable();
            Result[] favourites=new Result[10000];
            int top=-1;
            //Cursor cursor =db.rawQuery("SELECT * FROM FAVOURITES",null);

            Result temp=new Result();

            if(cursor.moveToFirst()){
                top++;
                favourites[top]=new Result();
                favourites[top].setName(cursor.getString(cursor.getColumnIndex("NAME")));
                favourites[top].setUrl(cursor.getString(cursor.getColumnIndex("URL")));
                favourites[top].setId(cursor.getInt(cursor.getColumnIndex("ID")));


            }
            while(cursor.moveToNext()) {
                top++;
                favourites[top]=new Result();
                favourites[top].setName(cursor.getString(cursor.getColumnIndex("NAME")));

                favourites[top].setUrl(cursor.getString(cursor.getColumnIndex("URL")));
                favourites[top].setId(cursor.getInt(cursor.getColumnIndex("ID")));
            }
            cursor.close();
            db.close();

            View v = getView();
            assert v != null;
            ListView listView = v.findViewById(R.id.pokemonTypes);
            ArrayList<Result> fav=new ArrayList<>();
            for(int i=0;i<=top;i++) {
                fav.add(favourites[i]);
            }
            MyCustomAdapter arrayAdapter = new MyCustomAdapter(context, fav, "pokemon",true);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(context,"blah",Toast.LENGTH_SHORT).show();
                }
            });
        }catch (SQLException e) {
            Toast toast = Toast.makeText(context,e.getLocalizedMessage(),Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
