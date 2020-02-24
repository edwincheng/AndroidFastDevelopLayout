package com.github.edwincheng.androidfastdeveloplayout.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO<解析数据类>
 */
public class HttpGsonUtil {

	private static HttpGsonUtil sHttpGsonUtil;

	public static synchronized HttpGsonUtil getInstance() {
		if (sHttpGsonUtil == null) {
			sHttpGsonUtil = new HttpGsonUtil();
		}
		return sHttpGsonUtil;
	}
	/*
     *转换json 方法
     */
	public Object jsonEnclose(Object obj){
		try{
			if(obj instanceof Map) { //如果是Map则转换为JsonObject
				Map<String, Object> map = (Map<String,Object>)obj;
				Iterator<Map.Entry<String,Object>> iterator =map.entrySet().iterator();
				JSONStringer jsonStringer =new JSONStringer().object();
				while(iterator.hasNext()){
					Map.Entry<String, Object> entry = iterator.next();
					jsonStringer.key(entry.getKey()).value(jsonEnclose(entry.getValue()));
				}
				JSONObject jsonObject = new JSONObject(new JSONTokener(jsonStringer.endObject().toString()));
				return jsonObject;
			}else if(obj instanceof List){//如果是List则转换为JsonArray
				List<Object> list = (List<Object>)obj;
				JSONStringer jsonStringer=new JSONStringer().array();
				for(int i=0;i<list.size();i++){
					jsonStringer.value(jsonEnclose(list.get(i)));
				}
				JSONArray jsonArray = new JSONArray(new JSONTokener(jsonStringer.endArray().toString()));
				return jsonArray;
			}else{
				return obj;
			}
		}catch(Exception e){
			return e.getMessage();
		}
	}
}