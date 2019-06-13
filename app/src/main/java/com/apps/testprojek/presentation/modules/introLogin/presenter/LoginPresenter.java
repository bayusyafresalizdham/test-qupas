package com.apps.testprojek.presentation.modules.introLogin.presenter;

import android.content.Context;

import com.apps.testprojek.R;
import com.apps.testprojek.presentation.modules.introLogin.view.ILoginView;
import com.apps.testprojek.presentation.modules.introLogin.model.Login;

public class LoginPresenter implements ILoginPresenter {

    ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onLogin(Context c,String username, String password) {
        Login user = new Login(username, password);
        int loginCode = user.isValidData();

        if(loginCode == 0) {
            loginView.onLoginError(c.getString(R.string.error_0));
        } else if(loginCode == 1) {
            loginView.onLoginError(c.getString(R.string.error_1));
        } else if(loginCode == 2) {
            loginView.onLoginError(c.getString(R.string.error_2));
        } else if(loginCode == 3) {
            loginView.onLoginSuccess(c.getString(R.string.error_3));
        }
    }
}
