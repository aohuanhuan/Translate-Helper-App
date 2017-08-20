package translate.com.translate_helper_app.utils;

import android.support.annotation.NonNull;

import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import translate.com.translate_helper_app.common.HttpMethod;
import translate.com.translate_helper_app.exception.ExceptionCode;
import translate.com.translate_helper_app.exception.TranslateException;

/**
 * Created by Administrator on 2017/8/16.
 */
public class HttpUtils
{
    /**
     * rest的Get请求
     *
     * @param url 请求的链接
     * @return rest请求结果
     */
    public static String doGet(String url)
    {
        try
        {
            HttpURLConnection urlConnection = getHttpURLConnection(url);
            urlConnection.setRequestMethod(HttpMethod.GET.name());

            return getResponse(urlConnection);
        } catch (IOException e)
        {
            throw new TranslateException(ExceptionCode.NETWORK_ERROR);
        }
    }

    /**
     * rest的POST请求
     *
     * @param url      请求的链接
     * @param postData 请求数据
     * @return rest请求结果
     */
    public static String doPost(String url, Object postData)
    {
        try
        {
            HttpURLConnection urlConnection = getHttpURLConnection(url);

            urlConnection.setRequestMethod(HttpMethod.POST.name());
            // 发送POST请求必须设置允许输出
            urlConnection.setDoOutput(true);
            // 发送POST请求必须设置允许输入setDoInput的默认值就是true
            urlConnection.setDoInput(true);

            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            //传递的数据
            String data = GsonUtils.toJson(postData);
            os.write(data.getBytes());
            os.flush();

            return getResponse(urlConnection);
        } catch (IOException e)
        {
            throw new TranslateException(ExceptionCode.NETWORK_ERROR);
        }
    }

    @NonNull
    private static String getResponse(HttpURLConnection urlConnection) throws IOException
    {
        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
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
            urlConnection.disconnect();

            // 返回字符串
            String response = new String(baos.toByteArray());
            return response;
        } else
        {
            throw new TranslateException(ExceptionCode.NETWORK_ERROR);
        }
    }

    @NonNull
    private static HttpURLConnection getHttpURLConnection(String url) throws IOException
    {
        // 根据地址创建URL对象
        URL uri = new URL(url);
        // 根据URL对象打开链接
        HttpURLConnection urlConnection = (HttpURLConnection) uri
                .openConnection();
        // 设置请求的超时时间
        urlConnection.setReadTimeout(5000);
        urlConnection.setConnectTimeout(5000);

        // 设置请求的头
        urlConnection.setRequestProperty("Connection", "keep-alive");
        // 设置请求的头
        urlConnection.setRequestProperty("Content-Type",
                "application/json");

        return urlConnection;
    }
}