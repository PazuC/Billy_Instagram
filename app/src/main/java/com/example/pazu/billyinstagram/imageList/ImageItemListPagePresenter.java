package com.example.pazu.billyinstagram.imageList;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.pazu.billyinstagram.R;
import com.example.pazu.billyinstagram.model.image.ImageItemResponse;
import com.example.pazu.billyinstagram.model.user.UserToken;
import com.google.gson.Gson;

public class ImageItemListPagePresenter implements ImageItemListContract.Presenter {
    ImageItemListContract.View view;

    @Override
    public void setView(ImageItemListContract.View view) {
        this.view = view;
    }

    @Override
    public void requestImage(String string) {
        final Gson gson = new Gson();
        final UserToken userToken = new UserToken();

        userToken.token = string;
        AndroidNetworking.post("https://hinl.app:9990/billy/item")
                .addBodyParameter("data", gson.toJson(userToken)) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {

                                ImageItemResponse imageItemResponse = gson.fromJson(response, ImageItemResponse.class);
                                view.showIdTextView(imageItemResponse.name);

                                ImageItemAdapter adapter = new ImageItemAdapter();
                                adapter.setDataList(imageItemResponse.data);
                                view.setLayoutManager();
                                view.setAdapter(adapter);

                                Log.d("TAG", "onResponse: ");

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


    @Override
    public void onAddClick(String string) {
        view.showAddImageItemPage(string);
    }
}
