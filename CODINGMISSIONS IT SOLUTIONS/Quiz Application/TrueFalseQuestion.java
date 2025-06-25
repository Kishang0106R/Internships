
public class TrueFalseQuestion extends Question {
    public TrueFalseQuestion(String questionText, boolean isTrue, int difficulty, String category, int points ){
        super(questionText, new String[] {"True", "False"}, isTrue ? 1:2, difficulty, category, points);
    }

    public void displayQuestion(){
        System.out.println("\n"+ getQuestionText()+ "(True/False)");
        System.out.println("Category: "+ getCategory()+ " | Difficiculty: "+ (getDifficulty() == 1 ? "Easy" : getDifficulty() == 2 ? "Medium" : "Hard"));
        System.out.println("1. True");
        System.out.println("2. False");
    }
}
