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
public class MemberDAO {

    public Member getMemberInfo(String memberID) throws SQLException {
        
        Connection connectionToDB = Database.getInstance().getConnection();
        
        String selectBookSql = "select * from libassistdb.members where id=?";
        PreparedStatement selectBookStmt = connectionToDB.prepareStatement(selectBookSql);
        selectBookStmt.setString(1, memberID);
        ResultSet result = selectBookStmt.executeQuery();
        
        Member memberInfo = null;
        
        if(result.next()) {
            
            String id = result.getString("id");
            String name = result.getString("name");
            String mobile = result.getString("mobile");
            String address = result.getString("address");
           
            
            memberInfo = new Member(id,name,mobile,address);
            
        }
        
        return memberInfo;
    }
    
    public void saveMember(Member member) throws SQLException{
        
        Connection connectionToDB = Database.getInstance().getConnection();
       
        String insertMemberSql = "insert into libassistdb.members (id,name,mobile,address) values (?,?,?,?)";
        PreparedStatement insertMemberStmt = connectionToDB.prepareStatement(insertMemberSql);
        insertMemberStmt.setString(1, member.getMemberID());
        insertMemberStmt.setString(2, member.getMemberName());
        insertMemberStmt.setString(3, member.getMemberMobile());
        insertMemberStmt.setString(4, member.getMemberAddress());
       insertMemberStmt.execute();
    }
    
    public ObservableList<Member> getMemberList () throws SQLException {
        
        Connection connectionToDB = Database.getInstance().getConnection();
    
        ObservableList<Member> memberList = FXCollections.observableArrayList();
        
        String getMemberListSql = "select * from libassistdb.members";
        
        try {
            Statement getMemberStmt = connectionToDB.createStatement();
            ResultSet memberResults = getMemberStmt.executeQuery(getMemberListSql);
            
            while (memberResults.next()){
            
                String memberID = memberResults.getString("id");
                String memberName = memberResults.getString("name");
                String memberMobile = memberResults.getString("mobile");
                String memberAddress = memberResults.getString("address");
                
                memberList.add(new Member(memberID, memberName, memberMobile, memberAddress));
                
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return memberList;
    }
    
}
