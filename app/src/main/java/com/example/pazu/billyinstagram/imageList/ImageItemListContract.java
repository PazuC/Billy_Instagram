package com.example.pazu.billyinstagram.imageList;

import android.view.View;

import com.example.pazu.billyinstagram.login.LoginContract;
import com.example.pazu.billyinstagram.model.image.ImageItemResponse;

public interface ImageItemListContract {
    interface View {
        void showAddImageItemPage(String string);

        void showIdTextView(String string);

        //void receiveImageItem(ImageItemResponse data);
    }

    interface Presenter {
        void setView(View view);

        void requestImage(String string);

        void onAddClick(String string);
    }
}
