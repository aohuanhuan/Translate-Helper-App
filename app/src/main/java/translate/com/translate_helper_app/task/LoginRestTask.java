package translate.com.translate_helper_app.task;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import translate.com.translate_helper_app.model.LoginRequest;
import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/15.
 */
public class LoginRestTask extends AsyncTask<String, Integer, Boolean>
{
    //登录请求异步任务
    private String URL = "http://119.29.22.107:8080/register/login";

    @Override
    protected Boolean doInBackground(String... strings)
    {
        String email = strings[0];
        String password = strings[1];

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        String response = HttpUtils.doPost(URL, loginRequest);
        if (null != response && "true".equals(response))
        {
            return true;
        }
        return false;
    }
}