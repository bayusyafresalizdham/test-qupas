package com.apps.testprojek.presentation.modules.introLogin.model;

import com.apps.testprojek.utils.ConstantUtils;

public class Login implements ILogin {

    String username, password;

    public Login(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int isValidData() {
        if(getUsername().equalsIgnoreCase("") || getPassword().equalsIgnoreCase("")) {
            return 0;
        } else{
            if(getUsername().equalsIgnoreCase(ConstantUtils.LOGIN_USERNAME) &&
                    getPassword().equalsIgnoreCase(ConstantUtils.LOGIN_PASSWORD)) {
                return 3;
            }else if(!getUsername().equalsIgnoreCase(ConstantUtils.LOGIN_USERNAME) &&
                    getPassword().equalsIgnoreCase(ConstantUtils.LOGIN_PASSWORD)) {
                return 1;
            }else if(getUsername().equalsIgnoreCase(ConstantUtils.LOGIN_USERNAME) &&
                    !getPassword().equalsIgnoreCase(ConstantUtils.LOGIN_PASSWORD)) {
                return 2;
            }else{
                return 0;
            }
        }
    }
}
