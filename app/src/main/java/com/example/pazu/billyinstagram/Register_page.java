package com.example.pazu.billyinstagram;


import android.os.Bundle;
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
import com.google.gson.Gson;

import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register_page extends Fragment {
    private Button register;
    private Button back;
    EditText signUpName;
    EditText signUpPassword;

    public Register_page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view_register_page = inflater.inflate(R.layout.fragment_register_page, container, false);
        register = view_register_page.findViewById(R.id.register);
        back = view_register_page.findViewById(R.id.back);
        signUpName = view_register_page.findViewById(R.id.signUpName);
        signUpPassword = view_register_page.findViewById(R.id.signUpPassword);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login_page login_page = new Login_page();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, login_page);
                ft.commit();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Gson gson = new Gson();
                User user = new User();

                user.userName = signUpName.getText().toString();
                user.passwd = signUpPassword.getText().toString();
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
                                        response = gson.fromJson("Token",response.getClass());
                                        Log.d("TAG", "onResponse: " + response);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                anError.printStackTrace();
                            }
                        });

            }
        });
        return view_register_page;
    }


}
