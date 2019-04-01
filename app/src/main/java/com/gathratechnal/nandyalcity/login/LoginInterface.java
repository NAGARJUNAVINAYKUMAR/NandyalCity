package com.gathratechnal.nandyalcity.login;

import com.gathratechnal.nandyalcity.login.model.LoginModel;

/**
 * Created by praveenkumar on 12/6/17 10:53 PM.
 */

public interface LoginInterface {

    void onLoginPostExecute(LoginModel loginModel, String message);
}
