package com.example.pazu.billyinstagram.imageList;

import android.view.View;

import com.example.pazu.billyinstagram.login.LoginContract;

public interface ImageItemListContract {
    interface View {
        void showAddImageItemPage(String string);

        void showIdTextView(String string);

        void setAdapter(ImageItemAdapter imageItemAdapter);

        void setLayoutManager();
    }

    interface Presenter {
        void setView(View view);

        void requestImage(String string);

        void onAddClick(String string);
    }
}
