package com.example.pazu.billyinstagram.login;

import android.text.TextWatcher;


public interface LoginContract {

    interface View {
        void showRegisterPage();

        void showImageListPage(String string);

        void usernameTooShortError();

        void usernameTooLongError();

        void passwordTooShortError();

        void passwordTooLongError();

        void serverResponseError(String error);

        void usernameNoError();

        void passwordNoError();
    }

    interface Presenter {

        void setView(View view);

        void onLoginClick(String username, String password);

        void onRegisterClick();

        void onChangeUsername(String username);

        void onChangePassword(String password);
    }
}
