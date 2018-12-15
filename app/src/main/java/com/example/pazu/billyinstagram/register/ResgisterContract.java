package com.example.pazu.billyinstagram.register;

public interface ResgisterContract {

    interface View {
        void showLoginPage();

        void usernameTooShortError();

        void usernameTooLongError();

        void passwordTooShortError();

        void passwordTooLongError();

        void serverResponseError(String error);

        void usernameNoError();

        void passwordNoError();

        void showSuccessRegister();
    }

    interface Presenter {
        void setView(View view);

        void onRegisterClick(String username, String password);

        void onBackClick();

        void onChangeUsername(String username);

        void onChangePassword(String password);
    }
}
