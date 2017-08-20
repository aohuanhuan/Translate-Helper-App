package translate.com.translate_helper_app.task;

import android.os.AsyncTask;

import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/17.
 */
public class FetchVerificationCodeTask extends AsyncTask<String, Integer, Boolean>
{
    //获取验证码请求异步任务
    private String URL = "http://119.29.22.107:8080/register/fetchVerificationCode?receiver=";

    @Override
    protected Boolean doInBackground(String... strings)
    {
        String email = strings[0];

        URL += email;

        String response = HttpUtils.doGet(URL);
        if ("true".equals(response))
        {
            return true;
        }
        return false;
    }
}