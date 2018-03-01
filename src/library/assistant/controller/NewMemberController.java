/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import library.assistant.database.Database;
import library.assistant.model.Member;
import library.assistant.model.MemberDAO;

/**
 * FXML Controller class
 *
 * @author SPELL
 */
public class NewMemberController implements Initializable {

    @FXML
    private JFXTextField memberIDField;
    @FXML
    private JFXTextField memberNameField;
    @FXML
    private JFXTextField memberMobileField;
    @FXML
    private JFXTextField memberAddressField;
    @FXML
    private JFXButton memberSaveBtn;
    @FXML
    private JFXButton memberCancelBtn;
    
    MemberDAO memberDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void saveMember(ActionEvent event) throws SQLException {
        
        memberDAO = new MemberDAO();
        
        String memberID = memberIDField.getText();
        String memberName = memberNameField.getText();
        String memberMobile = memberMobileField.getText();
        String memberAddress = memberAddressField.getText();
        
        if(memberID.isEmpty() || memberName.isEmpty() || memberMobile.isEmpty() || memberAddress.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.show();
            return;
        }
        
        try {
            memberDAO.saveMember(new Member(memberID,memberName,memberMobile,memberAddress));
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText(null);
            alert.setContentText("Member Adding Success.");
            alert.show();
            
        } catch (SQLException ex) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("FAIL");
            alert.setHeaderText(null);
            alert.setContentText("Member Adding Fail.");
            alert.show();
        
    }
    }

    @FXML
    private void cancelMemberWindow(ActionEvent event) {
        
        Stage stage = (Stage) memberSaveBtn.getScene().getWindow();
        stage.close();
      
    }
    
}
