package com.tugas.yomoviedb;

public enum ImageSize {
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W200("w200"),
    W342("w342"),
    W500("w500"),
    W780("w789"),
    ORI("original");

    private String value;

    ImageSize(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
