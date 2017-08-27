package translate.com.translate_helper_app.task;

import android.os.AsyncTask;

import translate.com.translate_helper_app.common.AppConstant;
import translate.com.translate_helper_app.model.BasicUserInfo;
import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/15.
 */
public class RegisterTask extends AsyncTask<Object, Void, Boolean>
{
    //注册请求异步任务
    private String restUrl = AppConstant.BASE_URL.concat("/register/registerUser?emailToken=");

    @Override
    protected Boolean doInBackground(Object... params)
    {
        BasicUserInfo basicUserInfo = (BasicUserInfo) params[0];
        String tokenId = (String) params[1];

        restUrl = restUrl.concat(tokenId);

        String response = null;
        try
        {
            response = HttpUtils.doPost(restUrl, basicUserInfo);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (null != response && "true".equals(response))
        {
            return true;
        }
        return false;
    }
}