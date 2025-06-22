import java.util.Scanner;
public class StudentManagementApp {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Student Management System");

        boolean running = true;
        while(running){
            try{
                displayMenu();
                System.out.println("Enter your choice (1-6):");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        manager.addStudent();
                        break;
                    case 2: 
                        manager.viewStudents();
                        break;
                    case 3:
                        manager.removeStudent();
                        break;
                    case 4:
                        manager.modifyStudent();
                        break;
                    case 5:
                        manager.searchStudent();
                        break;
                    case 6:
                        System.out.println("Thank you for using Student Management System!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice! please enter a number between (1-6)");
                    }
            }catch(NumberFormatException e){
                System.out.println("Error: Please enter a valid number for your choice");
            }catch(Exception e){
                System.out.println("An unexpected error occurred: "+e.getMessage());
            }

            if(running){
                System.out.println("\n Press Enter to continue............");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
    public static void displayMenu() {
        System.out.println("\n ===== Student Management System Menu =====");
        System.out.println("1. Add Students");
        System.out.println("2. View All Student");
        System.out.println("3. Remove Student");
        System.out.println("4. Modify Student");
        System.out.println("5. Search Student");
        System.out.println("6. Exit....");
        System.out.println("========================");
    }
}