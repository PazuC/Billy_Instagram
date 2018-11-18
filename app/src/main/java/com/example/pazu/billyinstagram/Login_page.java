package com.example.pazu.billyinstagram;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_page extends Fragment {
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
        EditText userName = view_login_page.findViewById(R.id.userName);
        EditText password = view_login_page.findViewById(R.id.password);

        /*to do login event
        -----*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register_page register_page = new Register_page();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, register_page);
                ft.commit();
            }
        });return view_login_page;


    }

}