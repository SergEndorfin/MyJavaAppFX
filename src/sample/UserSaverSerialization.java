package sample;

import java.io.*;

public class UserSaverSerialization implements Serializable {

    private String login;
    private String password;

    public UserSaverSerialization(String login, String passText) {
        this.login = login;
        this.password = passText;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
