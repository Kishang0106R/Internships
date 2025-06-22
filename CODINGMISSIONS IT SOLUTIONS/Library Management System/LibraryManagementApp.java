import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagementApp {
    public static void main(String[] args) {
        LibraryManager library = new LibraryManager();
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        while(true){
            if(!loggedIn){
                System.out.println("\n Library Management System");
                System.out.println("1. Login");
                System.out.println("2. Register User");
                System.out.println("3. Exit");
                System.out.println("Enter your choice: ");

                try{
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            loggedIn = library.login();
                            break;
                    
                        case 2:
                        library.registerUser();
                            break;
                        case 3:
                            System.out.println("Exiting.... Thank you!");
                            scanner.close();
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice! Please enter a number between 1-3.");
                    }
                }catch(InputMismatchException e){
                    System.out.println("Invalid input! Please enter a valid number. ");
                    scanner.nextLine();
                }
            }else{
                System.out.println("\n Library Management System");
                System.out.println("1. Add Books");
                System.out.println("2. View All Books");
                System.out.println("3. Search for a Book");
                System.out.println("4. Browse by Category");
                System.out.println("5. Issue Book");
                System.out.println("6. Return Book");
                System.out.println("7. View Issued Books");
                System.out.println("8. View My Books");
                System.out.println("9. Remove Books");
                System.out.println("10. Register new User");
                System.out.println("11. Logout");
                System.out.println("12. Exit");
                System.out.println("Enter your choice: ");

                try{
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> library.addBook();
                        case 2 -> library.viewBooks();
                        case 3 -> library.searchBook();
                        case 4 -> library.browseByCategory();
                        case 5 -> library.issueBook();
                        case 6 -> library.returnBook();
                        case 7 -> library.viewIssuedBooks();
                        case 8 -> library.viewMyBooks();
                        case 9 -> library.removeBook();
                        case 10 -> library.registerUser();
                        case 11 -> {
                            System.out.println("Logging out....");
                            loggedIn = false;
                        }
                        case 12 -> {
                            System.out.println("Exiting... Thank you!");
                            scanner.close();
                            System.exit(0);
                        }
                        default -> System.out.println("Invalid choice! Please enter a number between 1-12.");
                    }
                }catch(InputMismatchException e){
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.nextLine();
                }
            }
        }
    }
}