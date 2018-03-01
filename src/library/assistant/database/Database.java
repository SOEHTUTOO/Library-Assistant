
package library.assistant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class Database {
    
    private Connection connectionToDB;
    
    private static Database database;
    
    private Database() throws SQLException {

        connect();
        createDB();
        createTable();
        
        
    }
    
    public static Database getInstance () throws SQLException {
        
        if(database==null){
            database = new Database();
        }
        return database;
    }
    
    public void connect() throws SQLException{
        
        Preferences prefs = Preferences.userRoot().node("lbdb");
        String host = prefs.get("host", "localhost");
        String user = prefs.get("user", "root");
        String password = prefs.get("password", "");
        int port = prefs.getInt("port", 3306);
    
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        connectionToDB = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/", user, password);
        
    }
    
    public void disconnect() throws SQLException{
        
        if(connectionToDB != null) {
            connectionToDB.close();
        }
    
    }
    
    public void createDB​() throws SQLException{
    
        String createDBSql = "create database if not exists libassistdb";
        Statement creatDBStmt = connectionToDB.createStatement();
        creatDBStmt.execute(createDBSql);
        
    }
    
    public void createTable () throws SQLException {
    
        String createBookTB = "create table if not exists libassistdb.books (id varchar(255) primary key,title varchar(255),author varchar(255),publisher varchar(255),is_available boolean default true)";
        
        String createMemberTB = "create table if not exists libassistdb.members (id varchar(255) primary key,name varchar(255),mobile varchar(255),address varchar(255))";
        
        String createIssueTB = "create table if not exists libassistdb.issue (book_id varchar(255), member_id varchar(255), issue_date date, renew_count int, foreign key (book_id) references books(id), foreign key (member_id) references members(id))";
        
        Statement createTBStmt = connectionToDB.createStatement();
        
        createTBStmt.execute(createBookTB);
        createTBStmt.execute(createMemberTB);
        createTBStmt.execute(createIssueTB);
    }

    public Connection getConnection(){
        
        return connectionToDB;
        
    }
    
}
