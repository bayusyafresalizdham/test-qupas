package com.apps.testprojek.data.local.database;

import io.realm.RealmObject;

public class Loged extends RealmObject {
    boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
