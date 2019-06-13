package com.apps.testprojek.presentation.modules.mainContact;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.apps.testprojek.R;
import com.apps.testprojek.presentation.modules.BaseActivity;
import com.apps.testprojek.presentation.modules.mainContact.view.IContactView;

public class ContactActivity extends BaseActivity implements IContactView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initBackButton();
    }
}
