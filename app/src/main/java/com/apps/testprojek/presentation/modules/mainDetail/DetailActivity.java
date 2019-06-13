package com.apps.testprojek.presentation.modules.mainDetail;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.testprojek.R;
import com.apps.testprojek.data.local.RealmHelper;
import com.apps.testprojek.data.local.database.Favorit;
import com.apps.testprojek.data.remote.response.FilmDetailResponse;
import com.apps.testprojek.presentation.modules.BaseActivity;
import com.apps.testprojek.presentation.modules.mainDetail.presenter.DetailPresenter;
import com.apps.testprojek.presentation.modules.mainDetail.presenter.IDetailPresenter;
import com.apps.testprojek.presentation.modules.mainDetail.view.IDetailView;
import com.apps.testprojek.presentation.modules.mainHome.presenter.HomePresenter;
import com.apps.testprojek.presentation.modules.mainHome.presenter.IHomePresenter;
import com.apps.testprojek.utils.ConstantUtils;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.realm.RealmResults;

public class DetailActivity extends BaseActivity implements IDetailView {

    IDetailPresenter detailPresenter;
    @BindView(R.id.tTitle)
    TextView tTitle;
    @BindView(R.id.tYear)
    TextView tYear;
    @BindView(R.id.tPlot)
    TextView tPlot;
    @BindView(R.id.tActor)
    TextView tActor;
    @BindView(R.id.tStar)
    TextView tStar;
    @BindView(R.id.imgPoster)
    ImageView imgPoster;
    @BindView(R.id.btnFavorite)
    ImageView btnFavorite;
    private RealmResults<Favorit> favorits;
    private String title = "";
    private String poster = "";
    private String year = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        favorits = RealmHelper.getrealm(getApplicationContext()).where(Favorit.class).
                equalTo("title",""+ ConstantUtils.PARSE_TITLE_MOVIE).
                findAll();
        if(favorits.size() > 0){
            btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp);
        }else{
            btnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
        detailPresenter = new DetailPresenter(this);
        detailPresenter.getDataFromURL();
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorits = RealmHelper.getrealm(getApplicationContext()).where(Favorit.class).
                        equalTo("title",""+ ConstantUtils.PARSE_TITLE_MOVIE).
                        findAll();
                if(favorits.size() == 0){
                    RealmHelper.getrealm(getApplicationContext()).beginTransaction();
                    Favorit db = RealmHelper.getrealm(getApplicationContext()).createObject(Favorit.class);
                    db.setPoster(""+poster);
                    db.setTitle(""+title);
                    db.setReleaseDate(""+year);
                    RealmHelper.getrealm(getApplicationContext()).commitTransaction();
                    btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp);
                }else{
                    RealmHelper.getrealm(getApplicationContext()).beginTransaction();
                    favorits.deleteAllFromRealm();
                    RealmHelper.getrealm(getApplicationContext()).commitTransaction();
                    btnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                }

            }
        });
    }

    @Override
    public void getDataSuccess(FilmDetailResponse films) {
        title = films.getTitle();
        year = films.getYear();
        poster = films.getPoster();
        tTitle.setText(""+films.getTitle());
        tYear.setText(""+films.getReleased());
        tPlot.setText(""+films.getPlot());
        tStar.setText(""+films.getImdbRating());
        tActor.setText(""+films.getActors());
        Glide.with(getApplicationContext()).load(films.getPoster())
                .placeholder(R.drawable.dummy)
                .error(R.drawable.dummy)
                .into(imgPoster);
    }

    @Override
    public void getDataFailure(boolean status) {
        Toasty.error(this,"Load Failed",Toasty.LENGTH_LONG).show();
    }

    @Override
    public void isLoading(boolean status) {
        if(status){
            tTitle.setText("-");
            tYear.setText("-");
            tPlot.setText("-");
            tStar.setText("-");
            tActor.setText("-");
        }

    }

    @Override
    public void isEmpty(boolean status) {

    }
}
