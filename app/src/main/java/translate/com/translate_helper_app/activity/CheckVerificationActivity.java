package translate.com.translate_helper_app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import translate.com.translate_helper_app.task.CheckVerificationCodeTask;

/**
 * Created by Administrator on 2017/8/18.
 */
public class CheckVerificationActivity extends BaseActivity implements View.OnClickListener
{
    private TextView back;
    private TextView more;
    private TextView title;

    private EditText registerCode;
    private Button next;

    private TextView emailAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_verification_code);
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

        registerCode = (EditText) findViewById(R.id.registerCode);
        next = (Button) findViewById(R.id.check_next);

        emailAccount = (TextView) findViewById(R.id.emailAccount);

        //注册界面只需要显示
        back.setText(getString(R.string.back));
        title.setText(getString(R.string.register));
        more.setText("");

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        emailAccount.setText(email);

        //设置监听
        back.setOnClickListener(this);
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
            case R.id.check_next:
                checkCode();
                break;
        }
    }

    /**
     * 校验验证码
     */
    private void checkCode()
    {
        CheckVerificationCodeTask task = new CheckVerificationCodeTask();

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        String registerCode = this.registerCode.getText().toString();
        if (!registerCode.matches(RegexConst.verificationCodeRegex))
        {
            Toast.makeText(this, "请输入6位数字验证码！", Toast.LENGTH_SHORT).show();
            return;
        }

        AsyncTask<String, Integer, String> execute = task.execute(email, registerCode);

        try
        {
            String tokenId = execute.get(10, TimeUnit.SECONDS);
            //服务器验证码校验成功
            if (!"".equals(tokenId))
            {
                Toast.makeText(this, "验证码校验成功，返回当前的tokenId为 " + tokenId, Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e)
        {
            new TranslateException(ExceptionCode.TIMEOUT);
        }
    }
}