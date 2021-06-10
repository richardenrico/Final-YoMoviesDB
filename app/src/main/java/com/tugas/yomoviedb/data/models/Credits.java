package com.tugas.yomoviedb.data.models;

import java.util.List;

public class Credits {
    private int id;
    private List<Cast> cast;

    public int getId() {
        return id;
    }

    public List<Cast> getCast() {
        return cast;
    }
}
