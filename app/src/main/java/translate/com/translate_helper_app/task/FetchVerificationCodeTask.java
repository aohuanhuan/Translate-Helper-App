package translate.com.translate_helper_app.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import translate.com.translate_helper_app.activity.BaseActivity;
import translate.com.translate_helper_app.activity.CheckVerificationActivity;
import translate.com.translate_helper_app.common.AppConstant;
import translate.com.translate_helper_app.common.RestStatus;
import translate.com.translate_helper_app.exception.ExceptionCode;
import translate.com.translate_helper_app.exception.TranslateException;
import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/17.
 */
public class FetchVerificationCodeTask extends AsyncTask<String, Integer, RestStatus>
{
    private BaseActivity baseActivity;

    private String email;
    //获取验证码请求异步任务
    private String restUrl = AppConstant.BASE_URL.concat("/register/fetchVerificationCode?receiver=");

    public FetchVerificationCodeTask(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    @Override
    protected RestStatus doInBackground(String... params)
    {
        String email = params[0];
        this.email = email;

        restUrl = restUrl.concat(email);

        String response = null;
        try
        {
            response = HttpUtils.doGet(restUrl);
        } catch (Exception e)
        {
            return RestStatus.NETWORK_ERROR;
        }
        if ("true".equals(response))
        {
            return RestStatus.SUCCESS;
        }

        return RestStatus.FAIL;
    }

    @Override
    protected void onPostExecute(RestStatus status)
    {
        super.onPostExecute(status);

        switch (status)
        {
            case SUCCESS:
                Toast.makeText(baseActivity, "已成功向您的邮箱 " + email + " 发送验证码！", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("email", email);

                baseActivity.openActivity(CheckVerificationActivity.class, bundle);
                break;
            case FAIL:
                Toast.makeText(baseActivity, "向您的邮箱 " + email + " 发送验证码失败！", Toast.LENGTH_SHORT).show();
                break;
            case NETWORK_ERROR:
                Toast.makeText(baseActivity, "获取验证码失败，网络错误！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}