package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.DataBase;
import sample.scenes.MarkerScene;
import sample.scenes.Scenes;

public class AddNewsSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label addNews_label;

    @FXML
    private Label addArticleSuccess;

    @FXML
    private JFXButton userCabinetFromAddNews_btn;

    @FXML
    private JFXButton addNews_btn;

    @FXML
    private JFXButton backFromAddNews_btn;

    @FXML
    private JFXTextArea enterIntro_field;

    @FXML
    private JFXTextArea enterText_field;

    @FXML
    private JFXTextArea enterTitle_field;

    @FXML
    private Label noLessTitle;

    @FXML
    private Label noLessIntro;

    @FXML
    private Label noLessText;

    ChangeScene changeScene = new ChangeScene();
    DataBase dataBase = new DataBase();


    @FXML
    void initialize() {

        userCabinetFromAddNews_btn.setOnAction(event -> {
            MarkerScene.setMarkerScene(Scenes.ADD_NEWS_SCENE);
            try {
                changeScene.changeSceneTo(userCabinetFromAddNews_btn, "/sample/scenes/userCabinetScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        backFromAddNews_btn.setOnAction(event -> {
            try {
                if (MarkerScene.getMarkerScene().equals(Scenes.LAST_NEWS_SCENE))
                    changeScene.changeSceneTo(backFromAddNews_btn, "/sample/scenes/lastNewsScene.fxml");
                else if (MarkerScene.getMarkerScene().equals(Scenes.NEWS_READER_SCENE))
                    changeScene.changeSceneTo(backFromAddNews_btn, "/sample/scenes/news_ReaderScene.fxml");
                else
                    changeScene.changeSceneTo(backFromAddNews_btn, "/sample/scenes/lastNewsScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addNews_btn.setOnAction(event -> {

            clearEnterFields();

            if (enterTitle_field.getText().length() <= 5 && !enterTitle_field.getText().equals("")) {
                noLessTitle.setText("No less 5 chars");
                enterTitle_field.setStyle("-fx-border-color: red");
                return;
            } else if (enterIntro_field.getText().length() <= 5 && !enterTitle_field.getText().equals("")) {
                noLessIntro.setText("No less 5 chars:");
                enterIntro_field.setStyle("-fx-border-color: red");
                return;
            } else if (enterText_field.getText().length() <= 9 && !enterIntro_field.getText().equals("")) {
                noLessText.setText("No less 9 chars:");
                enterText_field.setStyle("-fx-border-color: red");
                return;
            }
            if (!enterTitle_field.getText().equals("") && !enterIntro_field.getText().equals("")
                    && !enterText_field.getText().equals("")) {
                try {
                    dataBase.isConnected();
                    dataBase.addNewArticle(enterTitle_field.getText(), enterIntro_field.getText(), enterText_field.getText(), 0);
                    clearTextInFields();
                    addArticleSuccess.setText("Your article added. Success!");
                    addArticleSuccess.setStyle("-fx-text-fill: green");

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                addArticleSuccess.setStyle("-fx-text-fill: red");
                addArticleSuccess.setText("Something wrong");
            }

        });

    }

    private void clearEnterFields() {
        enterTitle_field.setStyle("");
        enterIntro_field.setStyle("");
        enterText_field.setStyle("");
        noLessTitle.setText("");
        noLessIntro.setText("");
        noLessText.setText("");
    }

    private void clearTextInFields() {
        enterTitle_field.setText("");
        enterIntro_field.setText("");
        enterText_field.setText("");
    }

}
