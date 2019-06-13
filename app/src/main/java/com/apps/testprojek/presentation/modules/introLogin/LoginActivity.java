package com.apps.testprojek.presentation.modules.introLogin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.apps.testprojek.R;
import com.apps.testprojek.data.local.RealmHelper;
import com.apps.testprojek.data.local.database.Loged;
import com.apps.testprojek.presentation.modules.BaseActivity;
import com.apps.testprojek.presentation.modules.introLogin.view.ILoginView;
import com.apps.testprojek.presentation.modules.introLogin.presenter.ILoginPresenter;
import com.apps.testprojek.presentation.modules.introLogin.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity implements ILoginView {

    ILoginPresenter loginPresenter;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    CardView btnLogin;
    @BindView(R.id.btnEye)
    ImageView btnEye;
    private Boolean chiper_text = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initBackButton();
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
        btnEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!chiper_text){
                    chiper_text = true;
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    chiper_text = false;
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                loginPresenter.onLogin(getApplicationContext(), username, password);
            }
        });
    }

    @Override
    public void onLoginSuccess(String message) {
        Toasty.success(this, message, Toast.LENGTH_SHORT).show();
        RealmHelper.getrealm(getApplicationContext()).beginTransaction();
        Loged db = RealmHelper.getrealm(getApplicationContext()).createObject(Loged.class);
        db.setStatus(true);
        RealmHelper.getrealm(getApplicationContext()).commitTransaction();
        getNavigator().openHome(true,LoginActivity.this);

    }

    @Override
    public void onLoginError(String message) {
        Toasty.error(this, message, Toast.LENGTH_SHORT).show();
    }
}