/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.main;

import java.sql.SQLException;
import java.util.Optional;
import library.assistant.database.Database;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author SPELL
 */
public class LibraryAssistant extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
            
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to exit?");
                Optional<ButtonType> choice = alert.showAndWait();
                
                if(choice.get().equals(ButtonType.CANCEL)){
                    event.consume();
                } 
            }
        });
        
        stage.getIcons().add(new Image("/library/assistant/icon/book.png"));
        
        stage.setScene(scene);
        stage.setTitle("Library Assistant Program");
        stage.show();
        
        try {
            Database.getInstance();
        }catch(SQLException e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Connecting");
            alert.setContentText("Cannot Connect To The Database.");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
