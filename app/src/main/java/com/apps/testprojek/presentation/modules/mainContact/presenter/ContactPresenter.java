package com.apps.testprojek.presentation.modules.mainContact.presenter;

import com.apps.testprojek.presentation.modules.mainContact.view.IContactView;

public class ContactPresenter implements IContactPresenter {

    IContactView contactView;

    public ContactPresenter(IContactView contactView) {
        this.contactView = contactView;
    }


}
