package com.example.pazu.billyinstagram.register;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.pazu.billyinstagram.model.user.User;
import com.example.pazu.billyinstagram.model.user.UserToken;
import com.google.gson.Gson;

public class ResgisterPagePresenter implements ResgisterContract.Presenter {
    ResgisterContract.View view;

    @Override
    public void setView(ResgisterContract.View view) {
        this.view = view;
    }

    @Override
    public void onRegisterClick(String username, String password) {
        final Gson gson = new Gson();
        User user = new User();

        user.userName = username;
        user.passwd = password;
        AndroidNetworking.post("https://hinl.app:9990/billy/register")
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
    public void onBackClick() {
        view.showLoginPage();
    }

    @Override
    public void onChangeUsername(String username) {

    }

    @Override
    public void onChangePassword(String password) {

    }
}
