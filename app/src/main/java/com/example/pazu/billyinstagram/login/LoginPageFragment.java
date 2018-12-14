package com.example.pazu.billyinstagram.login;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.pazu.billyinstagram.R;
import com.example.pazu.billyinstagram.model.user.User;
import com.example.pazu.billyinstagram.model.user.UserToken;
import com.example.pazu.billyinstagram.register.RegisterPageFragment;
import com.example.pazu.billyinstagram.imageList.ImageItemListPageFragment;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPageFragment extends Fragment implements LoginContract.View {
    private Button login;
    private Button register;
    private EditText userName;
    private EditText password;
    private TextView userNameError;
    LoginPagePresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       return inflater.inflate(R.layout.fragment_login_page, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        login = view.findViewById(R.id.login);
        register = view.findViewById(R.id.register);
        userName = view.findViewById(R.id.userName);
        password = view.findViewById(R.id.password);
        userNameError = view.findViewById(R.id.userNameError);
        presenter = new LoginPagePresenter();
        presenter.setView(this);





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLoginClick(userName.getText().toString(),password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.onRegisterClick();

            }
        });
    }


    @Override
    public void showRegisterPage() {
        RegisterPageFragment registerPageFragment = new RegisterPageFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, registerPageFragment);
        ft.commit();
    }

    @Override
    public void showImageListPage(String string) {
        ImageItemListPageFragment imageItemListPageFragment = new ImageItemListPageFragment();
        Bundle args = new Bundle();
        args.putString("Token", string);
        imageItemListPageFragment.setArguments(args);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, imageItemListPageFragment);
        ft.commit();
    }

    @Override
    public void usernameTooShortError() {
       userNameError.setText("too short");
    }

    @Override
    public void usernameTooLongError() {
        int length = userName.getText().toString().length();
        if (length > 30) {

        }
    }

    @Override
    public void passwordTooShortError() {

        }


    @Override
    public void passwordTooLongError() {
        int length = password.getText().toString().length();
        if (length > 30) {

        }
    }

    @Override
    public void serverResponseError(String error) {
        // show serverResponse not in a toast way
    }
}