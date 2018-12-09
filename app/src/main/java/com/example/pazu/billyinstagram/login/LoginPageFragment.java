package com.example.pazu.billyinstagram.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class LoginPageFragment extends Fragment implements LoginContract.View{
    private Button login;
    private Button register;
    private EditText userName;
    private EditText password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_login_page = inflater.inflate(R.layout.fragment_login_page, container, false);
        login = view_login_page.findViewById(R.id.login);
        register = view_login_page.findViewById(R.id.register);
        userName = view_login_page.findViewById(R.id.userName);
        password = view_login_page.findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Gson gson = new Gson();
                final User user = new User();

                user.userName = userName.getText().toString();
                user.passwd = password.getText().toString();
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
                                            ImageItemListPageFragment imageItemListPageFragment = new ImageItemListPageFragment();
                                            Bundle args = new Bundle();
                                            args.putString("Token", userToken.token);
                                            imageItemListPageFragment.setArguments(args);

                                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                                            ft.replace(R.id.container, imageItemListPageFragment);
                                            ft.commit();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getContext(), anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                                anError.printStackTrace();
                            }
                        });


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterPageFragment registerPageFragment = new RegisterPageFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, registerPageFragment);
                ft.commit();
            }
        });
        return view_login_page;


    }

    @Override
    public void showRegisterPage() {

    }

    @Override
    public void showImageListPage() {

    }

    @Override
    public void showUusernameTooShortError() {

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

    }
}