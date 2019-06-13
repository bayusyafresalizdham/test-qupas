package com.apps.testprojek.presentation.modules.mainHome.presenter;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.apps.testprojek.data.remote.RemoteService;
import com.apps.testprojek.data.remote.RetrofitHelper;
import com.apps.testprojek.data.remote.response.FilmResponse;
import com.apps.testprojek.presentation.modules.introSplash.model.Splash;
import com.apps.testprojek.presentation.modules.introSplash.presenter.ISplashPresenter;
import com.apps.testprojek.presentation.modules.introSplash.view.ISplashView;
import com.apps.testprojek.presentation.modules.mainHome.model.Film;
import com.apps.testprojek.presentation.modules.mainHome.view.IHomeView;
import com.apps.testprojek.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomePresenter implements IHomePresenter {

    IHomeView homeView;

    public HomePresenter(IHomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void getDataFromURL(String page) {
        homeView.isLoading(true);
        RemoteService service = RetrofitHelper.getClient().create(RemoteService.class);
        Call<FilmResponse> call = service.getFilmOMDB(ConstantUtils.OMDB_TOKEN,
                ""+ConstantUtils.OMDB_KEYWORD,
                ""+ConstantUtils.OMDB_TYPE,
                ""+ConstantUtils.OMDB_YEAR,
                ""+page);
        call.enqueue(new Callback<FilmResponse>() {
            @Override
            public void onResponse(Call<FilmResponse> call, retrofit2.Response<FilmResponse> response) {
                Log.d("cekurl",""+call.request());
                homeView.isLoading(false);
                if(response.body().getSearch() != null){
                    if(response.body().getSearch().size() == 0){
                        homeView.isEmpty(true);
                    }else{
                        List<Film> films = new ArrayList<>();
                        for (int i=0;i<response.body().getSearch().size();i++){
                            films.add(new Film(response.body().getSearch().get(i).getTitle(),
                                    response.body().getSearch().get(i).getYear(),
                                    response.body().getSearch().get(i).getImdbID(),
                                    response.body().getSearch().get(i).getType(),
                                    response.body().getSearch().get(i).getPoster()));
                        }
                        homeView.getDataSuccess(films);
                    }
                }else{
                    homeView.getDataFailure(true);
                }
            }
            @Override
            public void onFailure(Call<FilmResponse> call, Throwable t) {
                homeView.getDataFailure(true);
            }
        });
    }

}
