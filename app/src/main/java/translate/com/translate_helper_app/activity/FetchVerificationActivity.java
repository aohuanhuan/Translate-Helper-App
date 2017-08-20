package translate.com.translate_helper_app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import translate.com.translate_helper_app.R;
import translate.com.translate_helper_app.common.RegexConst;
import translate.com.translate_helper_app.exception.ExceptionCode;
import translate.com.translate_helper_app.exception.TranslateException;
import translate.com.translate_helper_app.task.FetchVerificationCodeTask;

public class FetchVerificationActivity extends BaseActivity implements View.OnClickListener
{
    private TextView back;
    private TextView more;
    private TextView title;

    private TextView useLaw;
    private TextView privacy;

    private EditText registerEmail;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_verification_code);

        initViews();
    }

    private void initViews()
    {
        /**
         * 获取组件
         */
        back = (TextView) findViewById(R.id.back);
        more = (TextView) findViewById(R.id.more);
        title = (TextView) findViewById(R.id.title);

        useLaw = (TextView) findViewById(R.id.useLaw);
        privacy = (TextView) findViewById(R.id.privacy);

        registerEmail = (EditText) findViewById(R.id.registerEmail);

        next = (Button) findViewById(R.id.next);

        //注册界面只需要显示
        back.setText(getString(R.string.back));
        title.setText(getString(R.string.register));
        more.setText("");

        //设置监听
        back.setOnClickListener(this);
        useLaw.setOnClickListener(this);
        privacy.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.useLaw:
                Toast.makeText(this, "跳转到使用条款页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.privacy:
                Toast.makeText(this, "跳转到隐私政策页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.next:
                fetchVerificationCode();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void fetchVerificationCode()
    {
        FetchVerificationCodeTask task = new FetchVerificationCodeTask();

        String email = registerEmail.getText().toString();
        if (!email.matches(RegexConst.emailRegex))
        {
            Toast.makeText(this, "请输入正确的邮箱！", Toast.LENGTH_SHORT).show();
            return;
        }

        AsyncTask<String, Integer, Boolean> execute = task.execute(email);
        try
        {
            boolean response = execute.get(10, TimeUnit.SECONDS);
            if (response)
            {
                Toast.makeText(this, "已成功向您的邮箱 " + email + " 发送验证码！", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                openActivity(CheckVerificationActivity.class, bundle);
            } else
            {
                Toast.makeText(this, "向您的邮箱 " + email + " 发送验证码失败！", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e)
        {
            throw new TranslateException(ExceptionCode.TIMEOUT);
        }
    }
}