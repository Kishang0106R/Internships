import java.io.Serializable;
public class Student implements Serializable{

    private static final long serialVersionUID = 1L;
    private int id;
    private int age;
    private String name;
    public Student(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age  = age;
    }

    public int getID(){
        return id;
    }
    public int getAge(){
        return age;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name  = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String toString(){
        return "Student [ID: "+id+", Name: "+name+", Age: "+age+"]";
    }

    public static void main(String[] args) {
    }
}