import java.util.*;
import java.io.*;
public class QuestionBank {
    private List<Question> questions;
    private final String QUESTION_FILE = "questions.txt";

    public QuestionBank(){
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public List<Question> getQuestions(){
        return questions;
    }

    public List<Question> getQuestionsByCategory(String category){
        List<Question> filteredQuestions = new ArrayList<>();
        for(Question q : questions){
            if(q.getCategory().equalsIgnoreCase(category)){
                filteredQuestions.add(q);
            }
        }
        return filteredQuestions;
    }

    public List<Question> getQuestionsByDifficulty(int difficulty){
        List<Question> filterQuestions = new ArrayList<>();
        for(Question q : questions){
            if(q.getDifficulty() == difficulty){
                filterQuestions.add(q);
            }
        }
        return filterQuestions;
    }

    public void loadDefaultQuestions(){
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("which of the following is not a java feature?", new String[] {"Platform independence", "Pointer", "Object-oriented", "Robust"}, 2, 1, "java Basics", 1));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("Which data type is used to create a variable that should store text in java?", new String[]{"myString", "String", "Txt", "string"}, 2, 1, "Java Basics", 1));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("Which is a valid declaration of a method in Java", new String[]{"void myMethod()", "static void myMethod()", "void myMethod", "int myMethod()"},2, 2, "Methods", 2));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("What is the correct way to create an object in java?", new String[] {"class obj = new obj();", "new obj = class();","class obj = new Class;",  "ClassName obj = new ClassName();"}, 4, 2, "OOP", 2));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("Which keyword is used to inherit a class in Java?", new String[]{"implements", "extends", "inherits", "using"}, 4, 2, "OOP", 2));
        addQuestion(QuestionFactory.createTrueFalseQuestion("In java, array are objects. ", true, 1, "Arrays", 1));
        addQuestion(QuestionFactory.createTrueFalseQuestion("Java supports multiple inheritance through classes.", false, 3, "OOP", 3));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("What is the purpose of 'super' keyword in java?", new String[]{"To call superclass methods", "To refer current class instance", " to access static variables", "To create objects"}, 1, 3, "OOP", 3));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("What is capital of India?", new String[]{"Mumbai", "Delhi", "Hyderabad", "Chennai"},2, 1, "General", 1));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("Who wrote 'Romeo and Juliet'?", new String[]{"Whilliam Wordsworth", "Willioam Shakespeare", "mark Twain", "Charies Dickens"}, 2, 1, "General", 1));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("Which planet is known as the red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Venus"}, 2, 1, "General", 1));
        addQuestion(QuestionFactory.createMultipleChoiceQuestion("Which is the largest ocean on Earth?", new String[]{"Atlantic", "Indian", "Pacific", "Arctic"}, 3, 2, "General", 2));
    }

    public void saveQuestionToFile(){
        try(
            FileWriter fw = new FileWriter(QUESTION_FILE);
            BufferedWriter bw = new BufferedWriter(fw)){
                for(Question q : questions){
                    bw.write(q.toString());
                    bw.newLine();
                }
                System.out.println("Questions saved to file successfully!");
        }catch(IOException e){
            System.out.println("Error saving Question: "+ e.getMessage());
        }
        
    }

    public void loadQuestionFromFile(){
        File file = new File(QUESTION_FILE);
        if(!file.exists()){
            System.out.println("Questions file not found, loading default questions.");
            loadDefaultQuestions();
            return;
        }

        try(FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr)){
            String line;
            questions.clear();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if(parts.length>= 6){
                    String questionText = parts[0];
                String[] options = parts[1].split(",");

                    int correctAnswer = Integer.parseInt(parts[2]);
                    int difficulty = Integer.parseInt(parts[3]);
                    String category = parts[4];
                    int points = Integer.parseInt(parts[5]);

                    if (options.length == 2 &&
                    options[0].equalsIgnoreCase("True") &&
                    options[1].equalsIgnoreCase("False")) {
                        addQuestion(QuestionFactory.createTrueFalseQuestion(questionText, correctAnswer == 1, difficulty, category, points));
                    }else{
                        addQuestion(QuestionFactory.createMultipleChoiceQuestion(questionText, options, correctAnswer, difficulty, category, points));
                    }
                }
                
            }
            System.out.println("Questions loaded from file successfully!");
        } catch(IOException | NumberFormatException e){
        System.out.println("Error loading questions: "+ e.getMessage());
        System.out.println("Loading default questions instead.");
        loadDefaultQuestions();
        }
    }
}
