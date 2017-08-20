package translate.com.translate_helper_app.task;

import android.os.AsyncTask;

import java.net.URL;

import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/17.
 */
public class CheckVerificationCodeTask extends AsyncTask<String, Integer, String>
{
    //获取验证码请求异步任务
    private String URL = "http://119.29.22.107:8080/register/checkVerificationCode?email=";

    @Override
    protected String doInBackground(String... strings)
    {
        String email = strings[0];
        String code = strings[1];
        URL += email + "&verificationCode=";
        URL += code;

        String response = HttpUtils.doGet(URL);

        return response;
    }
}