package popovvad.findme;


import android.content.Context;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by Вадим on 19.03.2018.
 */

public class User {

    private String user = new String();
    private String password = new String();
    private Context context;

    User(String user, String password, Context context){
        this.context = context;
        setUser(user,password);
    }

    public void setUser(String user, String password){
        this.user = user; this.password = password;
    }
    public String getUsername(){
        return user;
    }
    public String getUserpassword(){
        return password;
    }

    public Context getContext() {
        return context;
    }

    public Boolean login () {
        if (!(loginControl())) {
            return false;
        }
        return true;
    }
    public Boolean registration() throws IOException {
        if (!(loginControl())) {
            return false;
        }
        ServerInteraction ServerInteractionLogin = new ServerInteraction("Popovvad.ru/login.php",
                "{“username”:”"+ getUsername() +"”, “password”:”"+getUserpassword()+"”}",getContext());
        ServerInteractionLogin.PostQuery();

        return true;
    }
    private Boolean loginControl(){
        if (getUsername().length()<=0){
            userMessage("Логин не может быть пустым");
          return false;
        }
        else if (getUsername().length()>15){
            userMessage("Логин не может быть больше 15 символов");
            return false;
        }
        else if (getUserpassword().length()<=0){
            userMessage("Пароль не может быть пустым");
            return false;
        }
        else if(getUserpassword().length()> 15){
            userMessage("Пароль не может быть больше 15 символов");
            return false;
        }
        return true;
    }
    private void userMessage(String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

}
