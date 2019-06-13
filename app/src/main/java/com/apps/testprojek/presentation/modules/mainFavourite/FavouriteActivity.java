package com.apps.testprojek.presentation.modules.mainFavourite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apps.testprojek.R;
import com.apps.testprojek.presentation.modules.BaseActivity;
import com.apps.testprojek.presentation.modules.mainFavourite.adapter.FilmAdapter;
import com.apps.testprojek.presentation.modules.mainFavourite.model.Film;
import com.apps.testprojek.presentation.modules.mainFavourite.presenter.FavouritePresenter;
import com.apps.testprojek.presentation.modules.mainFavourite.presenter.IFavouritePresenter;
import com.apps.testprojek.presentation.modules.mainFavourite.view.IFavouriteView;
import com.apps.testprojek.utils.ConstantHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteActivity extends BaseActivity  implements IFavouriteView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<Film> films = new ArrayList<>();
    FilmAdapter adapter;
    IFavouritePresenter favouritePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        ButterKnife.bind(this);
        initBackButton();
        initRecycleView();
        favouritePresenter = new FavouritePresenter(this);
        favouritePresenter.getDataFromURL(this);
    }

    public void initRecycleView(){
        adapter = new FilmAdapter(getApplicationContext(), films, FavouriteActivity.this){
            @Override
            public void load() {
            }
        };
        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ConstantHelper.GridSpacingItemDecoration(2,
                new ConstantHelper().dpToPx(10,
                        getApplicationContext()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void getDataSuccess(List<Film> films) {

        for (int i=0;i<films.size();i++){
            this.films.add(films.get(i));
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getDataFailure(boolean status) {

    }

    @Override
    public void isLoading(boolean status) {

    }

    @Override
    public void isEmpty(boolean status) {

    }
}
