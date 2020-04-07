package sample.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.DataBase;
import sample.MD5;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label SignUp_label;

    @FXML
    private JFXTextArea signUpLogin_field;

    @FXML
    private JFXTextArea signUpEmail_field;

    @FXML
    private JFXButton signUp_btn;

    @FXML
    private JFXButton login_btn;

    @FXML
    private JFXCheckBox check_Box;

    @FXML
    private Hyperlink termsOfUse_link;

    @FXML
    private Label wrongLogin_SUp;

    @FXML
    private Label wrongEmail_SUp;

    @FXML
    private Label wrongPass_SUp;

    @FXML
    private Label termsUnsigned_SUp;

    @FXML
    private JFXPasswordField signUpPassword_field;

    @FXML
    private ImageView img1;

    private DataBase dataBase = new DataBase();
    private Image image = new Image("sample/sources/img1.png");

    @FXML
    void changeSceneButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/sample/scenes/loginScene.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        //this line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void initialize() {

        signUp_btn.setOnAction(event -> {

            cleanBorders();
            cleanFields();

            if (signUpLogin_field.getText().length() <= 2 && !signUpLogin_field.getText().equals("")) {
                wrongLogin_SUp.setText("To small login:");
                signUpLogin_field.setStyle("-fx-border-color: red");
                img1.setImage(null);
                return;
            } else if ((!signUpEmail_field.getText().contains("@") || !signUpEmail_field.getText().contains(".")
                        || signUpEmail_field.getText().contains(" ")) && !signUpEmail_field.getText().equals("")) {
                wrongEmail_SUp.setText("Invalid email:");
                signUpEmail_field.setStyle("-fx-border-color: red");
                img1.setImage(null);
                return;
            } else if (signUpPassword_field.getText().length() <= 4 && !signUpPassword_field.getText().equals("")) {
                wrongPass_SUp.setText("No less 5 chars:");
                signUpPassword_field.setStyle("-fx-border-color: red");
                img1.setImage(null);
                return;
            } else if (!check_Box.isSelected()) {
                termsUnsigned_SUp.setText("Accept User Agreement:");
                img1.setImage(null);
                return;
            }
            if (!signUpLogin_field.getText().equals("") && !signUpEmail_field.getText().equals("")
                    && !signUpPassword_field.getText().equals("") && check_Box.isSelected()) {
                String password = signUpPassword_field.getText();

                try {
                    dataBase.isConnected();
                    boolean isAuthorisation = dataBase.registrationUser(signUpLogin_field.getText(), signUpEmail_field.getText(), MD5.md5String(password));
                    if (isAuthorisation) {
                        cleanFields();
                        SignUp_label.setText("Sign Up Success! You can login");
                        SignUp_label.setStyle("-fx-text-fill: #5cf7be");
                        img1.setImage(image);
                    } else {
                        SignUp_label.setText("User exists, try another login!");
                        SignUp_label.setStyle("-fx-text-fill: red");
                        signUpLogin_field.setStyle("-fx-border-color: red");
                        img1.setImage(null);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                SignUp_label.setText("Sign Up");
                SignUp_label.setStyle("-fx-color-fill: #f032fa");
                img1.setImage(null);
            }
        });

    }

    private void cleanBorders() {
        signUpLogin_field.setStyle("");
        signUpEmail_field.setStyle("");
        signUpPassword_field.setStyle("");
    }

    private void cleanFields() {
        wrongLogin_SUp.setText("");
        wrongEmail_SUp.setText("");
        wrongPass_SUp.setText("");
        termsUnsigned_SUp.setText("");
    }

}