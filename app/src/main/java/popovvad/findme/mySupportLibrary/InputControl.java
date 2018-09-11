package popovvad.findme.mySupportLibrary;

public abstract class InputControl {

    public static String loginControl(String username, String password) {
        if (username.length() <= 0) {
            return "Логин не может быть пустым";
        } else if (username.length() > 15) {
            return "Логин не может быть больше 15 символов";
        } else if (password.length() <= 0) {
            return "Пароль не может быть пустым";
        } else if (password.length() > 15) {
            return "Пароль не может быть больше 15 символов";
        }
        return "successful";
    }

    public static String regControl(String username, String password) {
        if (username.length() <= 0) {
            return "Введите логин и пароль и нажмите регистрация";
        } else if (username.length() > 15) {
            return "Логин не может быть больше 15 символов";
        } else if (password.length() <= 0) {
            return "Введите логин и пароль и нажмите регистрация";
        } else if (password.length() > 15) {
            return "Пароль не может быть больше 15 символов";
        }
        return "successful";
    }

}
