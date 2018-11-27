package com.example.pazu.billyinstagram;

import java.util.ArrayList;

public class GetFromServer {
    private String name;
    private ArrayList<Data> data;

    public static class Data {
        String id;
        String imageUrl;
        String title;
        String desc;


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
