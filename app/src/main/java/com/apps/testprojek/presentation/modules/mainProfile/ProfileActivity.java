package com.apps.testprojek.presentation.modules.mainProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.apps.testprojek.R;
import com.apps.testprojek.presentation.modules.BaseActivity;
import com.apps.testprojek.presentation.modules.introLogin.LoginActivity;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        findViewById(R.id.btnContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigator().openAbout(false,ProfileActivity.this);
            }
        });
        findViewById(R.id.btnFavourite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigator().openFavourite(false,ProfileActivity.this);
            }
        });
        findViewById(R.id.btnAbout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigator().openContact(false,ProfileActivity.this);
            }
        });
        findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finishAffinity();
            }
        });
    }

}
