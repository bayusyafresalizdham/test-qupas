package com.apps.testprojek.presentation.modules.introSplash.model;

public class Splash implements ISplash {

    boolean status;

    public Splash(boolean status) {
        this.status = status;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public int isLoged() {
        if(status) return 1;
        else return 0;
    }
}
