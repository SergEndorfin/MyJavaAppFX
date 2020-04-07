package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.*;
import sample.scenes.MarkerScene;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label login_label;

    @FXML
    private JFXTextArea login_field;

    @FXML
    private JFXButton login_btn;

    @FXML
    private JFXButton signup_btn;

    @FXML
    private Label wrongLogin_login;

    @FXML
    private Label wrongPass_login;

    @FXML
    private JFXPasswordField LoginPassword_field;

    @FXML
    private ImageView img1_red;

    @FXML
    void changeSceneButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/scenes/signUpScene.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //this line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    private DataBase dataBase = new DataBase();
    private Image imageRed = new Image("sample/sources/img1_red.png");
    public static User currentUser = null;

    @FXML
    void initialize() {

        login_btn.setOnAction(event -> {

            cleanBorders();
            cleanFields();

            if (login_field.getText().length() <= 2 && !login_field.getText().equals("")) {
                wrongLogin_login.setText("To small login:");
                login_field.setStyle("-fx-border-color: red");
                img1_red.setImage(null);
                return;
            } else if (LoginPassword_field.getText().length() <= 4 && !LoginPassword_field.getText().equals("")) {
                wrongPass_login.setText("No less 5 chars:");
                LoginPassword_field.setStyle("-fx-border-color: red");
                img1_red.setImage(null);
                return;
            }
            String loginText = login_field.getText().trim();
            String passText = LoginPassword_field.getText().trim();
            if (!loginText.equals("") && !passText.equals("")){
                loginUser(loginText, passText);
            }
            else {
                login_label.setText("Login");
                login_label.setStyle("-fx-text-fill: #f032fa");
                setPictureRed(null);
            }
        });

    }

    private void loginUser(String loginText, String passText) {
        passText = MD5.md5String(passText);
        try {
            dataBase.isConnected();
            ResultSet resultSet = dataBase.getUser(loginText, passText);

            int counter = 0;
            while (resultSet.next()){
                counter++;
            }
            if (counter > 0) {
                setPictureRed(null);
                System.out.println("Есть такой пользователь");
                currentUser = new User(loginText, passText);

                //Serialization: save login.
                LoginLogoutSerialization.loginSerialization(loginText, passText);

                ChangeScene changeScene = new ChangeScene();
                changeScene.changeSceneTo(login_btn, "/sample/scenes/menuScene.fxml");
            }
            else {
                login_label.setText("User doesn't exist. Sign Up, pls");
                setPictureRed(imageRed);
                login_label.setStyle("-fx-text-fill: red");
            }

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setPictureRed(Image image) {
        img1_red.setImage(image);
    }

    private void cleanBorders() {
        login_field.setStyle("");
        LoginPassword_field.setStyle("");
    }

    private void cleanFields() {
        wrongLogin_login.setText("");
        wrongPass_login.setText("");
    }

}
