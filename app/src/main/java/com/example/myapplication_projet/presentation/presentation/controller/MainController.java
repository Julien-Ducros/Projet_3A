package com.example.myapplication_projet.presentation.presentation.controller;

import android.content.SharedPreferences;

import com.example.myapplication_projet.Constant;
import com.example.myapplication_projet.presentation.presentation.Singletons;
import com.example.myapplication_projet.presentation.presentation.model.Pokemon;
import com.example.myapplication_projet.presentation.presentation.model.RestPokeApi;
import com.example.myapplication_projet.presentation.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;



    public MainController() {
    }

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){

            List<Pokemon> pokemonList = getDataCache();

            if(pokemonList != null) {
                view.showlist(pokemonList);
            } else {
                makeApiCall();
            }
        }

    private List<Pokemon> getDataCache() {
        String jsonPokemon= sharedPreferences.getString(Constant.Key_Pokemon, null);

        if (jsonPokemon == null){
            return null;
        } else {
            Type ListType = new TypeToken<List<Pokemon>>() {
            }.getType();
            return gson.fromJson(jsonPokemon, ListType);
        }


    }

   /* public void page_1 (View view) {
        startActivity(new Intent(this, Page_2.class));
    }
*/
    private void makeApiCall(){

        Call<RestPokeApi> call = Singletons.getpokeApi().getPokeApi();
        call.enqueue(new Callback<RestPokeApi>() {
            @Override
            public void onResponse(Call<RestPokeApi> call, Response<RestPokeApi> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Pokemon> pokemonList = response.body().getResult();
                    memoireList(pokemonList);
                    view.showlist(pokemonList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokeApi> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void memoireList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);
        sharedPreferences
                .edit()
                .putString(Constant.Key_Pokemon, jsonString)
                .apply();
       // Toast.makeText(getApplicationContext(), "Saved !", Toast.LENGTH_SHORT).show();

    }

    

    }

