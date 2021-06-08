package com.tugas.yomoviedb.data.api.repository.callback;

import com.tugas.yomoviedb.data.models.TvShow;

import java.util.List;

public interface OnTvShowCallback {
    void onSuccess(int page, List<TvShow> tvShowList);

    void onFailure(String message);
}
