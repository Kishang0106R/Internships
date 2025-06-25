import java.util.Scanner;
public class QuizApp {
    private static Scanner scanner;
    private static Quiz quiz;

    public static void main(String[] args){
        quiz = new Quiz();
        scanner = new Scanner(System.in);

        quiz.initialize();

        while(true){
            displayMainMenu();
            int choice = getValidIntInput(1,4);

            switch(choice){
                case 1:
                    quiz.setupQuiz();
                    quiz.start();
                    break;
                case 2:
                    quiz.adminMode();
                    break;
                case 3:
                    displayHelp();
                    break;
                case 4:
                    System.out.println("\nThank you for using the Java Quiz Application!");
                    scanner.close();
                    System.exit(0);
            }
        }
    }

    private static void displayMainMenu(){
        System.out.println("\n MAIN MENU");
        System.out.println("=================================");
        System.out.println("1. Start Quiz");
        System.out.println("2. Admin Mode (Manage Questions)");
        System.out.println("3. Help");
        System.out.println("4. Exit");
        System.out.print("Please enter your choice (1-4): ");
    }

    private static void displayHelp(){
        System.out.println("\nHELP");
        System.out.println("=================================");
        System.out.println("This advanced quiz application allows you to test your java knowledge.");
        System.out.println("\nFeatures:");
        System.out.println(". Multiple quiz modes (Quick, full, category, difficulty)");
        System.out.println(". Timed questions option");
        System.out.println(". Score tracking and leaderboard");
        System.out.println(". Different question types");
        System.out.println(". Admin mode to manage questions");
        System.out.println("\nHow to Play:");
        System.out.println("1. Select 'Start Quiz' from the main menu.");
        System.out.println("2. Enter your name and choose quiz settings");
        System.out.println("3. Answer by typing the number of your choice");
        System.out.println("4. View your score after completing the quiz");

        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }

    private static int getValidIntInput(int min , int max){
        int input = -1;
        do{
            try{
                System.out.println("\n Enter your choice ("+ min +"-"+ max +"): ");
                input = scanner.nextInt();
                scanner.nextLine();
                if(input < min || input > max){
                    System.out.println("Please enter a valid number");
                    input = -1;
                }
            }catch(Exception e){
                System.out.println("Please enter a valid number!");
                scanner.next();
            }
        }while(input < min || input > max);
        return input;
    }
}
