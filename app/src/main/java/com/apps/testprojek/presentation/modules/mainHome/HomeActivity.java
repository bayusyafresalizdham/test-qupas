package com.apps.testprojek.presentation.modules.mainHome;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.testprojek.R;
import com.apps.testprojek.presentation.modules.BaseActivity;
import com.apps.testprojek.presentation.modules.mainHome.adapter.FilmAdapter;
import com.apps.testprojek.presentation.modules.mainHome.model.Film;
import com.apps.testprojek.presentation.modules.mainHome.presenter.HomePresenter;
import com.apps.testprojek.presentation.modules.mainHome.presenter.IHomePresenter;
import com.apps.testprojek.presentation.modules.mainHome.view.IHomeView;
import com.apps.testprojek.utils.ConstantHelper;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements IHomeView {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    List<Film> films = new ArrayList<>();
    FilmAdapter adapter;
    IHomePresenter homePresenter;
    @BindView(R.id.fabMenu)
    FloatingActionButton fabMenu;
    @BindView(R.id.tError)
    TextView tError;
    int page = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homePresenter = new HomePresenter(this);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        Glide.with(getApplicationContext()).load(R.drawable.cover_default).into((ImageView) findViewById(R.id.backdrop));
        initRecycleView();
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigator().openProfile(false,HomeActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        films.clear();
        adapter.notifyDataSetChanged();
        homePresenter.getDataFromURL(""+page);
    }

    public void initRecycleView(){
        adapter = new FilmAdapter(getApplicationContext(), films,HomeActivity.this){
            @Override
            public void load() {
                page+=1;
                homePresenter.getDataFromURL(""+page);
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

    private void initCollapsingToolbar() {
        collapsingToolbar.setTitle(" ");
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void getDataSuccess(List<Film> films) {
        tError.setVisibility(View.GONE);
        for (int i=0;i<films.size();i++){
            this.films.add(films.get(i));
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getDataFailure(boolean status) {
        tError.setText("Load Data Failed");
    }

    @Override
    public void isLoading(boolean status) {
        tError.setText("Waiting Process");

    }

    @Override
    public void isEmpty(boolean status) {
        tError.setText("Data Is Empty");

    }
}
