package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.*;
import sample.scenes.MarkerScene;
import sample.scenes.Scenes;

public class UserCabinetController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextArea loginNew_field;

    @FXML
    private JFXTextArea EmailNew_field;

    @FXML
    private JFXButton changeData_btn;

    @FXML
    private Label wrongLogin_UCab;

    @FXML
    private Label wrongEmail_UCab;

    @FXML
    private Label wrongPass_UCab;

    @FXML
    private JFXPasswordField passwordNew_field;

    @FXML
    private JFXButton logOut_btn;

    @FXML
    private Label userCabinet_label;

    @FXML
    private JFXButton backFromUserCabinet_btn;

    DataBase dataBase = new DataBase();
    User checkInUser = LoginController.currentUser;
    ChangeScene changeScene = new ChangeScene();

    @FXML
    void changeSceneButtonPushed(ActionEvent event) throws IOException {
        //logout
        LoginLogoutSerialization.logoutSerialization();
        //new scene
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/scenes/loginScene.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //this line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void initialize() {

        changeData_btn.setOnAction(event -> {

            cleanBorders();
            cleanFields();

            if (loginNew_field.getText().length() <= 2 && !loginNew_field.getText().equals("")) {
                wrongLogin_UCab.setText("To small login:");
                wrongLogin_UCab.setStyle("-fx-text-fill: red");
                loginNew_field.setStyle("-fx-border-color: red");
                return;
            } else if ((!EmailNew_field.getText().contains("@") || !EmailNew_field.getText().contains(".")
                       || EmailNew_field.getText().contains(" ")) && !EmailNew_field.getText().equals("")) {
                wrongEmail_UCab.setText("Invalid email:");
                wrongEmail_UCab.setStyle("-fx-text-fill: red");
                EmailNew_field.setStyle("-fx-border-color: red");
                return;
            } else if (passwordNew_field.getText().length() <= 4 && !passwordNew_field.getText().equals("")) {
                wrongPass_UCab.setText("No less 5 chars:");
                wrongPass_UCab.setStyle("-fx-text-fill: red");
                passwordNew_field.setStyle("-fx-border-color: red");
                return;
            }

            try {
                dataBase.getDataFromUsersTable(checkInUser);
                if (!loginNew_field.getText().equals("")) {
                    String login = loginNew_field.getText();
                    dataBase.updateLoginInUsersTable(checkInUser, login);
                    checkInUser.setLogin(login);
                    LoginLogoutSerialization.loginSerialization(login, checkInUser.getPassword());
                    wrongLogin_UCab.setText("Login changed");
                    wrongLogin_UCab.setStyle("-fx-text-fill: green");
                }
                if (!EmailNew_field.getText().equals("")) {
                    dataBase.updateEmailInUsersTable(checkInUser, EmailNew_field.getText());
                    wrongEmail_UCab.setText("Email changed");
                    wrongEmail_UCab.setStyle("-fx-text-fill: green");
                }
                if (!passwordNew_field.getText().equals("")) {
                    String pass = MD5.md5String(passwordNew_field.getText());
                    dataBase.updatePasswordInUsersTable(checkInUser, pass);

                    checkInUser.setPassword(pass);
                    LoginLogoutSerialization.loginSerialization(checkInUser.getLogin(), pass);

                    wrongPass_UCab.setText("Password changed");
                    wrongPass_UCab.setStyle("-fx-text-fill: green");
                }

                if (loginNew_field.getText().equals("") && EmailNew_field.getText().equals("") && passwordNew_field.getText().equals("")) {
                    userCabinet_label.setText("User Cabinet");
                    userCabinet_label.setStyle("-fx-text-fill: #f032fa");
                } else {
                    userCabinet_label.setText("Change successful");
                    userCabinet_label.setStyle("-fx-text-fill: #5cf7be");
                }
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        });

        backFromUserCabinet_btn.setOnAction(event -> {
            try {
                if (MarkerScene.getMarkerScene().equals(Scenes.LAST_NEWS_SCENE))
                    changeScene.changeSceneTo(backFromUserCabinet_btn, "/sample/scenes/lastNewsScene.fxml");
                else if (MarkerScene.getMarkerScene().equals(Scenes.ADD_NEWS_SCENE))
                    changeScene.changeSceneTo(backFromUserCabinet_btn, "/sample/scenes/addNewsScene.fxml");
                else if (MarkerScene.getMarkerScene().equals(Scenes.NEWS_READER_SCENE))
                    changeScene.changeSceneTo(backFromUserCabinet_btn, "/sample/scenes/news_ReaderScene.fxml");
                else if (MarkerScene.getMarkerScene().equals(Scenes.MENU_SCENE))
                    changeScene.changeSceneTo(backFromUserCabinet_btn, "/sample/scenes/menuScene.fxml");
                else if (MarkerScene.getMarkerScene().equals(Scenes.SHORTER_LINK_SCENE))
                    changeScene.changeSceneTo(backFromUserCabinet_btn, "/sample/scenes/shorterLinkScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void cleanBorders() {
        loginNew_field.setStyle("");
        EmailNew_field.setStyle("");
        passwordNew_field.setStyle("");
    }

    private void cleanFields() {
        wrongLogin_UCab.setText("");
        wrongEmail_UCab.setText("");
        wrongPass_UCab.setText("");
    }
}
