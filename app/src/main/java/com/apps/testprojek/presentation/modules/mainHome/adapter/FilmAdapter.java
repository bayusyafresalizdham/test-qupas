package com.apps.testprojek.presentation.modules.mainHome.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.apps.testprojek.R;
import com.apps.testprojek.data.local.RealmHelper;
import com.apps.testprojek.data.local.database.Favorit;
import com.apps.testprojek.data.local.database.Loged;
import com.apps.testprojek.presentation.Navigator;
import com.apps.testprojek.presentation.modules.mainHome.model.Film;
import com.apps.testprojek.utils.ConstantUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.realm.RealmResults;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public abstract class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.MyViewHolder> {

    private Context mContext;
    private List<Film> albumList;
    private Activity activity;
    private RealmResults<Favorit> favorits;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail;
        public CardView layout_click;
        public ImageView btnFavorite;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.title);
            count =  view.findViewById(R.id.count);
            btnFavorite = view.findViewById(R.id.btnFavorite);
            thumbnail =  view.findViewById(R.id.thumbnail);
            layout_click = view.findViewById(R.id.layout_click);
        }
    }


    public FilmAdapter(Context mContext, List<Film> albumList, Activity activity) {
        this.mContext = mContext;
        this.albumList = albumList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Film album = albumList.get(position);

        favorits = RealmHelper.getrealm(mContext).where(Favorit.class).
                equalTo("title",""+album.getTitle()).
                findAll();
        if(favorits.size() > 0){
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp);
        }else{
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorits = RealmHelper.getrealm(mContext).where(Favorit.class).
                        equalTo("title",""+album.getTitle()).
                        findAll();
                if(favorits.size() == 0){
                    RealmHelper.getrealm(mContext).beginTransaction();
                    Favorit db = RealmHelper.getrealm(mContext).createObject(Favorit.class);
                    db.setPoster(""+album.getPoster());
                    db.setTitle(""+album.getTitle());
                    db.setReleaseDate(""+album.getYear());
                    RealmHelper.getrealm(mContext).commitTransaction();
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite_red_24dp);
                }else{
                    RealmHelper.getrealm(mContext).beginTransaction();
                    favorits.deleteAllFromRealm();
                    RealmHelper.getrealm(mContext).commitTransaction();
                    holder.btnFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });
        holder.title.setText(album.getTitle());
        holder.count.setText(album.getYear());
        Glide.with(mContext).load(album.getPoster())
                .placeholder(R.drawable.dummy)
                .error(R.drawable.dummy)
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantUtils.PARSE_TITLE_MOVIE = album.getTitle();
                new Navigator().openDetail(false,activity);
            }
        });
        holder.layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantUtils.PARSE_TITLE_MOVIE = album.getTitle();
                new Navigator().openDetail(false,activity);
            }
        });


        if(position >= getItemCount() -1){
            load();
        }

    }
    public abstract void load();
    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
