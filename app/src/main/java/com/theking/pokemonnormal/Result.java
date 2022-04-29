package com.theking.pokemonnormal;

import androidx.annotation.NonNull;

import java.util.Arrays;

class Result {

    private String name;

    private String url;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        char[] name1 = name.toCharArray();
        if (name1[0]>96) {
            name1[0]= (char) (name1[0]-32);
        }
        for(int i =1;i<name.length();i++) {
            if(name1[i]=='-') {
                name1[i]=' ';
            }
        }

        return String.valueOf(name1);
    }
}
