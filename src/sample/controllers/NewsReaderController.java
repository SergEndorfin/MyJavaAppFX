package sample.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sample.DataBase;
import sample.scenes.MarkerScene;
import sample.scenes.Scenes;

public class NewsReaderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label newsReader_label;

    @FXML
    private JFXButton userCabinetFromNewsReader_btn;

    @FXML
    private JFXButton newsReaderAddNews_btn;

    @FXML
    private JFXButton backFromNewsReader_btn;

    @FXML
    private Label titleNewsReade;

    @FXML
    private Text contentNewsReader_field;

    DataBase dataBase = new DataBase();
    ChangeScene changeScene = new ChangeScene();

    public static int getArticleIdFromNode;

    @FXML
    void initialize() {

        ResultSet resultSet = null;
        try {
            resultSet = dataBase.getArticleFromArticleTable(getArticleIdFromNode);
            while (resultSet.next()){
                titleNewsReade.setText(resultSet.getString("title"));
                contentNewsReader_field.setText(resultSet.getString("text"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        MarkerScene.setMarkerScene(Scenes.NEWS_READER_SCENE);

        userCabinetFromNewsReader_btn.setOnAction(event -> {
            try {
                changeScene.changeSceneTo(userCabinetFromNewsReader_btn, "/sample/scenes/userCabinetScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        backFromNewsReader_btn.setOnAction(event -> {
            try {
                changeScene.changeSceneTo(backFromNewsReader_btn, "/sample/scenes/lastNewsScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        newsReaderAddNews_btn.setOnAction((event -> {
            try {
                changeScene.changeSceneTo(newsReaderAddNews_btn, "/sample/scenes/addNewsScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

    }
}
