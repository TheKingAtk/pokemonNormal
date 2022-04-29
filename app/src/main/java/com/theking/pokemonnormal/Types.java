package com.theking.pokemonnormal;

import androidx.annotation.NonNull;

class Types {
    private pokeType type;

    @NonNull
    @Override
    public String toString() {

        return type.getName();
    }
}
