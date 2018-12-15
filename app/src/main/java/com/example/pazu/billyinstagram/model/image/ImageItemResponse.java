package com.example.pazu.billyinstagram.model.image;

import java.util.ArrayList;


public class ImageItemResponse {
    public String name;
    public ArrayList<Data> data;

    public static class Data {
        public String id;
        public String imageUrl;
        public String title;
        public String desc;


        public String getTitle() {
            return title;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getDesc() {
            return desc;
        }
    }
}
