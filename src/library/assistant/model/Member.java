
package library.assistant.model;


public class Member {
    
    private String memberID;
    private String memberName;
    private String memberMobile;
    private String memberAddress;

    public Member(String memberID, String memberName, String memberMobile, String memberAddress) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.memberMobile = memberMobile;
        this.memberAddress = memberAddress;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }
    
    
    
}
