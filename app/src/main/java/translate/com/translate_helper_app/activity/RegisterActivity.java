package translate.com.translate_helper_app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import translate.com.translate_helper_app.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView back;
    private TextView more;
    private TextView title;

    private TextView useLaw;
    private TextView privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState)
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

        useLaw = (TextView) findViewById(R.id.useLaw);
        privacy = (TextView) findViewById(R.id.privacy);

        //注册界面只需要显示
        back.setText(getString(R.string.back));
        title.setText(getString(R.string.register));
        more.setText("");

        //设置监听
        back.setOnClickListener(this);
        useLaw.setOnClickListener(this);
        privacy.setOnClickListener(this);
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
        }
    }
}