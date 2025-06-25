import java.util.*;
import java.io.*;

public class Quiz {
    private QuestionBank questionBank;
    private List<Question> currentQuizQuestions;
    private Player player;
    private int timeLimit;
    private boolean timedMode;
    private Scanner scanner;
    private static final String LEADERBOARD_FILE = "leaderboard.txt";
    private static final int DEFAULT_TIME_LIMIT = 15;

    public Quiz(){
        questionBank = new QuestionBank();
        currentQuizQuestions = new ArrayList<>();
        scanner = new Scanner(System.in);
        timeLimit = DEFAULT_TIME_LIMIT;
        timedMode = false;
    }

    public void initialize(){
        questionBank.loadQuestionFromFile();
        clearScreen();
        displayWelcomeScreen();
    }

    private void displayWelcomeScreen(){
        System.out.println("=======JAVA Quiz Application========");
        System.out.println("\n welcome to the advanced java quiz!\n");
    }

    public void setupQuiz(){
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine().trim();
        if(playerName.isEmpty()) playerName = "player";
        player = new Player(playerName);


        System.out.println("\n Select Quiz Mode: ");
        System.out.println("1. Quick Quiz (5 random questions)");
        System.out.println("2. Full Quiz (All questions)");
        System.out.println("3. Category Quiz");
        System.out.println("4. Difficulty Level Quiz");
        int modeChoice = getValidIntInput(1, 4);

        System.out.println("\n Enable timed mode? (y/n)");
        timedMode = scanner.nextLine().trim().equalsIgnoreCase("y");

        if(timedMode){
            System.out.println("set time limit per question(5-60 seconds): ");
            timeLimit = getValidIntInput(5, 60);
        }

        switch (modeChoice) {
            case 1:
                setupQuickQuiz();
                break;
            case 2:
                setupFullQuiz();
                break;
            case 3:
                setupCategoryQuiz();
                break;
            case 4:
                setupDifficultyQuiz();
                break;
        }
        Collections.shuffle(currentQuizQuestions);

    }

    private void setupQuickQuiz(){
        List<Question> allQuestions = questionBank.getQuestions();
        Collections.shuffle(allQuestions);
        int questionCount = Math.min(5, allQuestions.size());
        currentQuizQuestions = new ArrayList<>(allQuestions.subList(0, questionCount));
    }

    private void setupFullQuiz(){
        currentQuizQuestions = new ArrayList<>(questionBank.getQuestions());
    }

    private void setupCategoryQuiz(){
        Set<String> categories = new HashSet<>();
        for(Question q : questionBank.getQuestions()){
            categories.add(q.getCategory());
        }

        System.out.println("\n Select a category: ");
        List <String> categoryList = new ArrayList<>(categories);
        for(int i = 0; i<categoryList.size(); i++){
            System.out.println((i+1)+ ", "+ categoryList.get(i));
        }

        int categoryChoice = getValidIntInput(1, categories.size());
        String selectedCategory = categoryList.get(categoryChoice - 1);
        currentQuizQuestions = questionBank.getQuestionsByCategory(selectedCategory);
    }

    private void setupDifficultyQuiz(){
        System.out.println("\n Select difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        int difficultyChoice = getValidIntInput(1, 3);
        currentQuizQuestions = questionBank.getQuestionsByDifficulty(difficultyChoice);
    }

    public void start(){
        if(currentQuizQuestions.isEmpty()){
            System.out.println("No questions available for this quiz. Please try again.");
            return;
        }
        System.out.println("======================================================");

        for(Question question : currentQuizQuestions){
            clearScreen();
            question.displayQuestion();

            long startTime = System.currentTimeMillis();
            long endTime = startTime + (timeLimit * 1000);
            int userAnswer = 0;
            boolean timeOut = false;

            if(timedMode){
                Thread timerThread = new Thread(new Runnable() {
                    public void run() {
                        long remaining;
                        while ((remaining = endTime - System.currentTimeMillis()) > 0) {
                            try {
                                System.out.print("\rTime remaining: " + (remaining / 1000) + " seconds");
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                        System.out.println("\nTime's up!");
                    }
                });
                timerThread.setDaemon(true);
                timerThread.start();

                try{
                    System.out.println("\n Enter your answer(1-"+question.getOptions().length+"): ");
                    while(!scanner.hasNextInt() && System.currentTimeMillis() < endTime){
                        scanner.next();
                    }
                    if(System.currentTimeMillis() >= endTime){
                        timeOut = true;
                    }else {
                        userAnswer = scanner.nextInt();
                        scanner.nextLine(); 
                    }
                    timerThread.interrupt();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                while(true){
                    try{
                        System.out.println("\nEnter your answer(1-"+ question.getOptions().length + "): ");
                        userAnswer = scanner.nextInt();
                        scanner.nextLine();
                        if(userAnswer < 1 || userAnswer > question.getOptions().length){
                            System.out.println("Invalid answer. Enter a number between 1 and "+ question.getOptions().length);
                            continue;
                        }
                        break;
                    }catch (Exception e){
                    System.out.println("Please enter a valid number!");
                    scanner.next();
                    }
                }
            }

            player.incrementQuestionAnswered();

            if(timeOut){
                System.out.println("Time's up! Moving to next question.");
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    System.out.println("Error during sleep: " + e.getMessage());
                }
            }else if(question.isCorrect(userAnswer)){
                System.out.println("Correct answer!"+ question.getPoints() + " points awarded.");
                player.addScore(question.getPoints());
            }else{
                System.out.println("Wrong answer! The correct answer was: " + question.getCorrectAnswer() + ". " + question.getOptions()[question.getCorrectAnswer() - 1]);
            }

            try{
                System.out.println("\n Press Enter to Continue....");
                scanner.nextLine();
            }catch (Exception e){
                System.out.println("Error during input: " + e.getMessage());
            }
        }
        displayResults();
        saveScore();
        displayLeaderboard();
    }
    private void displayResults(){
        clearScreen();
        System.out.println("\n Quiz Completed!");
        System.out.println("--------------------------------");
        System.out.println("Player: " + player.getName());
        System.out.println("Score: " + player.getScore());
        System.out.println("Correct Answers: " + player.getCorrectAnswers()+ " out of " + player.getQuestionAnswered());
        System.out.println("Accuracy: " + String.format("%.2f", player.getAccuracy()) + "%");

        if(player.getAccuracy() >= 80){
            System.out.println("Excellent performance! You're a Java master!");
    }else if(player.getAccuracy() >= 60){
            System.out.println("Good job! Keep practicing to improve further.");
        }else{
            System.out.println("Keep studying! You'll get better with practice.");
        }
    }

    private void saveScore(){
        try(FileWriter fw = new FileWriter(LEADERBOARD_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(player.getName() + "|" + player.getScore() + "|" + player.getCorrectAnswers() + "|" + player.getQuestionAnswered() + "|" + String.format("%.2f", player.getAccuracy()));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    private void displayLeaderboard(){
        System.out.println("\n Leaderboard");
        System.out.println("=================================");
        List<String[]> leaderboardEntries = new ArrayList<>();
        try(FileReader fr = new FileReader(LEADERBOARD_FILE);
            BufferedReader br = new BufferedReader(fr)) {
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split("\\|");
                if(parts.length >= 3){
                    leaderboardEntries.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("No previous scores found.");
            return;
        }

        leaderboardEntries.sort((a, b) -> Integer.parseInt(b[1]) - Integer.parseInt(a[1]));
        int count = 0;
        for(String[] entry : leaderboardEntries){
            if(count >= 5) break;
            System.out.println((count + 1)+ ". " + entry[0]+"_"+ entry[1] + " points");
            count++;
        }
        System.out.println("\n Press Enter to continue...");
        scanner.nextLine();
    }
    
    public void adminMode(){
        clearScreen();
        System.out.println("ADMIN MODE: QUESTION MANAGEMENT");
        System.out.println("----------------------------------");

        while(true){
            System.out.println("\nSelect an option:");
            System.out.println("1. View all questions");
            System.out.println("2. Add a new question");
            System.out.println("3. Save questions to file");
            System.out.println("4. Return to main menu");

            int choice = getValidIntInput(1, 4);
            switch (choice) {
                case 1:
                    viewAllQuestions();
                    break;
                case 2:
                    addNewQuestion();
                    break;
                case 3:
                    questionBank.saveQuestionToFile();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void viewAllQuestions(){
        clearScreen();
        System.out.println("All Questions:");
        System.out.println("---------------");
        List<Question> questions = questionBank.getQuestions();
        if(questions.isEmpty()){
            System.out.println("No questions available.");
            return;
        }
        for(int i = 0; i < questions.size(); i++){
            Question q = questions.get(i);
            System.out.println((i + 1) + ". " + q.getQuestionText());
            System.out.println("   Category: " + q.getCategory() + " | Difficulty: " + q.getDifficulty());
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void addNewQuestion(){
        clearScreen();
        System.out.println("Add a New Question:");
        System.out.println("--------------------");

        System.out.println("Select question type:");
        System.out.println("1. Multiple Choice");
        System.out.println("2. True/False");
        int typeChoice = getValidIntInput(1, 2);

        try{
            scanner.nextLine();
            System.out.println("Enter the question text:");
            String questionText = scanner.nextLine();

            String category;
            System.out.println("Enter the category:");
            category = scanner.nextLine();
            if(category.isEmpty()) category = "General";

            System.out.println("Enter difficulty (1-Easy, 2-Medium, 3-Hard):");
            int difficulty = getValidIntInput(1, 3);

            int points = difficulty;

            if(typeChoice == 1){
                String[] options = new String[4];
                for(int i = 0; i < 4; i++){
                    System.out.println("Enter option " + (i + 1) + ":");
                    options[i] = scanner.nextLine();
                }
                System.out.println("Enter the correct answer number (1-4):");
                int correctAnswer = getValidIntInput(1, 4);
                
                questionBank.addQuestion(QuestionFactory.createMultipleChoiceQuestion(questionText, options, correctAnswer, difficulty, category, points));
            }else{
                System.out.println("Is the statement true? (y/n):");
                boolean isTrue = scanner.nextLine().trim().equalsIgnoreCase("y");
                questionBank.addQuestion(QuestionFactory.createTrueFalseQuestion(questionText, isTrue, difficulty, category, points));
            }

            System.out.println("Question added successfully!");
        }
        catch (Exception e) {
            System.out.println("Error adding question: " + e.getMessage());
        }
    }

    private int getValidIntInput(int min, int max){
        int input = -1;
        do {
            try {
                System.out.print("Enter your choice (" + min + "-" + max + "): ");
                input = scanner.nextInt();
                scanner.nextLine();
                if(input < min || input > max){
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    input = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); 
            }
        } while(input < min || input > max);
        return input;
    }

    private void clearScreen(){
        try{
            final String os = System.getProperty("os.name");
            if(os.contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }else{
            System.out.println("\033[H\033[2J");
            System.out.flush();
        }
    }catch(Exception e){
            for(int i = 0; i < 50; i++){
                System.out.println();
            }
        }
    }
}
