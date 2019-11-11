package com.example.filmespopulares.data.local;

import androidx.room.TypeConverter;

import java.util.ArrayList;
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
        if (list != null) {
            for (Long i : list) {
                genreIds += "," + i;
            }
        }

        return genreIds;
    }

}
