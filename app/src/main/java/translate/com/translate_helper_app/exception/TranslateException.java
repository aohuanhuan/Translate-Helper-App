package translate.com.translate_helper_app.exception;

/**
 * Created by Administrator on 2017/8/18.
 */
public class TranslateException extends RuntimeException
{
    public TranslateException()
    {
    }

    public TranslateException(String message)
    {
        super(message);
    }

    public TranslateException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public TranslateException(Throwable cause)
    {
        super(cause);
    }
}