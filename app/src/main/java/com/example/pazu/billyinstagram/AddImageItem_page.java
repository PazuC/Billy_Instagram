package com.example.pazu.billyinstagram;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddImageItem_page extends Fragment {
    ImageView imageView;
    Button add;
    EditText title;
    EditText description;
    Button upload;
    Bitmap bitmapImage = null;
    File imageFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_addImageItem_page = inflater.inflate(R.layout.fragment_add_image_item_page, container, false);
        imageView = view_addImageItem_page.findViewById(R.id.imageView);
        add = view_addImageItem_page.findViewById(R.id.add);
        title = view_addImageItem_page.findViewById(R.id.title);
        description = view_addImageItem_page.findViewById(R.id.description);
        upload = view_addImageItem_page.findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToServerImage addToServerImage = new AddToServerImage();
                AddToServer addToServer = new AddToServer();
                addToServerImage.bitmap = bitmapImage;
                addToServer.title = title.getText().toString();
                addToServer.desc = description.getText().toString();
                addToServer.token = getArguments().getString("Token");

                final Gson gson = new Gson();
                //
                AndroidNetworking.upload("https://hinl.app:9990/billy/item/add")
                        .addMultipartFile("img", imageFile)
                        .addMultipartParameter("data", gson.toJson(addToServer))
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if (response != null) {

                                        ReturnId returnId = gson.fromJson(response, ReturnId.class);
                                        Log.d("TAG", "onResponse: " + returnId.id);

                                        if (returnId.id != "") {
                                            ImageItemList_page imageItemList_page = new ImageItemList_page();
                                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                                            ft.replace(R.id.container, imageItemList_page);
                                            ft.commit();
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
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                } else {
                    startGallery();
                }
            }
        });
        return view_addImageItem_page;
    }

    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(returnUri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imagePath = cursor.getString(columnIndex);
                    imageFile = new File(imagePath);
                    cursor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmapImage);
            }
        }
    }

}
