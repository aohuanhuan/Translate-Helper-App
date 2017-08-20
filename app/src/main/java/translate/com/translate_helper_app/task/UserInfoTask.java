package translate.com.translate_helper_app.task;

import android.os.AsyncTask;

import translate.com.translate_helper_app.model.BasicUserInfo;
import translate.com.translate_helper_app.utils.GsonUtils;
import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/18.
 */
public class UserInfoTask extends AsyncTask<String, Void, BasicUserInfo>
{
    private String URL = "http://119.29.22.107:8080/register/queryBindingUserInfo?email=";

    @Override
    protected BasicUserInfo doInBackground(String... strings)
    {
        String email = strings[0];
        URL += email;

        String response = HttpUtils.doGet(URL);

        BasicUserInfo basicUserInfo = GsonUtils.fromJson(response, BasicUserInfo.class);

        return basicUserInfo;
    }
}
