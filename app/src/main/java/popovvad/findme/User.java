package popovvad.findme;


import android.content.Context;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


/**
 * Created by Вадим on 19.03.2018.
 */

public class User {

    private String user;
    private String password;
    private Context context;

    User(String user, String password, Context context){
        this.context = context;
        this.user = user;
        this.password = password;
    }

    public void setContext(Context context){
        this.context = context;
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

    public Boolean login () throws IOException, ExecutionException, InterruptedException {
        if (!(loginControl())) {
            return false;
        }
        ServerInteraction ServerInteractionLog = new ServerInteraction("http://popovvad.ru/authorization.php",
                "{\"username\" " + ":\"" + getUsername() + "\", \"password\" :" + "\"" + getUserpassword() + "\"" + "}","post");
        ServerInteractionLog.execute();
        String response = ServerInteractionLog.get();
        Boolean returnable;
        switch (response){
            case "failed_password" : returnable = false;
                Message.showMessage(getContext(),"Неверный пароль");
                break;
            case "failed_username" : returnable = false;
                Message.showMessage(getContext(),"Пользователь с таким именем не найден");
                break;
            case "successful" : returnable = true;
                break;
            case "dbaseError" : returnable = false;
                Message.showMessage(getContext(),"Ошибка подключения к базе данных");
                break;
            default: returnable = false;
                Message.showMessage(getContext(),"Что то пошло не так");
        }

        return returnable;
    }
    public Boolean registration() throws IOException, ExecutionException, InterruptedException {
        if (!(regControl())) {
            return false;
        }
        ServerInteraction ServerInteractionReg = new ServerInteraction("http://popovvad.ru/register.php",
                "{\"username\" " + ":\"" + getUsername() + "\", \"password\" :" + "\"" + getUserpassword() + "\"" + "}","post");
        ServerInteractionReg.execute();
        String response = ServerInteractionReg.get();
        Boolean returnable;
        switch (response){
            case "failed" : returnable = false;
                Message.showMessage(getContext(),"Пользователь с таким именем уже существует");
                break;
            case "successful" : returnable = true;
                break;
            case "dbaseError" : returnable = false;
                Message.showMessage(getContext(),"Ошибка подключения к базе данных");
                break;
            default: returnable = false;
                Message.showMessage(getContext(),"Что то пошло не так");
        }

        return returnable;
    }
    private Boolean loginControl(){
        if (getUsername().length()<=0){
            Message.showMessage(getContext(),"Логин не может быть пустым");
            return false;
        }
        else if (getUsername().length()>15){
            Message.showMessage(getContext(),"Логин не может быть больше 15 символов");
            return false;
        }
        else if (getUserpassword().length()<=0){
            Message.showMessage(getContext(),"Пароль не может быть пустым");
            return false;
        }
        else if(getUserpassword().length()> 15){
            Message.showMessage(getContext(),"Пароль не может быть больше 15 символов");
            return false;
        }
        return true;
    }

    private Boolean regControl(){
        if (getUsername().length()<=0){
            Message.showMessage(getContext(),"Для регистрации введите логин и пароль");
            return false;
        }
        else if (getUsername().length()>15){
            Message.showMessage(getContext(),"Логин не может быть больше 15 символов");
            return false;
        }
        else if (getUserpassword().length()<=0){
            Message.showMessage(getContext(),"Для регистрации введите логин и пароль");
            return false;
        }
        else if(getUserpassword().length()> 15){
            Message.showMessage(getContext(),"Пароль не может быть больше 15 символов");
            return false;
        }
        return true;
    }

}
