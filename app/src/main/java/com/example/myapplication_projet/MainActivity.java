package com.example.myapplication_projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://pokeapi.co/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // showlist();
        // makeApiCall();
    }
    public void page_1 (View view) {
        startActivity(new Intent(this, com.example.projet_progmobile.Page_2.class));
    }
}
