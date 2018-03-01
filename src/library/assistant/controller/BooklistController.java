/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import library.assistant.model.Book;
import library.assistant.model.BookDAO;

/**
 * FXML Controller class
 *
 * @author SPELL
 */
public class BooklistController implements Initializable {

    @FXML
    private TableView<Book> bookListTable;
    @FXML
    private JFXButton closeBookListBtn;
    @FXML
    private TableColumn<Book, String> bookIDColumn;
    @FXML
    private TableColumn<Book, String> bookTitleColumn;
    @FXML
    private TableColumn<Book, String> bookAuthorColumn;
    @FXML
    private TableColumn<Book, String> bookPublisherColumn;
    @FXML
    private JFXButton editBtn;
    @FXML
    private TableColumn<Book, Integer> bookAvaiColumn;
    @FXML
    private MenuItem deleteBookMenu;
    
    private BookDAO bookDAO;
    

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            initColumn();
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void initColumn() throws SQLException{
        
            bookDAO = new BookDAO();
            
            ObservableList<Book> bookList = bookDAO.getBookList();
        
        
        bookListTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        bookPublisherColumn.setCellValueFactory(new PropertyValueFactory<>("bookPublisher"));
        bookAvaiColumn.setCellValueFactory(new PropertyValueFactory<>("availableStatus"));
        
        
        
        
        bookListTable.getItems().addAll(bookList);
       
    
    }


    @FXML
    private void closeBookList(ActionEvent event) {
        
        Stage stage = (Stage) closeBookListBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void editBookInfo(ActionEvent event) {
        
        bookListTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        Book book;
        book = bookListTable.getSelectionModel().getSelectedItem();
        
        Stage newBookStage = new Stage();
        
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/library/assistant/view/editbook.fxml"));
            
            Scene scene = new Scene(root);
        
            newBookStage.setScene(scene);
            newBookStage.setTitle("Edit Book");
            newBookStage.show();
        
        
        } catch (IOException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteBook(ActionEvent event){
        
        Book selectedBook = bookListTable.getSelectionModel().getSelectedItem();
        
        if(selectedBook == null){
            
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book you want to delete.");
            alert.show();
            return;
        }
        
        try {
            
            bookDAO = new BookDAO();
            
            bookDAO.deleteBook(selectedBook.getBookID());
            bookListTable.getItems().remove(selectedBook);
            
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("SUCCESS");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Deleteing the book success.");
            successAlert.show();
            
            
        } catch (SQLException ex) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Can't Delete the book.");
            alert.show();
            
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
}
    

