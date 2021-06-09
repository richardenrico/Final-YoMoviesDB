package com.tugas.yomoviedb.data.api.repository.callback;

import com.tugas.yomoviedb.data.models.tvshow.TvShow;

public interface OnTvDetailCallback {
    void onSuccess(TvShow tvShow, String message);

    void onFailure(String message);
}
