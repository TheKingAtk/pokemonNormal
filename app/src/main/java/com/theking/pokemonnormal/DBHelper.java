package com.theking.pokemonnormal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="PokemonFavourites";
    private static final int DB_VERSION=14;
    private static final String TABLE_NAME="Favourites";
    private static final String COLUMN_0="id";
    private static final String COLUMN_1="NAME";
    private static final String COLUMN_2="URL";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+COLUMN_0+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_1+" TEXT, " + COLUMN_2+"TEXT)");
        db.execSQL("CREATE TABLE Favourites ( ID integer primary key,NAME TEXT, URL TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long addData(int id,String name,String url) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_0,id);
        contentValues.put(COLUMN_1, name);
        contentValues.put(COLUMN_2,url);
        //db.execSQL("insert into "+TABLE_NAME+"("+COLUMN_0+","+COLUMN_1+")"+" values(1,id);");
        return db.insert("Favourites",null,contentValues);
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ TABLE_NAME+ " where id="+id+"", null );
        return res;
    }
    public Cursor getFullTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ TABLE_NAME, null );
        return res;
    }
    public boolean removeData(int id) {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,COLUMN_0+"="+id,null)>0;
    }
}
