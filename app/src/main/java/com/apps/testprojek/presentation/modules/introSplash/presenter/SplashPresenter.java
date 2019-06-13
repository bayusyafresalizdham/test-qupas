package com.apps.testprojek.presentation.modules.introSplash.presenter;

import android.os.Handler;

import com.apps.testprojek.presentation.modules.introLogin.model.Login;
import com.apps.testprojek.presentation.modules.introLogin.presenter.ILoginPresenter;
import com.apps.testprojek.presentation.modules.introLogin.view.ILoginView;
import com.apps.testprojek.presentation.modules.introSplash.model.Splash;
import com.apps.testprojek.presentation.modules.introSplash.view.ISplashView;
import com.apps.testprojek.utils.ConstantUtils;

public class SplashPresenter implements ISplashPresenter {

    ISplashView splashView;

    public SplashPresenter(ISplashView loginView) {
        this.splashView = loginView;
    }

    @Override
    public void onMovePageTo(boolean status) {
        Splash user = new Splash(status);
        final int isLoged = user.isLoged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLoged == 1){
                    splashView.showHomePage();
                }else{
                    splashView.showLoginPage();
                }
            }
        },ConstantUtils.DELAY_SPLASH);

    }

}
