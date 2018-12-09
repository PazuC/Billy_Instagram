package com.example.pazu.billyinstagram.imageList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.pazu.billyinstagram.R;
import com.example.pazu.billyinstagram.imageUpload.AddImageItemPageFragment;
import com.example.pazu.billyinstagram.model.image.ImageItemResponse;
import com.example.pazu.billyinstagram.model.user.UserToken;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageItemListPageFragment extends Fragment {
    Button add;
    TextView idTextView;

    private RecyclerView recyclerView;
    private ImageItemAdapter adapter;
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
                AddImageItemPageFragment addImageItemPageFragment = new AddImageItemPageFragment();
                Bundle args = new Bundle();
                args.putString("Token", token);
                addImageItemPageFragment.setArguments(args);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, addImageItemPageFragment);
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

                                ImageItemResponse imageItemResponse = gson.fromJson(response, ImageItemResponse.class);
                                adapter.setDataList(imageItemResponse.data);

                                idTextView.setText(imageItemResponse.name);
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
        adapter = new ImageItemAdapter();
        recyclerView.setAdapter(adapter);
    }

}
