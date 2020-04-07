package sample;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LoginLogoutSerialization {

    public static void loginSerialization(String loginText, String passText) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("user.saver");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(new UserSaverSerialization(loginText, passText));
        objectOutputStream.close();
    }

    public static void logoutSerialization() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("user.saver");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(new UserSaverSerialization("", ""));
        objectOutputStream.close();
    }
}
