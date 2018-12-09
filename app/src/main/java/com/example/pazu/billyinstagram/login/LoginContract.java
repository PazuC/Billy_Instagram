package com.example.pazu.billyinstagram.login;

public interface LoginContract {
    interface View{
        void showRegisterPage();
        void showImageListPage();
        void showUusernameTooShortError();
        void usernameTooLongError();
        void passwordTooShortError();
        void passwordTooLongError();
        void serverResponseError(String error);
    }
    interface Presenter{
        void onLoginClick(String username, String password);
        void onRegisterClick();
        void onChangeUsername(String username);
        void onChangePassword(String password);
    }
}
