package com.example.pazu.billyinstagram.register;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.pazu.billyinstagram.R;
import com.example.pazu.billyinstagram.login.LoginPageFragment;
import com.example.pazu.billyinstagram.model.user.User;
import com.example.pazu.billyinstagram.model.user.UserToken;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterPageFragment extends Fragment implements ResgisterContract.View {
    private Button register;
    private Button back;
    EditText signUpName;
    EditText signUpPassword;

    ResgisterPagePresenter presenter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_page, container, false);

    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        register = view.findViewById(R.id.register);
        back = view.findViewById(R.id.back);
        signUpName = view.findViewById(R.id.signUpName);
        signUpPassword = view.findViewById(R.id.signUpPassword);

        presenter = new ResgisterPagePresenter();
        presenter.setView(this);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackClick();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterClick(signUpName.getText().toString(), signUpPassword.getText().toString());
            }
        });

    }

    @Override
    public void showLoginPage() {
        LoginPageFragment login_page = new LoginPageFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, login_page);
        ft.commit();
    }

    @Override
    public void usernameTooShortError() {

    }

    @Override
    public void usernameTooLongError() {

    }

    @Override
    public void passwordTooShortError() {

    }

    @Override
    public void passwordTooLongError() {

    }

    @Override
    public void serverResponseError(String error) {
        // show serverResponse not in a toast way
    }
}