package com.example.pazu.billyinstagram.imageList;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
public class ImageItemListPageFragment extends Fragment implements ImageItemListContract.View {
    Button add;
    TextView idTextView;

    private RecyclerView recyclerView;
    private ImageItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageItemListPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_item_list__page, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ImageItemListPresenter();
        presenter.setView(this);

        add = view.findViewById(R.id.add);
        idTextView = view.findViewById(R.id.idTextView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        String token = getArguments().getString("Token");
        presenter.requestImage(token);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getArguments().getString("Token");
                presenter.onAddClick(token);
            }
        });
    }

    @Override
    public void setAdapter(ImageItemAdapter imageItemAdapter) {
        recyclerView.setAdapter(imageItemAdapter);
    }

    @Override
    public void setLayoutManager() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void showAddImageItemPage(String string) {
        AddImageItemPageFragment addImageItemPageFragment = new AddImageItemPageFragment();
        Bundle args = new Bundle();
        args.putString("Token", string);
        addImageItemPageFragment.setArguments(args);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, addImageItemPageFragment);
        ft.commit();
    }

    @Override
    public void showIdTextView(String string) {
        idTextView.setText(string);
    }


}
