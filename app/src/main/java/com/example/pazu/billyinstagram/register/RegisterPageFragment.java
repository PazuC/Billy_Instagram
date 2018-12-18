package com.example.pazu.billyinstagram.register;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pazu.billyinstagram.R;
import com.example.pazu.billyinstagram.imageList.ImageItemListPageFragment;
import com.example.pazu.billyinstagram.login.LoginPageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterPageFragment extends Fragment implements RegisterContract.View {
    private Button register;
    private Button back;
    EditText signUpName;
    EditText signUpPassword;
    TextView userNameError;
    TextView passwordError;
    TextView registerSuccessMessage;

    RegisterContract.Presenter presenter;


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
        userNameError = view.findViewById(R.id.userNameError);
        passwordError = view.findViewById(R.id.passwordError);
        registerSuccessMessage = view.findViewById(R.id.registerSuccessMessage);

        signUpPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        presenter = new RegisterPagePresenter();
        presenter.setView(this);

        signUpName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onChangeUsername(s.toString());
            }
        });
        signUpPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onChangePassword(s.toString());
            }
        });


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
        userNameError.setText("* Username length should be more than 8");
    }

    @Override
    public void usernameTooLongError() {
        userNameError.setText("* Username length should be less than 30");
    }

    @Override
    public void passwordTooShortError() {
        passwordError.setText("* Password length should be more than 8");
    }

    @Override
    public void passwordTooLongError() {
        passwordError.setText("* Password length should be less than 30");
    }

    @Override
    public void serverResponseError(String error) {
        Toast toast = Toast.makeText(getContext(), error, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void usernameNoError() {
        userNameError.setText("");
    }

    @Override
    public void passwordNoError() {
        passwordError.setText("");
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
    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new AsteriskPasswordTransformationMethod.PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }
            public char charAt(int index) {
                return '*'; // This is the important part
            }
            public int length() {
                return mSource.length(); // Return default
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
}
