package popovvad.findme;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Вадим on 19.03.2018.
 */

public class User extends AppCompatActivity {

    private String user;
    private String password;

    User(String user, String password){
        setUser(user,password);
    }

    public void setUser(String user, String password){
        this.user = user; this.password = password;
    }
    public String getUsername(){
        return this.user;
    }
    public String getUserpassword(){
        return this.password;
    }

    public Boolean login () {
        if (!(loginControl())) {
            return false;
        }
        return true;
    }
    private Boolean loginControl(){
        if (user.length()<=0){
            userMessage("Логин не может быть пустым");
          return false;
        }
        else if (user.length()>10){
            userMessage("Логин не может быть больше 10 символов");
        }
        else if (password.length()<=0){
            userMessage("Пароль не может быть пустым");
            return false;
        }
        else if(password.length()> 10){
            userMessage("Пароль не может быть больше 10 символов");
            return false;
        }
        return true;
    }
    private void userMessage(String message){
        Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
    }

}
