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
import translate.com.translate_helper_app.model.BasicUserInfo;
import translate.com.translate_helper_app.task.RegisterTask;

/**
 * Created by Administrator on 2017/8/23.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener
{

    private TextView back;
    private TextView more;
    private TextView title;

    private Button regButton;

    private EditText regEmail;
    private EditText regUsername;
    private EditText regPass;
    private EditText regMobile;
    private EditText regWorkPlace;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);

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

        regButton = (Button) findViewById(R.id.regButton);

        regEmail = (EditText) findViewById(R.id.regEmail);
        regUsername = (EditText) findViewById(R.id.regUsername);
        regPass = (EditText) findViewById(R.id.regPass);
        regMobile = (EditText) findViewById(R.id.regMobile);
        regWorkPlace = (EditText) findViewById(R.id.regWorkPlace);

        //注册界面只需要显示
        back.setText(getString(R.string.back));
        title.setText(getString(R.string.register));
        more.setText("");

        //设置监听
        back.setOnClickListener(this);
        regButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.regButton:
                register();
                break;
        }
    }

    private void register()
    {
        BasicUserInfo basicUserInfo = new BasicUserInfo();

        basicUserInfo.setEmail(regEmail.getText().toString());
        basicUserInfo.setUsername(regUsername.getText().toString());
        basicUserInfo.setPassword(regPass.getText().toString());
        basicUserInfo.setMobile(regMobile.getText().toString());
        basicUserInfo.setProvince("320100");
        basicUserInfo.setCity("320111");
        basicUserInfo.setWorkPlace("江苏省南京市雨花台华为南京研究所");

        Bundle bundle = getIntent().getExtras();
        String tokenId = bundle.getString("tokenId");

        RegisterTask registerTask = new RegisterTask();
        AsyncTask<Object, Void, Boolean> task = registerTask.execute(basicUserInfo, tokenId);

        try
        {
            boolean response = task.get(10, TimeUnit.SECONDS);
            if (response)
            {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();

                openActivity(LoginActivity.class);
            } else
            {
                Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        } catch (TimeoutException e)
        {
            e.printStackTrace();
        }
    }
}
