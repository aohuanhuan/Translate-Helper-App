package translate.com.translate_helper_app.model;

import translate.com.translate_helper_app.common.RestStatus;

/**
 * Created by Administrator on 2017/8/27.
 */
public class UserInfoRsp
{
    private RestStatus status;

    private BasicUserInfo userInfo;

    public RestStatus getStatus()
    {
        return status;
    }

    public void setStatus(RestStatus status)
    {
        this.status = status;
    }

    public BasicUserInfo getUserInfo()
    {
        return userInfo;
    }

    public void setUserInfo(BasicUserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof UserInfoRsp)) return false;

        UserInfoRsp that = (UserInfoRsp) o;

        if (getStatus() != that.getStatus()) return false;
        return getUserInfo() != null ? getUserInfo().equals(that.getUserInfo()) : that.getUserInfo() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getStatus() != null ? getStatus().hashCode() : 0;
        result = 31 * result + (getUserInfo() != null ? getUserInfo().hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "UserInfoRsp{" +
                "status=" + status +
                ", userInfo=" + userInfo +
                '}';
    }
}