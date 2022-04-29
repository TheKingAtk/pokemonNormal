package com.theking.pokemonnormal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyCustomAdapter1 extends ArrayAdapter<Pokemon_1> {
    private Context context;

    private List<Pokemon_1> pokemons ;
    public MyCustomAdapter1(Context context , List<Pokemon_1> pokemons) {
        super(context,R.layout.row_item,pokemons);
        this.context=context;
        this.pokemons=pokemons;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
        }
        TextView name = convertView.findViewById(R.id.pokemonName);
        name.setText(pokemons.get(position).toString());
        TextView id = convertView.findViewById(R.id.pokemonId);
        String url =  pokemons.get(position).getPokemon_2().getUrl();
        String sub= url.substring(34);
        char[] temp =sub.replaceAll("/", " ").toCharArray();
        for (int i =0 ;i < temp.length;i++) {
            if(temp[i]==' ') {
                sub=sub.substring(0,i);
                break;
            }
        }
        int idValue=Integer.parseInt(sub);
        DBHelper dbHelper=new DBHelper(context);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Switch favi=convertView.findViewById(R.id.fav);
        Cursor cursor=dbHelper.getData(idValue);

        if(cursor.moveToFirst()) {favi.setChecked(true);}
        else favi.setChecked(false);
        cursor.close();



            favi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(favi.isChecked()) {
                        long success = dbHelper.addData(idValue,pokemons.get(position).getPokemon_2().getName(),pokemons.get(position).getPokemon_2().getUrl());
                        if(success!=-1) Toast.makeText(context,"Added "+pokemons.get(position).getPokemon_2().getName()+" to favourites!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(dbHelper.removeData(idValue)){
                            Toast.makeText(context,"Removed "+pokemons.get(position).getPokemon_2().getName()+" from favourites!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        db.close();
        id.setText(String.format("ID: %s", String.valueOf(idValue)));
        ImageView sprite =convertView.findViewById(R.id.sprite);
        if(idValue<807) {
            Picasso.get().load("https://pokeres.bastionbot.org/images/pokemon/"+(idValue)+".png").
                    error(R.drawable.no_image).into(sprite);
        }
        else{
            Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+(idValue)+".png").
                    error(R.drawable.no_image).into(sprite);
        }

        return convertView;
    }


}
