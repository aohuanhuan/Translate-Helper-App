package translate.com.translate_helper_app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import translate.com.translate_helper_app.R;

/**
 * Created by Administrator on 2017/8/20.
 */
public class EmailUsedActivity extends BaseActivity implements View.OnClickListener
{
    private TextView back;
    private TextView more;
    private TextView title;

    private TextView emailUsedUsername;

    private Button emailUsedLogin;
    private Button registerByEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.email_uesd);

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

        emailUsedUsername = (TextView) findViewById(R.id.emailUsedUsername);

        emailUsedLogin = (Button) findViewById(R.id.emailUsedLogin);

        registerByEmail = (Button) findViewById(R.id.registerByEmail);

        //注册界面只需要显示
        back.setText(getString(R.string.back));
        title.setText(getString(R.string.register));
        more.setText("");

        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("userName");

        emailUsedUsername.setText(userName);

        //设置监听
        back.setOnClickListener(this);
        emailUsedLogin.setOnClickListener(this);
        registerByEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        Bundle bundle = getIntent().getExtras();
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            case R.id.emailUsedLogin:
                String email = bundle.getString("email");

                Bundle dataSend = new Bundle();
                dataSend.putString("email", email);

                openActivity(LoginActivity.class, dataSend);
                break;
            case R.id.registerByEmail:
                String tokenId = bundle.getString("tokenId");
                Bundle dataBundle = new Bundle();
                dataBundle.putString("tokenId", tokenId);

                openActivity(RegisterActivity.class, dataBundle);
                break;
            default:
        }
    }
}