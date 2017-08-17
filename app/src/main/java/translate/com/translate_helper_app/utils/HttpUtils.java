package translate.com.translate_helper_app.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import translate.com.translate_helper_app.model.LoginRequest;

/**
 * Created by Administrator on 2017/8/16.
 */
public class HttpUtils
{
    public static String doPost(String url, Object postData)
    {
        try
        {
            // 根据地址创建URL对象
            URL uri = new URL(url);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) uri
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);

            //传递的数据
            String data = GsonUtils.toJson(postData);

            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "keep-alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type",
                    "application/json");

            // 发送POST请求必须设置允许输出
            urlConnection.setDoOutput(true);
            // 发送POST请求必须设置允许输入setDoInput的默认值就是true
            urlConnection.setDoInput(true);
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            if (urlConnection.getResponseCode() == 200)
            {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();

                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1)
                {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }

                // 释放资源
                is.close();
                baos.close();

                // 返回字符串
                String result = new String(baos.toByteArray());

                return result;

            } else
            {
                Log.i("HttpUtils", "网络错误！");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
