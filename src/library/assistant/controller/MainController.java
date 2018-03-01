/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.assistant.main.LibraryAssistant;
import library.assistant.model.Book;
import library.assistant.model.BookDAO;
import library.assistant.model.Issue;
import library.assistant.model.IssueDAO;
import library.assistant.model.Member;
import library.assistant.model.MemberDAO;

/**
 *
 * @author SPELL
 */
public class MainController implements Initializable {
    
    @FXML
    private JFXButton addBookBtn;
    @FXML
    private JFXButton addMemberBtn;
    @FXML
    private MenuItem closeMenu;
    @FXML
    private JFXButton viewBooksBtn;
    @FXML
    private JFXButton viewMembersBtn;
    @FXML
    private JFXTextField bookSearchField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text availableText;
    
    private BookDAO bookDAO;
    @FXML
    private JFXTextField memberSearchField;
    @FXML
    private Text memberNameText;
    @FXML
    private Text memberMobileText;
    @FXML
    private Text memberAddressText;
    @FXML
    private JFXButton issueBookBtn;
    
    private IssueDAO issueDAO;
    
    private MemberDAO memberDAO;
    
    @FXML
    private Text mNameText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text bookTitleText;
    @FXML
    private Text bookAuthorText;
    @FXML
    private Text bookPublisherText;
    @FXML
    private Text issueDateText;
    @FXML
    private Text renewCountText;
    @FXML
    private JFXTextField searchBookIDField;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton submissionBtn;
    @FXML
    private MenuItem serverItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        bookDAO = new BookDAO();
        issueDAO = new IssueDAO();
        memberDAO = new MemberDAO();
        
    }    

    @FXML
    private void openAddBookWindow(ActionEvent event) throws IOException {
        
        loadWindows("Add Book", "/library/assistant/view/newbook.fxml");
    }

    @FXML
    private void openAddMemberWindow(ActionEvent event) throws IOException {
        
      loadWindows("Add Member", "/library/assistant/view/newmember.fxml");   
         
    }

    @FXML
    private void closeWIndows(ActionEvent event) {
        
        Stage stage = (Stage) addBookBtn.getScene().getWindow();
            
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to exit?");
                Optional<ButtonType> choice = alert.showAndWait();
                
                if(choice.get().equals(ButtonType.OK)){
                    stage.close();
                } 
        }

    @FXML
    private void openBooksList(ActionEvent event) throws IOException {
        
        loadWindows("Book List", "/library/assistant/view/booklist.fxml");
        
    }

    @FXML
    private void openMembersList(ActionEvent event) throws IOException {
        
        loadWindows("Member List", "/library/assistant/view/memberlist.fxml");
        
    }

    @FXML
    private void searchBook(ActionEvent event) {
        
        titleText.setText("-");
        authorText.setText("_");
        availableText.setText("_");
        
        if(bookSearchField.getText().isEmpty()){
        
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please insert the book ID.");
            alert.setHeaderText(null);
            alert.show();
            return;
            
        }
        
        try {
            Book book = bookDAO.getBookInfo(bookSearchField.getText());
            
            if(book!=null){
            
            titleText.setText("Title - "+book.getBookTitle());
            authorText.setText("Author - "+book.getBookAuthor());
            String available = book.isAvailable()?"Available":"Not Available";
            availableText.setText("Status - "+available);
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void searchMember(ActionEvent event) {
        
        memberNameText.setText("-");
        memberMobileText.setText("_");
        memberAddressText.setText("_");
        
        if(memberSearchField.getText().isEmpty()){
        
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please insert the member ID.");
            alert.setHeaderText(null);
            alert.show();
            return;
            
        }
        
        try {
        
        Member member = memberDAO.getMemberInfo(memberSearchField.getText());
        if(member!=null){
            
            memberNameText.setText("Name - "+member.getMemberName());
            memberMobileText.setText("Mobile - "+member.getMemberMobile());
            memberAddressText.setText("Address - "+member.getMemberAddress());
        
        }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void issueBook(ActionEvent event) {
        
        String bookId = bookSearchField.getText();
        String memberId = memberSearchField.getText();
        
        if(bookId.isEmpty() || memberId.isEmpty()) {
            return;
        }
        
        try {
            Book book = bookDAO.getBookInfo(bookId);
            if(!book.isAvailable()){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText(null);
                alert.setContentText("This Book is already Issued.");
                alert.show();
            return;
            }
            
            issueDAO.saveIssueInfo(new Issue(bookId, memberId));
            bookDAO.updateBook(false,bookId);
            
            Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText(null);
                alert.setContentText("This book is issued with you.");
                alert.show();
          
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void searchIssueBookInfo(ActionEvent event) {
        
        String searchText = searchBookIDField.getText();
        
        if(searchText.isEmpty()){
            System.out.println("Please put the Book ID");
            return;
        }
        
        try {
            Issue issueInfo = issueDAO.getIssueInfo(searchText);
            
            if(issueInfo!=null){
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
            String dateStr = dateFormat.format(issueInfo.getIssueDate());
                
            issueDateText.setText(dateStr);
            renewCountText.setText("Count - "+issueInfo.getRenewCount());
            
            
            }
            
            Book book = bookDAO.getBookInfo(searchText);
            
            if(book!=null){
            
            bookTitleText.setText(book.getBookTitle());
            bookAuthorText.setText(book.getBookAuthor());
            bookPublisherText.setText(book.getBookPublisher());
            
            }
           
            Member member = memberDAO.getMemberInfo(issueInfo.getMemberId());
            
            if(member!=null){
           
            mNameText.setText(member.getMemberName());
            mMobileText.setText(member.getMemberMobile());
            mAddressText.setText(member.getMemberAddress());
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void submitBook(ActionEvent event) {
        
        String searchText = searchBookIDField.getText();
        
        if(searchText.isEmpty()){
            System.out.println("Please put the Book ID");
            return;
        }
        
            Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRM");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to submit this book?");
                Optional<ButtonType> choice = alert.showAndWait();
                
        if(choice.get().equals(ButtonType.OK)){
        
            try {
                
                issueDAO.submitBook(searchText);
                bookDAO.updateBook(true,searchText);   
                
            } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
    }

    @FXML
    private void renewBook(ActionEvent event) {
        
        try {
            issueDAO.renewCount(searchBookIDField.getText());
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void loadServerConfig(ActionEvent event) throws IOException {
        
        loadWindows("Server Config", "/library/assistant/view/server.fxml");
        
    }
    
    private void loadWindows (String title, String url) throws IOException{
    
        Stage stage = new Stage();
        
        Parent root = FXMLLoader.load(getClass().getResource(url));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    
    }
    
}
