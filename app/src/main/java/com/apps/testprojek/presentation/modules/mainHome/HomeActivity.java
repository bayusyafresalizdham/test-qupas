package com.apps.testprojek.presentation.modules.mainHome;

import android.app.Dialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.apps.testprojek.R;
import com.apps.testprojek.presentation.modules.BaseActivity;
import com.apps.testprojek.presentation.modules.mainHome.adapter.FilmAdapter;
import com.apps.testprojek.presentation.modules.mainHome.model.Film;
import com.apps.testprojek.presentation.modules.mainHome.presenter.HomePresenter;
import com.apps.testprojek.presentation.modules.mainHome.presenter.IHomePresenter;
import com.apps.testprojek.presentation.modules.mainHome.view.IHomeView;
import com.apps.testprojek.utils.ConstantHelper;
import com.apps.testprojek.utils.ConstantUtils;
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
    private int page = 1;
    private List<String> listspiner = new ArrayList<>();
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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
         if (menuItem.getItemId() == R.id.action_search) {
            dialogSearch();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
    public void dialogSearch(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_filter);

        final EditText edtYear = dialog.findViewById(R.id.edtYear);
        final EditText edtKeyword = dialog.findViewById(R.id.edtKeyword);
        final Spinner spnType = dialog.findViewById(R.id.spnType);
        edtYear.setText(ConstantUtils.OMDB_YEAR);
        edtKeyword.setText(ConstantUtils.OMDB_KEYWORD);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if(ConstantUtils.OMDB_TYPE.equalsIgnoreCase("movie")){
            spnType.setSelection(1);
        }else if(ConstantUtils.OMDB_TYPE.equalsIgnoreCase("series")){
            spnType.setSelection(2);
        }else if(ConstantUtils.OMDB_TYPE.equalsIgnoreCase("episode")){
            spnType.setSelection(3);
        }
        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                ConstantUtils.OMDB_YEAR = edtYear.getText().toString();
                ConstantUtils.OMDB_KEYWORD = edtKeyword.getText().toString();
                if(spnType.getSelectedItemPosition() > 0){
                    ConstantUtils.OMDB_TYPE = spnType.getSelectedItem().toString().toLowerCase();
                }
                dialog.dismiss();
                refresh();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){

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
