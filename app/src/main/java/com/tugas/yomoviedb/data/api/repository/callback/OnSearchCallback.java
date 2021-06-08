package com.tugas.yomoviedb.data.api.repository.callback;

import com.tugas.yomoviedb.data.models.TvShow;

import java.util.List;

public interface OnSearchCallback {
    void onSuccess(List<TvShow> tvShowsList, String msg, int page);

    void onFailure(String msg);
}
