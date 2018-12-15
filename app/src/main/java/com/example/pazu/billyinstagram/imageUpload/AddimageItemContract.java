package com.example.pazu.billyinstagram.imageUpload;


import java.io.File;

public interface AddimageItemContract {
    interface View {

        void openGallery();

        void showImageListPage(String string);

        void serverResponseError(String error);

    }

    interface Presenter {
        void setView(View view);

        void onAddClick();

        void onBackClick(String string);

        void onUploadClick(File file, String title, String description, String token);

    }
}
