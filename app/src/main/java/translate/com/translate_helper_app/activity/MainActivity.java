package translate.com.translate_helper_app.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import translate.com.translate_helper_app.R;
import translate.com.translate_helper_app.exception.ExceptionCode;
import translate.com.translate_helper_app.exception.TranslateException;
import translate.com.translate_helper_app.model.BasicUserInfo;
import translate.com.translate_helper_app.model.UserInfoRsp;
import translate.com.translate_helper_app.task.UserInfoTask;

/**
 * Created by Administrator on 2017/8/18.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener
{
    private TextView back;
    private TextView more;
    private TextView title;

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        initViews();

        //加载用户基本信息
        loadBasicUserInfo();
    }

    private void loadBasicUserInfo()
    {
        UserInfoTask userInfoTask = new UserInfoTask(this, listView, true);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        userInfoTask.execute(email);
    }

    private void initViews()
    {
        /**
         * 获取组件
         */
        back = (TextView) findViewById(R.id.back);
        more = (TextView) findViewById(R.id.more);
        title = (TextView) findViewById(R.id.title);

        listView = (ListView) findViewById(R.id.userInfo);

        back.setText(getString(R.string.back));
        title.setText(getString(R.string.unserDetail));
        more.setText("");

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}