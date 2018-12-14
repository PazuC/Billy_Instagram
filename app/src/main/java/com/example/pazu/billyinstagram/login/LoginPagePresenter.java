package com.example.pazu.billyinstagram.login;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.pazu.billyinstagram.R;
import com.example.pazu.billyinstagram.model.user.User;
import com.example.pazu.billyinstagram.model.user.UserToken;
import com.example.pazu.billyinstagram.register.RegisterPageFragment;
import com.google.gson.Gson;

public class LoginPagePresenter implements LoginContract.Presenter {

    LoginContract.View view;
    public void setView(LoginContract.View view){
        this.view = view;
    }


    @Override
    public void onLoginClick(String username, String password) {
       /* loginPageFragment.usernameTooLongError();
        loginPageFragment.usernameTooShortError();
        loginPageFragment.passwordTooLongError();
        loginPageFragment.passwordTooShortError();
        loginPageFragment.showImageListPage();*/

        User user = new User();
        final Gson gson = new Gson();

        user.userName = username;
        user.passwd = password;
        AndroidNetworking.post("https://hinl.app:9990/billy/login")
                .addBodyParameter("data", gson.toJson(user)) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {

                                UserToken userToken = gson.fromJson(response, UserToken.class);
                                Log.d("TAG", "onResponse: " + userToken.token);

                                if (userToken.token != "") {
                                    view.showImageListPage();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        view.serverResponseError(anError.getErrorBody());
                        anError.printStackTrace();
                    }
                });
    }

    @Override
    public void onRegisterClick() {
        view.showRegisterPage();
    }

    @Override
    public void onChangeUsername(String username) {

    }

    @Override
    public void onChangePassword(String password) {

    }
}
