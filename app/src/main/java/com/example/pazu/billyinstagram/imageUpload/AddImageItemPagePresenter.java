package com.example.pazu.billyinstagram.imageUpload;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import com.example.pazu.billyinstagram.imageList.ImageItemListPageFragment;
import com.example.pazu.billyinstagram.model.image.AddImageItem;
import com.example.pazu.billyinstagram.model.image.AddImageItemResponse;
import com.example.pazu.billyinstagram.model.user.UserToken;
import com.google.gson.Gson;

import java.io.File;

public class AddImageItemPagePresenter implements AddimageItemContract.Presenter {
    AddimageItemContract.View view;


    @Override
    public void setView(AddimageItemContract.View view) {
        this.view = view;
    }

    @Override
    public void onAddClick() {
        view.openGallery();
    }

    @Override
    public void onBackClick(String string) {
        view.showImageListPage(string);
    }

    @Override
    public void onUploadClick(File file, String title, String description, final String token) {
        AddImageItem addImageItem = new AddImageItem();
        addImageItem.title = title;
        addImageItem.desc = description;
        addImageItem.token = token;

        final Gson gson = new Gson();
        //
        AndroidNetworking.upload("https://hinl.app:9990/billy/item/add")
                .addMultipartFile("img", file)
                .addMultipartParameter("data", gson.toJson(addImageItem))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {

                                AddImageItemResponse addImageItemResponse = gson.fromJson(response, AddImageItemResponse.class);
                                Log.d("TAG", "onResponse: " + addImageItemResponse.id);

                                if (addImageItemResponse.id != "") {
                                    view.showImageListPage(token);
                                }
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

}



