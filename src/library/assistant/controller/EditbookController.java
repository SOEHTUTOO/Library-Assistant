/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import library.assistant.model.Book;

/**
 * FXML Controller class
 *
 * @author SPELL
 */
public class EditbookController implements Initializable {
    

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
    @FXML
    private JFXButton cancelBtn;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
        
    }    


    @FXML
    private void cancelWindow(ActionEvent event) {
    }

    @FXML
    private void saveEditedBook(ActionEvent event) {
    }
    
    
    
}
