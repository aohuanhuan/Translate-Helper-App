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

    private int[] pics = new int[]{R.mipmap.bluetooth, R.mipmap.clock, R.mipmap.dog, R.mipmap.ps,
            R.mipmap.reader, R.mipmap.talk, R.mipmap.calc, R.mipmap.calendar, R.mipmap.camera};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        //加载用户基本信息
        BasicUserInfo basicUserInfo = loadBasicUserInfo();

        initViews(basicUserInfo);
    }

    private BasicUserInfo loadBasicUserInfo()
    {
        UserInfoTask userInfoTask = new UserInfoTask();

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");

        AsyncTask<String, Void, BasicUserInfo> execute = userInfoTask.execute(email);

        try
        {
            BasicUserInfo basicUserInfo = execute.get(10, TimeUnit.SECONDS);

            return basicUserInfo;
        } catch (Exception e)
        {
            throw new TranslateException(ExceptionCode.TIMEOUT);
        }
    }

    private void initViews(BasicUserInfo basicUserInfo)
    {
        /**
         * 获取组件
         */
        back = (TextView) findViewById(R.id.back);
        more = (TextView) findViewById(R.id.more);
        title = (TextView) findViewById(R.id.title);

        listView = (ListView) findViewById(R.id.userInfo);


        SimpleAdapter simpleAdapter = new SimpleAdapter(this, getData(basicUserInfo),
                R.layout.userinfo_item, new String[]{"userInfoIcon", "userInfoDes"}, new int[]{R.id.userInfoIcon, R.id.userInfoDes});
        listView.setAdapter(simpleAdapter);


        back.setText(getString(R.string.back));
        title.setText(getString(R.string.unserDetail));
        more.setText("");

        back.setOnClickListener(this);
    }

    private List<Map<String, Object>> getData(BasicUserInfo basicUserInfo)
    {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Field[] fields = basicUserInfo.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++)
        {
            Field.setAccessible(fields, true);
            if ("id".equals(fields[i].getName()) || "password".equals(fields[i].getName())
                    || "$change".equals(fields[i].getName()))
            {
                continue;
            }

            try
            {
                String value = (String) fields[i].get(basicUserInfo);

                HashMap<String, Object> map = new HashMap<>();

                map.put("userInfoIcon", pics[i]);
                map.put("userInfoDes", value);

                dataList.add(map);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return dataList;
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