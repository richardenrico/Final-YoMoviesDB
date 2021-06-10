package com.tugas.yomoviedb.data.models;

import com.tugas.yomoviedb.Const;
import com.tugas.yomoviedb.ImageSize;

public class Cast {
    private String name;
    private String profile_path;
    private String character;


    public String getCharacter() {
        return "(" + character + ")";
    }

    public String getName() {
        return name;
    }

    public String getProfilePath(ImageSize size) {
        return Const.IMG_URL + size.getValue() + profile_path;
    }
}
