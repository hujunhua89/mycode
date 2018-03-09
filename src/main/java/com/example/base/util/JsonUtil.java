package com.example.base.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtil {
    private static Gson gson;
    private static Gson parseGson;

    public static Gson get() {
        if (gson == null) {
            gson = new GsonBuilder().disableHtmlEscaping().create();
        }
        return gson;
    }

    public static Gson parseGson() {
        if (parseGson == null) {
            parseGson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .disableHtmlEscaping().create();
        }
        return parseGson;
    }

    public static String toJson(Object obj) {
        return get().toJson(obj);
    }

    // 将Json数组解析成相应的映射对象列表
    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) throws Exception {
        List<T> lst = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            lst.add(new Gson().fromJson(elem, clazz));
        }
        return lst;
    }


}
