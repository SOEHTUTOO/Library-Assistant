/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import library.assistant.model.Book;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import library.assistant.database.Database;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import library.assistant.model.BookDAO;

/**
 * FXML Controller class
 *
 * @author SPELL
 */
public class NewbookController implements Initializable {

    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField authorField;
    @FXML
    private JFXTextField publisherField;
    @FXML
    private JFXButton saveBtn;
    
    BookDAO bookDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cancelWindow(ActionEvent event) {
        Stage newBookStage = (Stage) cancelBtn.getScene().getWindow();
        newBookStage.close();
    }

    @FXML
    private void saveBook(ActionEvent event) throws SQLException{
        
        bookDAO = new BookDAO();
        
        String bookID = idField.getText();
        String bookTitle = titleField.getText();
        String bookAuthor = authorField.getText();
        String bookPublisher = publisherField.getText();
        
        if(bookID.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }
        
        try {
            bookDAO.saveBook(new Book(bookID,bookTitle,bookAuthor,bookPublisher));
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Book Adding Success.");
            alert.show();
            
        } catch (SQLException ex) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("FAIL");
            alert.setHeaderText(null);
            alert.setContentText("Book Adding Fail.");
            alert.show();
            
            
        }
        
    }

}
