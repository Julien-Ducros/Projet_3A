package com.example.myapplication_projet;

import java.util.List;

public class RestPokeApi {
    private Integer count;
    private String next;
    private List<Pokemon> result;

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public List<Pokemon> getResult() {
        return result;
    }
}
