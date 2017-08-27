package translate.com.translate_helper_app.task;

import android.os.AsyncTask;

import translate.com.translate_helper_app.model.BasicUserInfo;
import translate.com.translate_helper_app.model.LoginRequest;
import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/15.
 */
public class RegisterTask extends AsyncTask<Object, Void, Boolean>
{
    //注册请求异步任务
    private String URL = "http://119.29.22.107:8080/register/registerUser?emailToken=";

    @Override
    protected Boolean doInBackground(Object... obj)
    {
        BasicUserInfo basicUserInfo = (BasicUserInfo) obj[0];
        String tokenId = (String) obj[1];

        URL += tokenId;
        String response = HttpUtils.doPost(URL, basicUserInfo);
        if (null != response && "true".equals(response))
        {
            return true;
        }
        return false;
    }
}