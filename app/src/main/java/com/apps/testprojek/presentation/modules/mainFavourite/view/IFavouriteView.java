package com.apps.testprojek.presentation.modules.mainFavourite.view;

import com.apps.testprojek.presentation.modules.mainFavourite.model.Film;

import java.util.List;

public interface IFavouriteView {
    void getDataSuccess(List<Film> films);
    void getDataFailure(boolean status);
    void isLoading(boolean status);
    void isEmpty(boolean status);
}