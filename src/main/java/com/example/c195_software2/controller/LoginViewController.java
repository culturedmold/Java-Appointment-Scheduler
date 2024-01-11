package com.example.c195_software2.controller;

import com.example.c195_software2.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;


/**
 * Controller for login-view.fxml
 */
public class LoginViewController extends Controller implements Initializable {
    Stage stage;
    String invalidCredentialsAlertTextEN = "Invalid login credentials. Please check your username and password, and try again.";
    String invalidCredentialsAlertTextFR = "Identifiants de connexion invalides. Veuillez vérifier votre nom d'utilisateur et votre mot de passe, puis réessayer.";

    @FXML
    private Label login_title;
    @FXML
    private AnchorPane login_pane;
    @FXML
    TextField username_field;
    @FXML
    private TextField password_field;
    @FXML
    private Label locale_label;
    @FXML
    private Button login_button;

    /**
     * Method to perform login
     * Displays a message if the authentication attempt is false, indicating that username or password is not valid
     * @param event Button click
     */
    @FXML
    private void loginAction(ActionEvent event) {

        String username = username_field.getText();
        String password = password_field.getText();

        // get DAOImpl authentication result
        Boolean authResult = Session.authenticateSession(username, password);

        Session.recordLoginActivity(username, LocalDateTime.now(ZoneOffset.UTC), authResult);

        // if authResult is true, open appointments-view.fxml
        if (authResult) {
            openNewView(event, "appointments-view.fxml", "Home");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            if (Session.getSessionLocale().getLanguage() == "fr") {
                alert.setContentText(invalidCredentialsAlertTextFR);
            } else {
                alert.setContentText(invalidCredentialsAlertTextEN);
            }
            alert.showAndWait();
        }
        System.out.println(authResult);
    }

    /**
     * Exit and close the application
     */
    @FXML
    private void exitApplication() {
        stage = (Stage) login_pane.getScene().getWindow();
        System.out.println("Exit Application");
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_title.setWrapText(true);

        // French
        String usernamePromptFr = "Nom d'utilisateur";
        String passwordPromptFr = "Mot de passe";
        String loginButtonFr = "Connectez-vous à votre compte";

        // Title translation here
        String loginTitleFr = "Planificateur de rendez-vous";

        // Locale label
        locale_label.setText(TimeZone.getDefault().toZoneId().toString());

        if (Objects.equals(Session.getSessionLocale().getLanguage(), "fr")) {
            username_field.setPromptText(usernamePromptFr);
            password_field.setPromptText(passwordPromptFr);
            login_button.setText(loginButtonFr);
            login_title.setText(loginButtonFr);
        }
    }
}
