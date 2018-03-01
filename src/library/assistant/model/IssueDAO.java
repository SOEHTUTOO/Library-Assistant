/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static jdk.nashorn.internal.runtime.Debug.id;
import library.assistant.database.Database;

/**
 *
 * @author SPELL
 */
public class IssueDAO {

    public void saveIssueInfo(Issue issue) throws SQLException {
        
        Connection connectionToDB = Database.getInstance().getConnection();
        String saveIssueInfoSql = "insert into libassistdb.issue (book_id,member_id,issue_date,renew_count) values (?,?,curdate(),0)";
        PreparedStatement saveIssueStmt = connectionToDB.prepareStatement(saveIssueInfoSql);
        saveIssueStmt.setString(1, issue.getBookId());
        saveIssueStmt.setString(2, issue.getMemberId());
        saveIssueStmt.execute();
        
    }

    public Issue getIssueInfo(String searchText) throws SQLException {
        
        Connection connectionToDB = Database.getInstance().getConnection();
        
        String searchIssue = "select * from libassistdb.issue where book_id=?";
        PreparedStatement selectBookStmt = connectionToDB.prepareStatement(searchIssue);
        selectBookStmt.setString(1, searchText);
        ResultSet result = selectBookStmt.executeQuery();
        
        Issue issueInfo = null;
        
        if(result.next()) {
            
            String mID = result.getString("member_id");
            String bID = result.getString("book_id");
            Date issueDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");
            

            
            issueInfo = new Issue(bID,mID,issueDate,renewCount);
            
        }
        
        return issueInfo;
    }

    public void submitBook(String searchText) throws SQLException {
        
        Connection connectionToDB = Database.getInstance().getConnection();
        String submitSql = "delete from libassistdb.issue where book_id=?";
        PreparedStatement saveIssueStmt = connectionToDB.prepareStatement(submitSql);
        saveIssueStmt.setString(1, searchText);
        saveIssueStmt.execute();
        
    }

    public void renewCount(String text) throws SQLException {
        Connection connectionToDB = Database.getInstance().getConnection();
        String renewSql = "update libassistdb.issue set renew_count=renew_count+1 where book_id=?";
        PreparedStatement saveIssueStmt = connectionToDB.prepareStatement(renewSql);
        saveIssueStmt.setString(1, text);
        saveIssueStmt.execute();
    }
        
    
        
}
    
    
    

