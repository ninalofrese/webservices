package com.example.filmespopulares.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Converters {

    @TypeConverter
    public List<Long> gettingListFromString(String genreIds) {
        List<Long> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(Long.parseLong(s));
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<Long> list) {
        String genreIds = "";
        for (long i : list) {
            genreIds += "," + i;
        }
        return genreIds;
    }

}
