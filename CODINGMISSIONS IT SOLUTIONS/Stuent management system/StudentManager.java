import java.io.*;
import java.util.*;

public class StudentManager {
    private List<Student> student;

    private static final String File_name = "students.dat";

    private Scanner scanner;

    public StudentManager(){
        student = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadStudent();
    }

    private void loadStudent(){
        File file = new File(File_name);
        if(!file.exists()){
            System.out.println("No existing data found. Starting fresh");
            return;
        }

        try(ObjectInputStream  ois = new ObjectInputStream(new FileInputStream(File_name))){
            student = (ArrayList<Student>) ois.readObject();
            System.out.println("Data Loaded successfully");
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e);
        }
    }

    private void saveStudent(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(File_name))){
            oos.writeObject(student);
            System.out.println("Data save successfully");
        }catch(IOException e){
            System.out.println("Error saving data:"+e.getMessage());
        }
    }

    public void addStudent(){
        try{
            System.out.println("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            if(findStudentByID(id) != null){
                System.out.println("Error: Student with ID "+ id+ "already exist");
                return;
            }

            System.out.println("Enter Student Name: ");
            String name = scanner.nextLine();

            System.out.println("Enter Student Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            if(age <=0 || age >120){
                System.out.println("Error: Please enter a valid age (1-120)");
                return;
            }

            student.add(new Student(id, name, age));
            saveStudent();
            System.out.println("Student added successfully");

        }catch(NumberFormatException e){
            System.out.println("Error: Please enter a valid number for ID/Age");
        }catch(Exception e){
            System.out.println("Error adding student: "+ e.getMessage());
        }
    }

    public Student findStudentByID(int id){
        for(Student student : student){
            if(student.getID() == id){
                return student;
            }
        }
        return null;
    }

    public void viewStudents(){
        if(student.isEmpty()){
            System.out.println("No student found in the system");
            return;
        }
        System.out.println("\n===== Student List =====");
        for(Student student : student){
            System.out.println(student);
        }
        System.out.println("==========================");
    }

    public void removeStudent(){
        try{
            System.out.println("Enter Student ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine());
            Student studentToRemove = findStudentByID(id);
            if(studentToRemove == null){
                System.out.println("Student not found with ID: "+id);
                return;
            }
            student.remove(studentToRemove);
            saveStudent();
            System.out.println("Student with ID "+id+" removed successfully");
        }catch(NumberFormatException e){
            System.out.println("Error: Please enter a valid ID number");
        }catch(Exception e){
            System.out.println("Error removing student: "+ e.getMessage());
        }
    }

    public void modifyStudent(){
        try{
            System.out.println("Enter Student ID to Modify: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student studentToModify = findStudentByID(id);
            if(studentToModify == null){
                System.out.println("Student not found with ID: "+id);
                return;
            }
            System.out.println("Enter new Name (current: "+studentToModify.getName()+"):");
            String name = scanner.nextLine();

            System.out.println("Enter new Age (current: "+studentToModify.getAge()+"):");
            int age = Integer.parseInt(scanner.nextLine());

            if(age<= 0 || age>120){
                System.out.println("Error: Please enter a valid age (1-120)");
                return;
            }
            studentToModify.setName(name);
            studentToModify.setAge(age);
            saveStudent();
            System.out.println("Student details updated successfully");
        }catch(NumberFormatException e){
            System.out.println("Error: Please enter a valid number for ID/Age");
        }catch(Exception e){
            System.out.println("Error modifying student: "+e.getMessage());
        }
    }

    public void searchStudent(){
        System.out.println("Enter Student ID or Name to search: ");
        String query = scanner.nextLine().trim();

        List<Student>foundStudents = new ArrayList<>();

        for(Student student : student){
            if(String.valueOf(student.getID()).equals(query) || student.getName().toLowerCase().contains(query.toLowerCase())){
                foundStudents.add(student);
            }
        }

        if(foundStudents.isEmpty()){
            System.out.println("No student found matching: "+ query);
        }else{
            System.out.println("\n====== Search Result =====");
            for(Student student : foundStudents){
                System.out.println(student);
            }
        }
    }




}
