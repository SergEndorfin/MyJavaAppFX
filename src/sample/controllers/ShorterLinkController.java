package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.DataBase;
import sample.scenes.MarkerScene;
import sample.scenes.Scenes;

public class ShorterLinkController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lastNews_label;

    @FXML
    private JFXButton userCabinetFromShLink_btn;

    @FXML
    private JFXButton readNews_btn;

    @FXML
    private JFXButton backFromShorterLink_btn;

    @FXML
    private VBox paneVBox;

    @FXML
    private JFXTextArea fullLinkEnter_field;

    @FXML
    private Label wrongEnterLink_label;

    @FXML
    private Label linkExists_label;

    @FXML
    private JFXButton shortLink_btn;

    @FXML
    private JFXTextArea shortLinkEnter_field;

    ChangeScene changeScene = new ChangeScene();
    DataBase dataBase = new DataBase();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        MarkerScene.setMarkerScene(Scenes.SHORTER_LINK_SCENE);

        userCabinetFromShLink_btn.setOnAction(event -> {
            try {
                changeScene.changeSceneTo(userCabinetFromShLink_btn, "/sample/scenes/userCabinetScene.fxml");
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

        backFromShorterLink_btn.setOnAction(event ->{
            try {
                changeScene.changeSceneTo(backFromShorterLink_btn, "/sample/scenes/menuScene.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        shortLink_btn.setOnAction(event -> {

            clearLabelAndFieldsBorder();
            String fullLink = fullLinkEnter_field.getText();
            String shortLink = shortLinkEnter_field.getText();
            //проверка полей на валидность:
            if (fullLink.equals("")) {
                wrongEnterLink_label.setText("Enter some link, pls:");
                fullLinkEnter_field.setStyle("-fx-border-color: red");
                return;
            }
            else if (fullLink.length() < 12 && fullLink.length() > 0) {
                wrongEnterLink_label.setText("Wrong link, try again, pls:");
                fullLinkEnter_field.setStyle("-fx-border-color: red");
                return;
            }
            else if (shortLink.equals("")) {
                linkExists_label.setText("You need to enter something here:");
                shortLinkEnter_field.setStyle("-fx-border-color: red");
                return;
            }
            else if (shortLink.length() > 15) {
                linkExists_label.setText("Max 15 chars:");
                shortLinkEnter_field.setStyle("-fx-border-color: red");
                return;
            }
            //добавление записи в таблицу
            try {
                boolean isLinkAdded = dataBase.addLinkToLinkSorterTable(fullLink, shortLink);
                if (!isLinkAdded) {
                    linkExists_label.setText("Link exists, try another one, pls!");
                    shortLinkEnter_field.setStyle("-fx-border-color: red");
                }
                else {
                    clearTextFields();
                    clearLabelAndFieldsBorder();
                    changeScene.changeSceneTo(shortLink_btn, "/sample/scenes/shorterLinkScene.fxml" );
                }
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        });

        addAllLinks();

    }


    private void clearLabelAndFieldsBorder() {
        shortLinkEnter_field.setStyle("");
        fullLinkEnter_field.setStyle("");

        wrongEnterLink_label.setText("");
        linkExists_label.setText("");
    }
    private void clearTextFields() {
        shortLinkEnter_field.setText("");
        fullLinkEnter_field.setText("");
    }

    private void addAllLinks() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dataBase.findAllShortLinks();
        while (resultSet.next()) {
            Node node = null;
            Hyperlink shortLink = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/sample/scenes/shortLinkNew.fxml"));

                shortLink = (Hyperlink) node.lookup("#short_link");
                shortLink.setText(resultSet.getString("short_link"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //выделяем фон при наведении курсора
            final Node nodeSet = node;
            if (node != null) {
                node.setOnMouseEntered(mouseEvent -> {
                    nodeSet.setStyle("-fx-background-color: #343434");
                });
                node.setOnMouseExited(mouseEvent -> {
                    nodeSet.setStyle("-fx-background-color: #1f1f1f");
                });
            }
            String fullLink = resultSet.getString("link");
            assert shortLink != null;
            shortLink.setOnMouseClicked(mouseEvent -> {
                    goLink(fullLink);
            });

            HBox hBox = new HBox();
            hBox.getChildren().addAll(node);
            hBox.setAlignment(Pos.BASELINE_LEFT);
            paneVBox.getChildren().add(hBox);
            paneVBox.setSpacing(1);
        }
    }

    private static void goLink(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
