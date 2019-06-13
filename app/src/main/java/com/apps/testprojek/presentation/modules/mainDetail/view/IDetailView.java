package com.apps.testprojek.presentation.modules.mainDetail.view;

import com.apps.testprojek.data.remote.response.FilmDetailResponse;
import com.apps.testprojek.presentation.modules.mainHome.model.Film;

import java.util.List;

public interface IDetailView {
    void getDataSuccess(FilmDetailResponse films);
    void getDataFailure(boolean status);
    void isLoading(boolean status);
    void isEmpty(boolean status);
}