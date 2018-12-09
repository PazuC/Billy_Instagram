package com.example.pazu.billyinstagram.imageUpload;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.pazu.billyinstagram.imageList.ImageItemListPageFragment;
import com.example.pazu.billyinstagram.R;
import com.example.pazu.billyinstagram.model.image.AddImageItem;
import com.example.pazu.billyinstagram.model.image.AddImageItemResponse;
import com.example.pazu.billyinstagram.model.user.UserToken;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddImageItemPageFragment extends Fragment {

    private static final int PHOTO_ALBUM_REQUEST_CODE = 0x10;
    ImageView imageView;
    Button add;
    EditText title;
    EditText description;
    Button upload;
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
                AddImageItem addImageItem = new AddImageItem();
                addImageItem.title = title.getText().toString();
                addImageItem.desc = description.getText().toString();
                addImageItem.token = getArguments().getString("Token");

                final Gson gson = new Gson();
                //
                AndroidNetworking.upload("https://hinl.app:9990/billy/item/add")
                        .addMultipartFile("img", imageFile)
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
                                            ImageItemListPageFragment imageItemListPageFragment = new ImageItemListPageFragment();
                                            Bundle args = new Bundle();
                                            UserToken userToken = new UserToken();
                                            userToken.token = getArguments().getString("Token");
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
            startActivityForResult(cameraIntent, PHOTO_ALBUM_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_ALBUM_REQUEST_CODE) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
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
