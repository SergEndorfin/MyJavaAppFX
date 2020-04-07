package sample.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.LoginLogoutSerialization;
import sample.scenes.MarkerScene;
import sample.scenes.Scenes;

public class MenuSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label menu_label;

    @FXML
    private JFXButton userCabinetFromMenu_btn;

    @FXML
    private JFXButton readNews_btn;

    @FXML
    private JFXButton logout_btn;

    @FXML
    private JFXButton shortLink_btn;

    ChangeScene changeScene = new ChangeScene();


    @FXML
    void initialize() {
        MarkerScene.setMarkerScene(Scenes.MENU_SCENE);

        shortLink_btn.setOnAction(event -> {
            try {
                changeScene.changeSceneTo(shortLink_btn, "/sample/scenes/shorterLinkScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        userCabinetFromMenu_btn.setOnAction(event -> {
            try {
                changeScene.changeSceneTo(userCabinetFromMenu_btn, "/sample/scenes/userCabinetScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        readNews_btn.setOnAction(event -> {
            try {
                changeScene.changeSceneTo(readNews_btn, "/sample/scenes/lastNewsScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logout_btn.setOnAction(event -> {
            try {
                LoginLogoutSerialization.logoutSerialization();
                changeScene.changeSceneTo(logout_btn, "/sample/scenes/loginScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
