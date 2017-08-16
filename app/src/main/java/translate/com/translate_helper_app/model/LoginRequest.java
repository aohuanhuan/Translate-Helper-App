package translate.com.translate_helper_app.model;


import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/13.
 */
public class LoginRequest implements Serializable
{

    private String email;

    private String password;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof LoginRequest)) return false;

        LoginRequest that = (LoginRequest) o;

        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null)
            return false;
        return getPassword() != null ? getPassword().equals(that.getPassword()) : that.getPassword() == null;

    }

    @Override
    public int hashCode()
    {
        int result = getEmail() != null ? getEmail().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}