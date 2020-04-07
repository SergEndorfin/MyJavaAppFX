package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.LoginController;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String scene = "loginScene.FXML";
        File file = new File("user.saver");
        if (file.exists()){
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            UserSaverSerialization userSaver = (UserSaverSerialization) objectInputStream.readObject();
            if (!userSaver.getLogin().equals(""))
                LoginController.currentUser = new User(userSaver.getLogin(), userSaver.getPassword());
                scene = "menuScene.fxml";

            objectInputStream.close();
        }

        Parent root = FXMLLoader.load(getClass().getResource("scenes/" + scene));
        primaryStage.setTitle("Login App");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
