package com.example.myapplication_projet.presentation.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication_projet.Constant;
import com.example.myapplication_projet.data.PokeApi;
import com.example.myapplication_projet.R;
import com.example.myapplication_projet.presentation.presentation.model.Pokemon;
import com.example.myapplication_projet.presentation.presentation.model.RestPokeApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    static final String BASE_URL = "https://pokeapi.co/";


    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_2);

        sharedPreferences= getSharedPreferences(Constant.Sauvergarde, Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Pokemon> pokemonList = getDataCache();

        if(pokemonList != null) {
            showlist(pokemonList);
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

    private void showlist(List<Pokemon> pokemonList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(pokemonList);
        recyclerView.setAdapter(mAdapter);
    }


    public void page_1 (View view) {
        startActivity(new Intent(this, Page_2.class));
    }

    private void makeApiCall(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokeApi pokeApi = retrofit.create(PokeApi.class);

        Call<RestPokeApi> call = pokeApi.getPokeApi();
         call.enqueue(new Callback<RestPokeApi>() {
             @Override
             public void onResponse(Call<RestPokeApi> call, Response<RestPokeApi> response) {
                 if(response.isSuccessful() && response.body() != null){
                     List<Pokemon> pokemonList = response.body().getResult();
                     memoireList(pokemonList);
                    showlist(pokemonList);
                 } else {
                     showError();
                 }
             }

             @Override
             public void onFailure(Call<RestPokeApi> call, Throwable t) {
                showError();
             }
         });
    }

    private void memoireList(List<Pokemon> pokemonList) {
        String jsonString = gson.toJson(pokemonList);
        sharedPreferences
                .edit()
                .putString(Constant.Key_Pokemon, jsonString)
                .apply();
        Toast.makeText(getApplicationContext(), "Saved !", Toast.LENGTH_SHORT).show();

    }

    private void showError(){
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_SHORT).show();

    }
}
