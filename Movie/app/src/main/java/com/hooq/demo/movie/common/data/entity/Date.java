package com.hooq.demo.movie.common.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.hooq.demo.movie.common.data.converter.StringConverter;

@Entity
public class Date {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("maximum")
    @ColumnInfo(name = "maximum")
    @TypeConverters(StringConverter.class)
    private String maximum;

    @SerializedName("minimum")
    @ColumnInfo(name = "minimum")
    @TypeConverters(StringConverter.class)
    private String minimum;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }
}
