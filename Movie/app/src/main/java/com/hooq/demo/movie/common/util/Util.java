package com.hooq.demo.movie.common.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hooq.demo.movie.R;

public class Util {

    private Gson gson;
    private static Util _instance;

    public static Util getInstance() {
        if (_instance == null) {
            _instance = new Util();
            _instance.initGson();
        }

        return _instance;
    }

    private Util() {
    }

    private void initGson() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    public Gson getGson() {
        return gson;
    }

//    public List<Product> parseProductList(JsonObject jsonObject) {
//
//        if (jsonObject != null) {
//            List<Product> products = new ArrayList<>();
//            JsonArray array = jsonObject.get("products").getAsJsonArray();
//            if (array != null && array.size() > 0) {
//                for (int i = 0; i < array.size(); i++) {
//                    Product product = gson.fromJson(array.get(i), Product.class);
//                    products.add(product);
//                }
//
//                return products;
//            }
//
//        }
//
//        return null;
//    }

    public ProgressDialog getProgressDialog(Context context) {
        ProgressDialog pd = new ProgressDialog(context, R.style.MyTheme);
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        return pd;
    }
}
