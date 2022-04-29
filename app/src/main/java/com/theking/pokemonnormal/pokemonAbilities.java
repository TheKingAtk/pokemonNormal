package com.theking.pokemonnormal;

import androidx.annotation.NonNull;

class pokemonAbilities {
    private boolean is_hidden;
    private pokemonAbility ability;

    public boolean isIs_hidden() {
        return is_hidden;
    }

    public pokemonAbility getAbility() {
        return ability;
    }

    @NonNull
    @Override
    public String toString() {

        char[] nameTemp =  ability.getName().toCharArray();
        for (int i=0;i<ability.getName().length();i++) {
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
