package com.hooq.demo.movie.common.data.converter;


import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hooq.demo.movie.common.data.entity.Date;
import com.hooq.demo.movie.common.util.Util;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DateConverter {

    Gson gson = Util.getInstance().getGson();

    @TypeConverter
    public Date getDate(String data) {
        Type type = new TypeToken<Date>() {
        }.getType();
        return gson.fromJson(data, type);
    }

    @TypeConverter
    public String getStringFromDate(Date date) {
        return gson.toJson(date);
    }

    @TypeConverter
    public List<Date> getListDate(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Date>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String getStringFromListDate(List<Date> dateList) {
        return gson.toJson(dateList);
    }

}
