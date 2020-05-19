package com.example.myapplication_projet.data;

import com.example.myapplication_projet.presentation.presentation.model.RestPokeApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi {
    @GET("/api/v2/pokemon/")
    Call<RestPokeApi> getPokeApi();
}
