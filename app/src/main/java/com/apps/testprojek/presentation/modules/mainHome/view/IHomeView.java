package com.apps.testprojek.presentation.modules.mainHome.view;

import com.apps.testprojek.presentation.modules.mainHome.model.Film;

import java.util.List;

public interface IHomeView {
    void getDataSuccess(List<Film> films);
    void getDataFailure(boolean status);
    void isLoading(boolean status);
    void isEmpty(boolean status);
}