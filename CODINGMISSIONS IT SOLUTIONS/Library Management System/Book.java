
import java.io.Serializable;
import java.util.*;
public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;
    private String category;
    private Date issueDate;
    private int borrowerID;

    public Book(int id, String title, String author, String category){
        this.id=id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = false;
        this.issueDate = null;
        this.borrowerID = -1;
    }

    public int getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public boolean isIssued(){
        return isIssued;
    }

    public String getCategory(){
        return category;
    }

    public int getBorrowerID(){
        return borrowerID;
    }

    public Date getIssueDate(){
        return issueDate;
    }

    public void issueBook(int borrowerID){
        this.isIssued = true;
        this.issueDate = new Date();
        this.borrowerID = borrowerID;
    }

    public void returnBook(){
        this.isIssued = false;
        this.issueDate = null;
        this.borrowerID = -1;
    }

    public String toString(){
        return "ID: " +id +", Title: "+title+", Author: "+author+", Category: "+category+", Issued: "+(isIssued ? "yes" : "No");
    }
}