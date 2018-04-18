package popovvad.findme;


import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


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

    public Boolean login () throws ExecutionException, InterruptedException {
        if (!(loginControl())) {
            return false;
        }
        ServerInteraction ServerInteractionLog = new ServerInteraction("http://popovvad.ru/login.php",
                "{\"username\" " + ":\"" + getUsername() + "\", \"password\" :" + "\"" + getUserpassword() + "\"" + "}",getContext());
        ServerInteractionLog.execute();
        String response = ServerInteractionLog.get();
        Boolean returnable = true;
        switch (response){
            case "failed" : returnable = false;
                userMessage("Пользователь с таким именем не найден");
                break;
            case "successful" : returnable = true;
                break;
            case "dbaseError" : returnable = false;
                userMessage("Ошибка подключения к базе данных");
                break;
            default: returnable = false;
                userMessage("Что то пошло не так");
        }

        return returnable;
    }
    public Boolean registration() throws IOException, ExecutionException, InterruptedException {
        if (!(loginControl())) {
            return false;
        }
        ServerInteraction ServerInteractionReg = new ServerInteraction("http://popovvad.ru/register.php",
                "{\"username\" " + ":\"" + getUsername() + "\", \"password\" :" + "\"" + getUserpassword() + "\"" + "}",getContext());
        ServerInteractionReg.execute();
        String response = ServerInteractionReg.get();
        Boolean returnable = true;
        switch (response){
            case "failed" : returnable = false;
            userMessage("Пользователь с таким именем уже существует");
            break;
            case "successful" : returnable = true;
                break;
            case "dbaseError" : returnable = false;
            userMessage("Ошибка подключения к базе данных");
                break;
                default: returnable = false;
                userMessage("Что то пошло не так");
        }

        return returnable;
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
