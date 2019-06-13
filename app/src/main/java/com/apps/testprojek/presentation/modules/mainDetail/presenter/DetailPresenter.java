package com.apps.testprojek.presentation.modules.mainDetail.presenter;

import com.apps.testprojek.data.remote.RemoteService;
import com.apps.testprojek.data.remote.RetrofitHelper;
import com.apps.testprojek.data.remote.response.FilmDetailResponse;
import com.apps.testprojek.data.remote.response.FilmResponse;
import com.apps.testprojek.presentation.modules.mainDetail.view.IDetailView;
import com.apps.testprojek.presentation.modules.mainHome.model.Film;
import com.apps.testprojek.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailPresenter implements IDetailPresenter {

    IDetailView detailView;

    public DetailPresenter(IDetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void getDataFromURL() {
        detailView.isLoading(true);
        RemoteService service = RetrofitHelper.getClient().create(RemoteService.class);
        Call<FilmDetailResponse> call = service.getFilmDetailOMDB("20bd772c", ""+ ConstantUtils.PARSE_TITLE_MOVIE,"full");
        call.enqueue(new Callback<FilmDetailResponse>() {
            @Override
            public void onResponse(Call<FilmDetailResponse> call, retrofit2.Response<FilmDetailResponse> response) {
                detailView.isLoading(false);
                detailView.getDataSuccess(response.body());
            }
            @Override
            public void onFailure(Call<FilmDetailResponse> call, Throwable t) {
                detailView.getDataFailure(true);
            }
        });
    }

}
