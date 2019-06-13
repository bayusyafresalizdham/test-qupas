package com.apps.testprojek.presentation.modules;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.apps.testprojek.R;
import com.apps.testprojek.data.local.RealmHelper;
import com.apps.testprojek.data.local.database.Loged;
import com.apps.testprojek.presentation.Navigator;

import io.realm.RealmResults;

public class BaseActivity extends AppCompatActivity {
    Navigator navigator = null;
    RealmResults<Loged> loged;
    public Navigator getNavigator(){
        if(navigator == null) navigator = new Navigator();
        return navigator;
    }
    public void initBackButton(){
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean isLoged(){
        loged= RealmHelper.getrealm(getApplicationContext()).where(Loged.class).findAll();
        if(loged.size() > 0){
            return true;
        }else{
           return false;
        }
    }


    public void logOut(){
        loged= RealmHelper.getrealm(getApplicationContext()).where(Loged.class).findAll();
        RealmHelper.getrealm(getApplicationContext()).beginTransaction();
        loged.deleteAllFromRealm();
        RealmHelper.getrealm(getApplicationContext()).commitTransaction();

    }


}
