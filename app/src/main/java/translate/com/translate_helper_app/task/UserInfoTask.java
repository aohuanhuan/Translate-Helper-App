package translate.com.translate_helper_app.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import translate.com.translate_helper_app.R;
import translate.com.translate_helper_app.activity.BaseActivity;
import translate.com.translate_helper_app.activity.EmailUsedActivity;
import translate.com.translate_helper_app.activity.RegisterActivity;
import translate.com.translate_helper_app.common.AppConstant;
import translate.com.translate_helper_app.common.RestStatus;
import translate.com.translate_helper_app.model.BasicUserInfo;
import translate.com.translate_helper_app.model.UserInfoRsp;
import translate.com.translate_helper_app.utils.GsonUtils;
import translate.com.translate_helper_app.utils.HttpUtils;

/**
 * Created by Administrator on 2017/8/18.
 */
public class UserInfoTask extends AsyncTask<String, Void, UserInfoRsp>
{
    private BaseActivity baseActivity;

    private String tokenId;

    private String email;

    private ListView listView;

    private boolean isMain;

    private int[] pics = new int[]{R.mipmap.bluetooth, R.mipmap.clock, R.mipmap.dog, R.mipmap.ps,
            R.mipmap.reader, R.mipmap.talk, R.mipmap.calc, R.mipmap.calendar, R.mipmap.camera};

    private String restUrl = AppConstant.BASE_URL.concat("/register/queryBindingUserInfo?email=");

    public UserInfoTask(BaseActivity baseActivity, String email, String tokenId)
    {
        this.baseActivity = baseActivity;
        this.email = email;
        this.tokenId = tokenId;
    }

    public UserInfoTask(BaseActivity baseActivity, ListView listView, boolean isMain)
    {
        this.baseActivity = baseActivity;
        this.listView = listView;
        this.isMain = isMain;
    }

    @Override
    protected UserInfoRsp doInBackground(String... params)
    {
        String email = params[0];
        restUrl = restUrl.concat(email);

        UserInfoRsp userInfoRsp = new UserInfoRsp();
        String response = null;
        try
        {
            response = HttpUtils.doGet(restUrl);
        } catch (Exception e)
        {
            userInfoRsp.setStatus(RestStatus.NETWORK_ERROR);
            userInfoRsp.setUserInfo(null);

            return userInfoRsp;
        }

        BasicUserInfo userInfo = GsonUtils.fromJson(response, BasicUserInfo.class);

        userInfoRsp.setStatus(RestStatus.SUCCESS);
        userInfoRsp.setUserInfo(userInfo);

        return userInfoRsp;
    }

    @Override
    protected void onPostExecute(UserInfoRsp userInfoRsp)
    {
        super.onPostExecute(userInfoRsp);

        if (RestStatus.NETWORK_ERROR == userInfoRsp.getStatus())
        {
            Toast.makeText(baseActivity, "获取用户信息失败，网络错误！", Toast.LENGTH_SHORT).show();
        } else
        {
            BasicUserInfo basicUserInfo = userInfoRsp.getUserInfo();

            //如果是登录界面
            if (isMain)
            {
                SimpleAdapter simpleAdapter = new SimpleAdapter(baseActivity, getData(basicUserInfo),
                        R.layout.userinfo_item, new String[]{"userInfoIcon", "userInfoDes"}, new int[]{R.id.userInfoIcon, R.id.userInfoDes});
                listView.setAdapter(simpleAdapter);
            } else
            {
                Bundle userInfo = new Bundle();
                userInfo.putString("email", email);
                userInfo.putString("tokenId", tokenId);

                //邮箱已经被使用
                if (null != basicUserInfo)
                {
                    userInfo.putString("userName", basicUserInfo.getUsername());
                    baseActivity.openActivity(EmailUsedActivity.class, userInfo);
                }
                //跳转到注册页面
                else
                {
                    baseActivity.openActivity(RegisterActivity.class, userInfo);
                }
            }
        }
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
}