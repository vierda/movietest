package com.hooq.demo.movie.common.data.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooq.demo.movie.common.util.Util;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StringConverter {

    Gson gson = Util.getInstance().getGson();


    @TypeConverter
    public List<String> getListString(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String getString(List<String> stringList) {
        return gson.toJson(stringList);
    }

}
