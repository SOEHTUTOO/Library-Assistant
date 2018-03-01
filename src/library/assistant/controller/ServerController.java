/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SPELL
 */
public class ServerController implements Initializable {

    @FXML
    private TextField hostField;
    @FXML
    private Spinner<Integer> portSpinner;
    @FXML
    private TextField userFIeld;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    
    private Preferences prefs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        prefs = Preferences.userRoot().node("lbdb");
        String host = prefs.get("host", "localhost");
        String user = prefs.get("user", "user");
        String password = prefs.get("password", "admin");
        int port = prefs.getInt("port", 3306);
        
        SpinnerValueFactory integerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3320, port);
        portSpinner.setValueFactory(integerFactory);
        
        hostField.setText(host);
        userFIeld.setText(user);
        passwordField.setText(password);
        
    }    

    @FXML
    private void saveServerConfig(ActionEvent event) {
        
        String host = hostField.getText();
        String user = userFIeld.getText();
        String password = passwordField.getText();
        int port = portSpinner.getValue();
        
        prefs.put("host", host);
        prefs.put("user", user);
        prefs.put("password", password);
        prefs.putInt("port", port);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setContentText("Server Configuration successful. Please restart for it work.");
            alert.setHeaderText(null);
            Optional<ButtonType> choice = alert.showAndWait();
                
        if(choice.get().equals(ButtonType.OK)){
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
     
        
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
        
    }
    
}
