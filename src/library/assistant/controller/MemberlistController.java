/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import library.assistant.model.Member;
import library.assistant.model.MemberDAO;

/**
 * FXML Controller class
 *
 * @author SPELL
 */
public class MemberlistController implements Initializable {

    @FXML
    private TableView<Member> memberListTable;
    @FXML
    private JFXButton viewMembersBtn;
    @FXML
    private JFXButton closeMembersListBtn;
    @FXML
    private TableColumn<Member, String> memberIDColumn;
    @FXML
    private TableColumn<Member, String> memberNameColumn;
    @FXML
    private TableColumn<Member, String> memberMobileColumn;
    @FXML
    private TableColumn<Member, String> memberAddressColumn;
    
    MemberDAO memberDAO;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            initColumn();
        } catch (SQLException ex) {
            Logger.getLogger(MemberlistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void initColumn () throws SQLException {
    
        memberDAO = new MemberDAO();
            
        ObservableList<Member> memberList = memberDAO.getMemberList();
        
        
        memberListTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        memberIDColumn.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        memberMobileColumn.setCellValueFactory(new PropertyValueFactory<>("memberMobile"));
        memberAddressColumn.setCellValueFactory(new PropertyValueFactory<>("memberAddress"));
        
        
        memberListTable.getItems().addAll(memberList);
            
        
        
    }

    @FXML
    private void viewMembersList(ActionEvent event) throws SQLException {
        

    }

    @FXML
    private void closeMemberList(ActionEvent event) {
        
        Stage stage = (Stage) closeMembersListBtn.getScene().getWindow();
        stage.close();
    }
    
}
