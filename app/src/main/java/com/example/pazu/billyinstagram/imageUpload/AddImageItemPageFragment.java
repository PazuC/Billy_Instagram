package com.example.pazu.billyinstagram.imageUpload;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
public class AddImageItemPageFragment extends Fragment implements AddimageItemContract.View {

    private static final int PHOTO_ALBUM_REQUEST_CODE = 0x10;
    ImageView imageView;
    Button add;
    EditText title;
    EditText description;
    Button upload;
    Button back;
    File imageFile;
    AddimageItemContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_image_item_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageView);
        add = view.findViewById(R.id.add);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        upload = view.findViewById(R.id.upload);
        back = view.findViewById(R.id.back);

        presenter = new AddImageItemPagePresenter();
        presenter.setView(this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getArguments().getString("Token");
                presenter.onUploadClick(imageFile, title.getText().toString(), description.getText().toString(), token);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getArguments().getString("Token");
                presenter.onBackClick(token);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddClick();
            }
        });


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

    @Override
    public void openGallery() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        } else {
            startGallery();
        }
    }

    @Override
    public void showImageListPage(String string) {
        ImageItemListPageFragment imageItemListPageFragment = new ImageItemListPageFragment();
        Bundle args = new Bundle();
        string = getArguments().getString("Token");
        args.putString("Token", string);
        imageItemListPageFragment.setArguments(args);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, imageItemListPageFragment);
        ft.commit();

    }

    @Override
    public void serverResponseError(String error) {

    }
}
