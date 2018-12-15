package com.example.pazu.billyinstagram.imageList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pazu.billyinstagram.R;
import com.example.pazu.billyinstagram.model.image.ImageItemResponse;

import java.util.ArrayList;
import java.util.List;

public class ImageItemAdapter extends RecyclerView.Adapter<ImageItemAdapter.ViewHolder> {

    private ArrayList<ImageItemResponse.Data> dataList = new ArrayList<>();

    public void setDataList(List<ImageItemResponse.Data> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ImageItemResponse.Data data = dataList.get(i);
        viewHolder.title.setText(data.getTitle());
        viewHolder.description.setText(data.getDesc());
        //viewHolder.imageView.setImageBitmap do something with Glide
        Context context = viewHolder.itemView.getContext();
        Glide.with(context).load("https://hinl.app:9990" + data.imageUrl).into(viewHolder.imageView);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView description;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }

}
