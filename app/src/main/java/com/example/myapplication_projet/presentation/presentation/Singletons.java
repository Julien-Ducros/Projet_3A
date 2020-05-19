package com.example.myapplication_projet.presentation.presentation;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication_projet.Constant;
import com.example.myapplication_projet.data.PokeApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {
    private static Gson gsonIntance;
    private static PokeApi pokeApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonIntance== null){
            gsonIntance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
    return gsonIntance;
    }
    public static PokeApi getpokeApi(){
        if(pokeApiInstance==null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            PokeApi pokeApi = retrofit.create(PokeApi.class);
        }
        return pokeApiInstance;
    }

    public static SharedPreferences getSharedPreferencesInstance(Context context){
        if(sharedPreferencesInstance== null){
            sharedPreferencesInstance = context.getSharedPreferences(Constant.Sauvergarde, Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
