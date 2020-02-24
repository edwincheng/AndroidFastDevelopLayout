package com.github.edwincheng.androidfastdeveloplayout.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GsonUtil {
    /**
     * Json转化成对象
     *
     * @param result
     * @param ct
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String result, Class<T> ct) {
        T t = null;
        if (result.isEmpty()) {
            return t;
        } else {
            Gson gson = new Gson();
            t = gson.fromJson(result, ct);
        }
        return t;
    }

    /**
     * 对象转化成Json
     *
     * @param object
     * @return
     */
    public Object objectToJson(Object object) {
        try {
            if (object instanceof Map) { //如果是Map则转换为JsonObject
                Map<String, Object> map = (Map<String, Object>) object;
                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                JSONStringer jsonStringer = new JSONStringer().object();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> entry = iterator.next();
                    jsonStringer.key(entry.getKey()).value(objectToJson(entry.getValue()));
                }
                JSONObject jsonObject = new JSONObject(new JSONTokener(jsonStringer.endObject().toString()));
                return jsonObject;
            } else if (object instanceof List) {//如果是List则转换为JsonArray
                List<Object> list = (List<Object>) object;
                JSONStringer jsonStringer = new JSONStringer().array();
                for (int i = 0; i < list.size(); i++) {
                    jsonStringer.value(objectToJson(list.get(i)));
                }
                JSONArray jsonArray = new JSONArray(new JSONTokener(jsonStringer.endArray().toString()));
                return jsonArray;
            } else {
                return object;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
