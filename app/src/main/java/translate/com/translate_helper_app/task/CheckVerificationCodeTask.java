package translate.com.translate_helper_app.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import translate.com.translate_helper_app.activity.BaseActivity;
import translate.com.translate_helper_app.activity.EmailUsedActivity;
import translate.com.translate_helper_app.activity.RegisterActivity;
import translate.com.translate_helper_app.common.AppConstant;
import translate.com.translate_helper_app.common.RestStatus;
import translate.com.translate_helper_app.exception.ExceptionCode;
import translate.com.translate_helper_app.exception.TranslateException;
import translate.com.translate_helper_app.model.BasicUserInfo;
import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/17.
 */
public class CheckVerificationCodeTask extends AsyncTask<String, Void, String>
{
    private BaseActivity baseActivity;

    //获取验证码请求异步任务
    private String restUrl = AppConstant.BASE_URL.concat("/register/checkVerificationCode?email=");

    private String email;

    public CheckVerificationCodeTask(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    @Override
    protected String doInBackground(String... params)
    {
        String email = params[0];
        this.email = email;
        String code = params[1];

        restUrl = restUrl.concat(email).concat("&verificationCode=").concat(code);

        String response = null;
        try
        {
            response = HttpUtils.doGet(restUrl);
        } catch (Exception e)
        {
            response = RestStatus.NETWORK_ERROR.name();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String tokenId)
    {
        super.onPostExecute(tokenId);

        //服务器验证码校验成功
        if (!RestStatus.NETWORK_ERROR.name().equals(tokenId))
        {
            Toast.makeText(baseActivity, "验证码校验成功，返回当前的tokenId为 " + tokenId, Toast.LENGTH_SHORT).show();

            //验证码校验成功之后，查询绑定的邮箱有没有注册过账号
            UserInfoTask userInfoTask = new UserInfoTask(baseActivity, email, tokenId);

            userInfoTask.execute(email);
        } else
        {
            Toast.makeText(baseActivity, "验证码校验失败，网络错误！", Toast.LENGTH_SHORT).show();
        }
    }
}