package translate.com.translate_helper_app.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import translate.com.translate_helper_app.R;
import translate.com.translate_helper_app.common.RegexConst;
import translate.com.translate_helper_app.exception.ExceptionCode;
import translate.com.translate_helper_app.exception.TranslateException;
import translate.com.translate_helper_app.task.LoginRestTask;
import translate.com.translate_helper_app.utils.SharePreferenceUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    private TextView back;
    private TextView more;
    private TextView title;

    private CheckBox savePassCheck;
    private TextView savePassword;
    private TextView forgetPassword;
    private TextView loginLaw2;

    private EditText emailET = null;
    private EditText passwordET = null;

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //设置主题
        setTheme(R.style.AppTheme);

        initViews();
    }

    /**
     * 既然冷启动那么慢，我们就在非用户主动kill进程或系统通知kill进程的其他情况下不再主动退出进程。
     * 那答案很简单了，就是在位于Activity栈底activity中Hook其返回键行为，保证用户点击返回键后不再退出app。
     * 在我们App里位于我们栈底的一定是我们的MainActivity，因为一系统行为都是由其向下衍生的。所以只需加入以下几句话
     */
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    /**
     * 初始化控件
     */
    private void initViews()
    {
        /**
         * 获取组件
         */
        back = (TextView) findViewById(R.id.back);
        more = (TextView) findViewById(R.id.more);
        title = (TextView) findViewById(R.id.title);
        savePassCheck = (CheckBox) findViewById(R.id.savePassCheck);
        savePassword = (TextView) findViewById(R.id.savePassword);
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        loginLaw2 = (TextView) findViewById(R.id.loginLaw2);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);

        //记住用户名和密码
        initLoginInfo();

        //登录界面只需要显示
        back.setText(getString(R.string.leave));
        title.setText(getString(R.string.login));
        more.setText(getString(R.string.register));

        /**
         * 设置监听
         */
        back.setOnClickListener(this);
        more.setOnClickListener(this);
        savePassword.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        loginLaw2.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        savePassCheck.setOnCheckedChangeListener(this);
    }

    /**
     * 如果之前有记住用户名和密码，那么此处直接获取用户名和密码信息
     */
    private void initLoginInfo()
    {
        String emailSaved = SharePreferenceUtil.getString(this, "email");
        String passwordSaved = SharePreferenceUtil.getString(this, "password");
        boolean isSavePassChecked = SharePreferenceUtil.getBoolean(this, "isChecked", false);
        if (null != emailSaved && null != passwordSaved)
        {
            emailET.setText(emailSaved);
            passwordET.setText(passwordSaved);
        }
        if (isSavePassChecked)
        {
            savePassCheck.setChecked(isSavePassChecked);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                moveTaskToBack(true);
                break;
            case R.id.more:
                startActivity(new Intent(this, FetchVerificationActivity.class));
                break;
            case R.id.savePassword:
                boolean checked = savePassCheck.isChecked();
                savePassCheck.setChecked(!checked);
                break;
            case R.id.forgetPassword:
                Toast.makeText(this, "跳转找回密码页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.loginLaw2:
                Toast.makeText(this, "跳转服务条款页面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login:
                login();
                break;
            default:
        }
    }

    /**
     * 登录
     */
    private void login()
    {
        //异步登录
        //获取用户输入的email和password
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        if (!email.matches(RegexConst.emailRegex))
        {
            Toast.makeText(this, "请输入正确的邮箱账号！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(password))
        {
            Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRestTask loginRestTask = new LoginRestTask();
        AsyncTask<String, Integer, Boolean> loginTask = loginRestTask.execute(email, password);
        try
        {
            boolean loginStatus = loginTask.get(10, TimeUnit.SECONDS);
            if (loginStatus)
            {
                Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("email", email);

                openActivity(MainActivity.class, bundle);
            } else
            {
                Toast.makeText(this, "登录失败，用户名或者密码错误！", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e)
        {
            Toast.makeText(this, "登录失败，网络不通！", Toast.LENGTH_SHORT).show();
            throw new TranslateException(ExceptionCode.TIMEOUT);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        if (b)
        {
            SharePreferenceUtil.putString(this, "email", emailET.getText().toString());
            SharePreferenceUtil.putString(this, "password", passwordET.getText().toString());
            SharePreferenceUtil.putBoolean(this, "isChecked", true);
        } else
        {
            if (SharePreferenceUtil.haveKey(this, "email"))
            {
                SharePreferenceUtil.remove(this, "email");
            }
            if (SharePreferenceUtil.haveKey(this, "password"))
            {
                SharePreferenceUtil.remove(this, "password");
            }
            if (SharePreferenceUtil.haveKey(this, "isChecked"))
            {
                SharePreferenceUtil.remove(this, "isChecked");
            }
        }
    }
}