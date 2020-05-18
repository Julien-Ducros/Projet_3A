package com.example.myapplication_projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_2);


        makeApiCall();
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

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

    private void showError(){
        Toast.makeText(getApplicationContext(), "API error", Toast.LENGTH_SHORT).show();

    }
}
