import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LibraryManager {
    private List<Book> books;
    private List<User> users;
    private User currentUser;
    private final String BOOK_FILE = "library_date.txt";
    private final String USERS_FILE = "user_date.txt";
    private final int LOAN_PERIOD_DAYS = 7;
    private final double FINE_PER_DAY = 1.0;
    private Scanner scanner;

    public LibraryManager() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadBooks();
        loadUsers();
    }

    public boolean login(){
        if(users.isEmpty()){
            users.add(new User(1, "Admin", "admin"));
            saveUsers();
        }
        System.out.println("Enter User ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine();

        for(User user : users){
            if(user.getUserID() == userID){
                currentUser = user;
                System.out.println("Welcome, "+user.getName()+ "("+user.getRole()+")");
                return true;
            }
        }
        System.out.println("User not found. Please register first.");
        return false;
    }

    public void registerUser(){
        if(currentUser != null && !currentUser.isAdmin()){
            System.out.println("Only admins can register new users.");
            return;
        }

        try{
            System.out.println("Enter user ID: ");
            int userId = scanner.nextInt();
            scanner.nextLine();

            for(User user: users){
                if(user.getUserID() == userId){
                    System.out.println("User ID already exists. Try another ID.");
                    return;
                }
            }
            System.out.println("Enter User name: ");
            String name = scanner.nextLine();

            System.out.println("Enter Role (admin/student): ");
            String role = scanner.nextLine().toLowerCase();

            if(!role.equals("admin") && !role.equals("student")){
                System.out.println("Invalid role. Setting as student by default. ");
                role = "student";
            }
            users.add(new User(userId, name, role));
            saveUsers();
            System.out.println("User registered successfully");
        }catch(InputMismatchException e){
            System.out.println("Invalid input! User ID must be a number.");
            scanner.nextLine();
        }
    }

    public void addBook(){
        if(currentUser == null || !currentUser.isAdmin()){
            System.out.println("Only admins can add books.");
            return;
        }

        try{
            System.out.println("Enter Book ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            for(Book book : books){
                if(book.getID() == id){
                    System.out.println("Book ID already exists. Try another ID.");
                    return;
                }
            }
            System.out.println("Enter Book Title: ");
            String title = scanner.nextLine();

            System.out.println("Enter Author name: ");
            String author = scanner.nextLine();

            System.out.println("Enter Category/Genre: ");
            String category = scanner.nextLine();

            books.add(new Book(id, title, author, category));
            saveBooks();

            System.out.println("Book added successfully");
        }catch(InputMismatchException e){
            System.out.println("Invalid input! Book ID must be a number.");
            scanner.nextLine();
        }
    }

    public void removeBook(){
        if(currentUser == null || !currentUser.isAdmin()){
            System.out.println("Only admins can remove books.");
            return;
        }
        System.out.println("Enter Book ID to remove: ");
        int ID = scanner.nextInt();
        scanner.nextLine();

        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getID()== ID) {
                if (book.isIssued()) {
                    System.out.println("Cannot remove book. It is currently issued.");
                }else{
                    iterator.remove();
                    saveBooks();
                    System.out.println("Book removed successfully");
                }
                return;
            }
        }
        System.out.println("Book not found");
    }

    public void searchBook(){
        if(books.isEmpty()){
            System.out.println("No books available.");
            return;
        }
        System.out.println("Search by: ");
        System.out.println("1. ID");
        System.out.println("2. Title");
        System.out.println("3. Author");
        System.out.println("4. Category");
        System.out.println("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        List<Book> foundBooks = new ArrayList<>();

        switch (choice) {
            case 1:
                System.out.println("Enter Book ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                for(Book book : books){
                    if(book.getID() == id){
                        foundBooks.add(book);
                        break;
                    }
                }
                break;
        
            case 2:
                System.out.println("Enter Book Title (Partial match allowed): ");
                String title = scanner.nextLine().toLowerCase();

                for(Book book : books){
                    if(book.getTitle().toLowerCase().contains(title)){
                        foundBooks.add(book);
                    }
                }
                break;
            case 3:
                System.out.println("Enter author name (partial match allowed): ");
                String author = scanner.nextLine().toLowerCase();

                for(Book book : books){
                    if(book.getAuthor().toLowerCase().contains(author)){
                        foundBooks.add(book);
                    }
                }
                break;

            case 4:
                System.out.println("Enter Category (partial match allowed): ");
                String category = scanner.nextLine().toLowerCase();

                for(Book book : books){
                    if(book.getCategory().toLowerCase().contains(category)){
                        foundBooks.add(book);
                    }
                }
                break;

            default: 
                System.out.println("Invalid choice");
                return;
        }
        if(foundBooks.isEmpty()){
            System.out.println("No matching books found. ");
        }else{
            System.out.println("Found "+ foundBooks.size()+" matching books: ");
            for(Book book : foundBooks){
                System.out.println(book);
            }
        }
    }
    
    public void browseByCategory(){
        if(books.isEmpty()){
            System.out.println("No books available.");
            return;
        }

        Set<String> categories = new HashSet<>();
        for(Book book : books){
            categories.add(book.getCategory());
        }

        System.out.println("Available Categories");
        int i =1;
        for(String category : categories){
            System.out.println(i+ ". "+category);
            i++;
        }

        System.out.println("Enter category name to browse: ");
        String selectedCategory =scanner.nextLine();

        System.out.println("Books in category " + selectedCategory+ ":");
        boolean found = false;
        for(Book book : books){
            if(book.getCategory().equalsIgnoreCase(selectedCategory)){
                System.out.println(book);
                found = true;
            }
        }
        if(!found){
            System.out.println("No books found in this category.");
        }
    }

    public void viewBooks(){
        if(books.isEmpty()){
            System.out.println("No books available.");
            return;
        }

        for(Book book : books){
            System.out.println(book);
        }
    }

    public void viewIssuedBooks(){
        if(books.isEmpty()){
            System.out.println("No books available.");
            return;
        }

        boolean found = false;
        System.out.println("Currently issued books: ");

        for(Book book : books){
            if(book.isIssued()){
                found = true;

                String borrowerName = "Unknown";
                for(User user : users){
                    if(user.getUserID() == book.getBorrowerID()){
                        borrowerName = user.getName();
                        break;
                    }
                }

                long daysSinceIssue = 0;
                if(book.getIssueDate() != null){
                    long diffInMillis = new Date().getTime() - book.getIssueDate().getTime();
                    daysSinceIssue = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                }
                System.out.println(book+", Borrower: "+ borrowerName+", Days borrowed: "+ daysSinceIssue);
            }
        }
        if(!found){
            System.out.println("No books are currently issued.");
        }
    }

    public double calculatedFine(int bookID){
        for(Book book : books){
            if(book.getID() == bookID && book.isIssued() && book.getIssueDate() != null){
                long diffinMillies = new Date().getTime() - book.getIssueDate().getTime();
                long daysBorrowed = TimeUnit.DAYS.convert(diffinMillies, TimeUnit.MILLISECONDS);

                if(daysBorrowed > LOAN_PERIOD_DAYS){
                    long dayOverdue = daysBorrowed - LOAN_PERIOD_DAYS;
                    return dayOverdue * FINE_PER_DAY;
                }
            }
        }
        return 0.0;
    }

    public void issueBook() {
        if (currentUser == null) {
            System.out.println("Please login first");
            return;
        }

        if (currentUser.isAdmin()) {
            System.out.println("Enter user ID to issue book to: ");
            int userID = scanner.nextInt();
            scanner.nextLine();

            User targetUser = null;
            for (User user : users) {
                if (user.getUserID() == userID) {
                    targetUser = user;
                    break;
                }
            }

            if (targetUser == null) {
                System.out.println("User not found!");
                return;
            }

            if (targetUser.isAdmin()) {
                System.out.println("Cannot issue books to admin users.");
                return;
            }

            System.out.println("Enter Book ID to issue: ");
            int bookID = scanner.nextInt();
            scanner.nextLine();

            for (Book book : books) {
                if (book.getID() == bookID) {
                    if (!book.isIssued()) {
                        book.issueBook(targetUser.getUserID());
                        targetUser.addIssueBook(bookID);
                        saveBooks();
                        saveUsers();
                        System.out.println("Book issued successfully to " + targetUser.getName() + "!");
                        System.out.println("Due date: " + LOAN_PERIOD_DAYS + " day(s) from now.");
                    } else {
                        System.out.println("Book is already issued.");
                    }
                    return;
                }
            }

            System.out.println("Book not found");
        } else {
            System.out.println("Enter Book ID to issue: ");
            int bookID = scanner.nextInt();
            scanner.nextLine();

            for (Book book : books) {
                if (book.getID() == bookID) {
                    if (!book.isIssued()) {
                        book.issueBook(currentUser.getUserID());
                        currentUser.addIssueBook(bookID);
                        saveBooks();
                        saveUsers();
                        System.out.println("Book issued successfully");
                        System.out.println("Due date: " + LOAN_PERIOD_DAYS + " day(s) from now.");
                    } else {
                        System.out.println("Book is already issued.");
                    }
                    return;
                }
            }
            System.out.println("Book not found");
        }
    }


    public void returnBook(){
        if(currentUser == null){
            System.out.println("Please login first");
            return;
        }

        if(currentUser.isAdmin()){
            System.out.println("Enter Book ID to return: ");
            int bookID = scanner.nextInt();
            scanner.nextLine();

            for(Book book : books){
                if(book.getID() == bookID && book.isIssued()){
                    double fine = calculatedFine(bookID);
                    for(User user : users){
                        if(user.getUserID() == book.getBorrowerID()){
                            user.removeIssuedBook(bookID);
                            break;
                        }
                    }
                    book.returnBook();
                    saveBooks();
                    saveUsers();
                    System.out.println("Book returned successfully");
                    if(fine>0){
                        System.out.println("Fine amount: "+ fine);
                    }
                    return;
                }
            }
        }else{
                System.out.println("Enter Book ID to return: ");
                int bookID = scanner.nextInt();
                scanner.nextLine();

                double fine = calculatedFine(bookID);
                boolean issuedToCurrentUser = false;
                for(Integer issuedBookID : currentUser.getIssuedBookIDs()){
                    if(issuedBookID == bookID){
                        issuedToCurrentUser = true;
                        break;
                    }
                }

                if(!issuedToCurrentUser){
                    System.out.println("This book is not issued to you.");
                    return;
                }

                boolean bookFound = false;
                for(Book book : books){
                    if(book.getID() == bookID){

                    book.returnBook();
                    currentUser.removeIssuedBook(bookID);
                    saveBooks();
                    saveUsers();

                    System.out.println("Book returned successfully");

                    if(fine>0){
                        System.out.println("Fine amount: "+ fine);
                        System.out.println("Please pay the fine at the counter. ");
                    }
                    bookFound = true;
                    break;
                }

                if(!bookFound){
                    System.out.println("This book was not issued");
                }
            }
        }
        System.out.println("Book not found or not issued.");
    }

    public void viewMyBooks(){
        if(currentUser == null){
            System.out.println("Please login first.");
            return;
        }

        if(currentUser.getIssuedBookIDs().isEmpty()){
            System.out.println("You haven't issued any books.");
            return;
        }
        System.out.println("Your issued books: ");
        for(Integer bookID : currentUser.getIssuedBookIDs()){
            for(Book book : books){
                if(book.getID() == bookID){
                    double fine = calculatedFine(bookID);
                    System.out.printf("%s%s\n", book, (fine > 0 ? String.format(", Fine: $%.2f", fine) : ""));
                    break;
                }
            }
        }
    }

    private void saveBooks(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BOOK_FILE))){
            out.writeObject(books);
        }catch(IOException e){
            System.out.println("Error saving books: "+ e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadBooks(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(BOOK_FILE))){
            books = (List<Book>) in.readObject();
        }catch(FileNotFoundException e){
            books = new ArrayList<>();
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error loading books: "+ e.getMessage());
        }
    }

    private void saveUsers(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USERS_FILE))){
            out.writeObject(users);
        }catch(IOException e){
            System.out.println("Error saving users: "+ e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")

    private void loadUsers(){
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(USERS_FILE))){
            users = (List<User>) in.readObject();
        }catch(FileNotFoundException e){
            users = new ArrayList<>();
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error loading users: "+ e.getMessage());
        }
    }
}