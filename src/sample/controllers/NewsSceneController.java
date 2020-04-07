package sample.controllers;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.DataBase;
import sample.LoginLogoutSerialization;
import sample.UserSaverSerialization;
import sample.scenes.MarkerScene;
import sample.scenes.Scenes;

public class NewsSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lastNews_label;

    @FXML
    private JFXButton userCabinetFromNews_btn;

    @FXML
    private JFXButton addNews_btn;

    @FXML
    private JFXButton backAllNews_btn;

    @FXML
    private VBox paneVBox;

    ChangeScene changeScene = new ChangeScene();
    private DataBase dataBase = new DataBase();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = dataBase.getArticlesFromArticlesTable();

        while (resultSet.next()) {
            Node node = null;
            try {

                node = FXMLLoader.load(getClass().getResource("/sample/scenes/article.fxml"));

                Label title = (Label) node.lookup("#title");
                title.setText(resultSet.getString("title"));

                Label intro = (Label) node.lookup("#intro");
                intro.setText(resultSet.getString("intro"));

                int articleId = Integer.parseInt(resultSet.getString("id"));

                final Node nodeSet = node;
                node.setOnMouseEntered(mouseEvent -> {
                    nodeSet.setStyle("-fx-background-color: #343434");
                });
                node.setOnMouseExited(mouseEvent -> {
                    nodeSet.setStyle("-fx-background-color: #1f1f1f");
                });

                nodeSet.setOnMouseClicked (mouseEvent -> {
                    MarkerScene.setMarkerScene(Scenes.LAST_NEWS_SCENE);
                    NewsReaderController.getArticleIdFromNode = articleId;
                    try {
                        changeScene.changeSceneTo(nodeSet, "/sample/scenes/news_ReaderScene.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_RIGHT);
            paneVBox.getChildren().add(hBox);
            paneVBox.setSpacing(1);
        }

        backAllNews_btn.setOnAction(event -> {
            try {
                if (MarkerScene.getMarkerScene().equals(Scenes.SHORTER_LINK_SCENE))
                    changeScene.changeSceneTo(backAllNews_btn, "/sample/scenes/shorterLinkScene.fxml");
                else
                    changeScene.changeSceneTo(backAllNews_btn, "/sample/scenes/menuScene.fxml");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        userCabinetFromNews_btn.setOnAction(event -> {
            MarkerScene.setMarkerScene(Scenes.LAST_NEWS_SCENE);
            try {
                changeScene.changeSceneTo(userCabinetFromNews_btn, "/sample/scenes/userCabinetScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addNews_btn.setOnAction(event -> {
            MarkerScene.setMarkerScene(Scenes.LAST_NEWS_SCENE);
            try {
                changeScene.changeSceneTo(addNews_btn, "/sample/scenes/addNewsScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}