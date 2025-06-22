import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
    private int userID;
    private String name;
    private String role;
    private List<Integer> issueBookIDs;

    public User (int userID, String name, String role){
        this.userID = userID;
        this.name = name;
        this.role = role;
        this.issueBookIDs = new ArrayList<>();
    }

    public int getUserID(){
        return userID;
    }

    public String getName(){
        return name;
    }

    public String getRole(){
        return role;
    }

    public List<Integer> getIssuedBookIDs(){
        return issueBookIDs;
    }

    public void addIssueBook(int bookID){
        issueBookIDs.add(bookID);
    }

    public void removeIssuedBook(int bookID){
        issueBookIDs.remove(Integer.valueOf(bookID));
    }

    public boolean isAdmin(){
        return "admin".equalsIgnoreCase(role);
    }

    public String toString(){
        return "User ID: "+userID+", Name: "+name+", Role: "+ role+ ", Books Issued: "+ issueBookIDs.size();
    }
}