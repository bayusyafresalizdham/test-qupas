package com.apps.testprojek.presentation.modules.introSplash;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.apps.testprojek.R;
import com.apps.testprojek.presentation.modules.BaseActivity;
import com.apps.testprojek.presentation.modules.introLogin.presenter.ILoginPresenter;
import com.apps.testprojek.presentation.modules.introLogin.presenter.LoginPresenter;
import com.apps.testprojek.presentation.modules.introSplash.presenter.ISplashPresenter;
import com.apps.testprojek.presentation.modules.introSplash.presenter.SplashPresenter;
import com.apps.testprojek.presentation.modules.introSplash.view.ISplashView;

public class SplashActivity extends BaseActivity implements ISplashView {

    ISplashPresenter splashPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashPresenter = new SplashPresenter(this);
        splashPresenter.onMovePageTo(isLoged());
    }

    @Override
    public void showHomePage() {
        getNavigator().openHome(true,SplashActivity.this);

    }

    @Override
    public void showLoginPage() {
        getNavigator().openLogin(true, SplashActivity.this);
    }
}
