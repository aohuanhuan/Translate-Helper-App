package translate.com.translate_helper_app.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import translate.com.translate_helper_app.activity.BaseActivity;
import translate.com.translate_helper_app.activity.MainActivity;
import translate.com.translate_helper_app.common.AppConstant;
import translate.com.translate_helper_app.common.RestStatus;
import translate.com.translate_helper_app.model.LoginRequest;
import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/15.
 */
public class LoginTask extends AsyncTask<String, Void, RestStatus>
{
    private BaseActivity baseActivity;

    public LoginTask(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    private String email;
    //登录请求异步任务
    private String restUrl = AppConstant.BASE_URL.concat("/register/login");

    @Override
    protected RestStatus doInBackground(String... params)
    {
        String email = params[0];
        this.email = email;
        String password = params[1];

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        String response = null;
        try
        {
            response = HttpUtils.doPost(restUrl, loginRequest);
        } catch (Exception e)
        {
            return RestStatus.NETWORK_ERROR;
        }
        if (null != response && "true".equals(response))
        {
            return RestStatus.SUCCESS;
        }
        return RestStatus.FAIL;
    }

    @Override
    protected void onPostExecute(RestStatus restStatus)
    {
        super.onPostExecute(restStatus);

        switch (restStatus)
        {
            case SUCCESS:
                Toast.makeText(baseActivity, "登录成功！", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("email", email);

                baseActivity.openActivity(MainActivity.class, bundle);
                break;
            case NETWORK_ERROR:
                Toast.makeText(baseActivity, "登录失败，网络错误！", Toast.LENGTH_SHORT).show();
                break;
            case FAIL:
                Toast.makeText(baseActivity, "登录失败，用户名或者密码错误！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}