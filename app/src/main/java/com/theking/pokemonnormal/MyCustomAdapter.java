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

public class MyCustomAdapter extends ArrayAdapter<Result> {

    private Context context;
    private String kind;
    private List<Result> pokemons ;
    private boolean fav;
    final private String TABLE_NAME="FAVOURITES";
    MyCustomAdapter(Context context, List<Result> pokemons, String kind) {
        super(context,R.layout.row_item,pokemons);
        this.context=context;
        this.pokemons=pokemons;
        this.kind=kind;

    }
    MyCustomAdapter(Context context, List<Result> pokemons, String kind, boolean fav) {
        super(context,R.layout.row_item,pokemons);
        this.context=context;
        this.pokemons=pokemons;
        this.kind=kind;
        this.fav=fav;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
        }
        View finalConvertView = convertView;
        //convertView.setOnTouchListener(new MySwipeListener(context) {
           // @Override
          //  public void OnSwipe() {
               // Toast toast = Toast.makeText(context,"Added",Toast.LENGTH_SHORT);
         //       toast.show();
           // }
        //});
        DBHelper dbHelper=new DBHelper(context);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Switch favi=convertView.findViewById(R.id.fav);
        Cursor cursor=dbHelper.getData(fav?pokemons.get(position).getId():(position+1));

        if(cursor.moveToFirst()) {favi.setChecked(true);}
        else favi.setChecked(false);
        cursor.close();


        if(kind.equalsIgnoreCase("pokemon")) {
            favi.setOnClickListener(v -> {
                if(favi.isChecked()) {
                    long success = dbHelper.addData((fav?pokemons.get(position).getId():(position+1)),pokemons.get(position).getName(),pokemons.get(position).getUrl());
                    if(success!=-1) Toast.makeText(context,"Added "+pokemons.get(position).getName()+" to favourites!",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(dbHelper.removeData(fav?pokemons.get(position).getId():(position+1))){
                        Toast.makeText(context,"Removed "+pokemons.get(position).getName()+" from favourites!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else favi.setVisibility(View.GONE);
        db.close();
        TextView name = convertView.findViewById(R.id.pokemonName);
        name.setText(pokemons.get(position).toString());
        TextView id = convertView.findViewById(R.id.pokemonId);
        id.setText(String.format("ID: %s", (fav?pokemons.get(position).getId():(position+1))));
        ImageView sprite =convertView.findViewById(R.id.sprite);
        if(position<807&&kind.equalsIgnoreCase("pokemon")) {
            Picasso.get().load("https://pokeres.bastionbot.org/images/pokemon/"+((fav?pokemons.get(position).getId():(position+1)))+".png").
                    error(R.drawable.no_image).into(sprite);
        }
        else if (kind.equalsIgnoreCase("pokemon")){
            Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+((fav?pokemons.get(position).getId():(position+1))+9193)+".png").
                    error(R.drawable.no_image).into(sprite);
        }
        else if (kind.equalsIgnoreCase("item")) {
            Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/"+
                    (pokemons.get(position).getName())+".png").
                    error(R.drawable.no_image).into(sprite);
        }

        return convertView;
    }

}
