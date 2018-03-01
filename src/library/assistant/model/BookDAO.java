/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.assistant.database.Database;

/**
 *
 * @author SPELL
 */
public class BookDAO {
    
    public void saveBook(Book book) throws SQLException{
       
        Connection connectionToDB = Database.getInstance().getConnection();
        String insertBookSql = "insert into libassistdb.books (id,title,author,publisher) values (?,?,?,?)";
        PreparedStatement insertBookStmt = connectionToDB.prepareStatement(insertBookSql);
        insertBookStmt.setString(1, book.getBookID());
        insertBookStmt.setString(2, book.getBookTitle());
        insertBookStmt.setString(3, book.getBookAuthor());
        insertBookStmt.setString(4, book.getBookPublisher());
        insertBookStmt.execute();
    }
    
    public ObservableList<Book> getBookList () throws SQLException {
        
        Connection connectionToDB = Database.getInstance().getConnection();
    
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        
        String getBookListSql = "select * from libassistdb.books";
        
        try {
            Statement getBookStmt = connectionToDB.createStatement();
            ResultSet bookResults = getBookStmt.executeQuery(getBookListSql);
            
            while (bookResults.next()){
            
                String bookID = bookResults.getString("id");
                String bookTitle = bookResults.getString("title");
                String bookAuthor = bookResults.getString("author");
                String bookPublisher = bookResults.getString("publisher");
                boolean available = bookResults.getBoolean("is_Available");
                
                bookList.add(new Book(bookID,bookTitle,bookAuthor,bookPublisher,available));
                
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return bookList;
    }
    
    public void deleteBook(String bookID) throws SQLException {
        
        Connection connectionToDB = Database.getInstance().getConnection();
        
        String deleteBookSql = "delete from libassistdb.books where id=?";
        PreparedStatement insertBookStmt = connectionToDB.prepareStatement(deleteBookSql);
        insertBookStmt.setString(1, bookID);
        insertBookStmt.execute();
        
    }

    public Book getBookInfo(String bookID) throws SQLException{
        
        Connection connectionToDB = Database.getInstance().getConnection();
        
        String selectBookSql = "select * from libassistdb.books where id=?";
        PreparedStatement selectBookStmt = connectionToDB.prepareStatement(selectBookSql);
        selectBookStmt.setString(1, bookID);
        ResultSet result = selectBookStmt.executeQuery();
        
        Book bookInfo = null;
        
        if(result.next()) {
            
            String id = result.getString("id");
            String title = result.getString("title");
            String author = result.getString("author");
            String publisher = result.getString("publisher");
            boolean available = result.getBoolean("is_available");
//            String available = result.getBoolean("is_available")? "Available":"Not Available";
            
            bookInfo = new Book(id,title,author,publisher,available);
            
        }
        
        return bookInfo;
    }

    public void updateBook(boolean b, String bookId) throws SQLException {
        
        Connection connectionToDB = Database.getInstance().getConnection();
        
        String updateBookSql = "update libassistdb.books set is_available=? where id=?";
        PreparedStatement updateBookStmt = connectionToDB.prepareStatement(updateBookSql);
        updateBookStmt.setBoolean(1, b);
        updateBookStmt.setString(2, bookId);
        updateBookStmt.execute();
        
    }
    
  
    
}
