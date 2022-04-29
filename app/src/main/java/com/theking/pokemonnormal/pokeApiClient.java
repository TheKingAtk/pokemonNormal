package com.theking.pokemonnormal;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface pokeApiClient {
    @GET("/api/v2/{kind}/")
    Call<locations> getData(@Path("kind") String kind, @QueryMap Map<String,String> range);
    @GET("")
    Call<pokemon> getPokemon(@Url String url);
}
