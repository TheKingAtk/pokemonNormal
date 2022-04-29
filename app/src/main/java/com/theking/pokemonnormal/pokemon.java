package com.theking.pokemonnormal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class pokemon {
    private int id;
    private String name ;
    private int height;
    private int weight;
    private pokemonSprites sprites;

    public List<Pokemon_1> getPokemon_1() {
        return pokemon_1;
    }

    @SerializedName("pokemon")
    @Expose
    private List<Pokemon_1> pokemon_1;

    public List<Types> getTypes() {
        return types;
    }

    private List<Types> types;

    public List<pokemonAbilities> getAbilities() {
        return abilities;
    }

    private List<pokemonAbilities> abilities;

    public int getId() {
        return id;
    }

    public String getName() {
        char[] nameTemp =  name.toCharArray();
        for (int i=0;i<name.length();i++) {
            if(nameTemp[i]=='-') {
                nameTemp[i]=' ';
            }
        }
        name=String.valueOf(nameTemp).toUpperCase();
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public pokemonSprites getSprites() {
        return sprites;
    }
}
