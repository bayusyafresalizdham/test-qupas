package com.apps.testprojek.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.apps.testprojek.presentation.modules.introLogin.LoginActivity;
import com.apps.testprojek.presentation.modules.mainAbout.AboutActivity;
import com.apps.testprojek.presentation.modules.mainContact.ContactActivity;
import com.apps.testprojek.presentation.modules.mainDetail.DetailActivity;
import com.apps.testprojek.presentation.modules.mainFavourite.FavouriteActivity;
import com.apps.testprojek.presentation.modules.mainHome.HomeActivity;
import com.apps.testprojek.presentation.modules.mainProfile.ProfileActivity;

public class Navigator  {

    public void openLogin(boolean finish, Activity a){
        Intent i = new Intent(a, LoginActivity.class);
        a.startActivity(i);
        if(finish) a.finish();
    }

    public void openHome(boolean finish, Activity a){
        Intent i = new Intent(a, HomeActivity.class);
        a.startActivity(i);
        if(finish) a.finish();
    }

    public void openProfile(boolean finish, Activity a){
        Intent i = new Intent(a, ProfileActivity.class);
        a.startActivity(i);
        if(finish) a.finish();
    }


    public void openContact(boolean finish, Activity a){
        Intent i = new Intent(a, ContactActivity.class);
        a.startActivity(i);
        if(finish) a.finish();
    }

    public void openAbout(boolean finish, Activity a){
        Intent i = new Intent(a, AboutActivity.class);
        a.startActivity(i);
        if(finish) a.finish();
    }


    public void openDetail(boolean finish, Activity a){
        Intent i = new Intent(a, DetailActivity.class);
        a.startActivity(i);
        if(finish) a.finish();
    }


    public void openFavourite(boolean finish, Activity a){
        Intent i = new Intent(a, FavouriteActivity.class);
        a.startActivity(i);
        if(finish) a.finish();
    }

}
