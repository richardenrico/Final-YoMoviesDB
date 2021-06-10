package com.tugas.yomoviedb.data.api.repository.callback;

import com.tugas.yomoviedb.data.models.Credits;

public interface OnCastCallback {
    void onSuccess(Credits credits, String message);

    void onFailure(String message);
}
