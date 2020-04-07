package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene {

    public void changeSceneTo(Node event, String newScene) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(newScene));
        Scene tableViewScene = new Scene(tableViewParent);
        //this line gets the Stage information
        Stage window = (Stage)((Node)event).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

}
