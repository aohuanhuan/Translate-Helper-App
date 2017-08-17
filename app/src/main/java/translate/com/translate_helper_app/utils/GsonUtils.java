package translate.com.translate_helper_app.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2017/8/9.
 */
public class GsonUtils
{

    private static Gson gson = new GsonBuilder().create();

    public static String toJson(Object obj)
    {
        return null == obj ? "" : gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz)
    {
        return "".equals(json) ? null : gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, TypeToken<T> token)
    {
        return "".equals(json) ? null : (T) gson.fromJson(json, token.getType());
    }
}