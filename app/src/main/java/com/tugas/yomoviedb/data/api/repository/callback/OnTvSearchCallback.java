package com.tugas.yomoviedb.data.api.repository.callback;

import com.tugas.yomoviedb.data.models.tvshow.TvShow;

import java.util.List;

public interface OnTvSearchCallback {
    void onSuccess(List<TvShow> tvShowsList, String msg, int page);

    void onFailure(String msg);
}
