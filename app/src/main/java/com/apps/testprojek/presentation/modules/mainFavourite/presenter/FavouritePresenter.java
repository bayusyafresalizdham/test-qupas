package com.apps.testprojek.presentation.modules.mainFavourite.presenter;

import android.app.Activity;

import com.apps.testprojek.data.local.RealmHelper;
import com.apps.testprojek.data.local.database.Favorit;
import com.apps.testprojek.presentation.modules.mainFavourite.model.Film;
import com.apps.testprojek.presentation.modules.mainFavourite.view.IFavouriteView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class FavouritePresenter implements IFavouritePresenter {

    IFavouriteView favouriteView;

    public FavouritePresenter(IFavouriteView favouriteView) {
        this.favouriteView = favouriteView;
    }

    @Override
    public void getDataFromURL(Activity activity) {

        RealmResults<Favorit> favorits = RealmHelper.getrealm(activity.getApplicationContext())
                .where(Favorit.class).
                findAll();
        List<Film> films = new ArrayList<>();
        for (int i=0;i<favorits.size();i++){
            films.add(new Film(favorits.get(i).getTitle(),
                    favorits.get(i).getReleaseDate(),
                    "","",
                    favorits.get(i).getPoster()));

        }

        favouriteView.getDataSuccess(films);
    }

}
