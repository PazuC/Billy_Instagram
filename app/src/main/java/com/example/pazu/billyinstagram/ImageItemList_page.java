package com.example.pazu.billyinstagram;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageItemList_page extends Fragment {
    Button add;
    TextView idTextView;

    private RecyclerView recyclerView;
    private Myadapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view_imageItemList_page = inflater.inflate(R.layout.fragment_image_item_list__page, container, false);
        add = view_imageItemList_page.findViewById(R.id.add);
        idTextView = view_imageItemList_page.findViewById(R.id.idTextView);

        initRecyclerView(view_imageItemList_page);
        final String token = getArguments().getString("Token");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddImageItem_page addImageItem_page = new AddImageItem_page();
                Bundle args = new Bundle();
                args.putString("Token", token);
                addImageItem_page.setArguments(args);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, addImageItem_page);
                ft.commit();
            }
        });
        final Gson gson = new Gson();
        final UserToken userToken = new UserToken();

        userToken.token = getArguments().getString("Token");
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

                                GetFromServer getFromServer = gson.fromJson(response, GetFromServer.class);
                                adapter.setDataList(getFromServer.data);

                                idTextView.setText(getFromServer.name);
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


        return view_imageItemList_page;
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);


        // Layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        // Retrieve data:
        //  List<Route> routes = SQLite.select().from(Route.class).queryList();
        adapter = new Myadapter();
        recyclerView.setAdapter(adapter);
    }

}
