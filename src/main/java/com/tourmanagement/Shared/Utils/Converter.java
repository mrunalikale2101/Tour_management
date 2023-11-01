package com.tourmanagement.Shared.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converter {
    public static List<String> convertJsonImagesToListImages(String jsonImages) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>(){}.getType();

        return gson.fromJson(jsonImages, listType);
    }

    public static java.sql.Date convertDateUtilToSqlDate(Date date) {
        return  new java.sql.Date(date.getTime());
    }
}
