package com.theking.pokemonnormal;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class Pokemon_1 {
    public Pokemon_2 getPokemon_2() {
        return pokemon_2;
    }

    @SerializedName("pokemon")
    @Expose
    private Pokemon_2 pokemon_2 ;

    @NonNull
    @Override
    public String toString() {
       char[] nameTemp = pokemon_2.name.toCharArray();
        for (int i=0;i<pokemon_2.name.length();i++) {
            if(nameTemp[i]=='-') {
                nameTemp[i]=' ';
            }
        }
        if (nameTemp[0]>96) {
            nameTemp[0]= (char) (nameTemp[0]-32);
        }
        return String.valueOf(nameTemp);
    }
}
