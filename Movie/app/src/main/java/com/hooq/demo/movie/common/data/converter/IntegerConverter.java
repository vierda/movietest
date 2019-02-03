package com.hooq.demo.movie.common.data.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooq.demo.movie.common.util.Util;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class IntegerConverter {

    Gson gson = Util.getInstance().getGson();


    @TypeConverter
    public Integer getInteger(String data) {
        Type listType = new TypeToken<Integer>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String getStringFromInteger(Integer integer) {
        return gson.toJson(integer);
    }

    @TypeConverter
    public List<Integer> getListDescription(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Integer>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String getStringFromListInteger(List<Integer> descriptionList) {
        return gson.toJson(descriptionList);
    }


}
