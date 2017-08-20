package translate.com.translate_helper_app.common;

/**
 * Created by Administrator on 2017/8/18.
 */
public interface RegexConst
{
    /**
     * 邮箱正则表达式
     */
    String emailRegex = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

    /**
     * 6位数字验证码
     */
    String verificationCodeRegex = "^\\d{6}$";
}