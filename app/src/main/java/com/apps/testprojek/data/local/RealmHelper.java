package com.apps.testprojek.data.local;

import android.content.Context;

import io.realm.Realm;

public class RealmHelper {
    public static Realm getrealm(Context mcontext){
        Realm.init(mcontext);
        Realm realm = Realm.getDefaultInstance();
        return  realm;
    }
}
