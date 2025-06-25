
public class Question {

    protected String questionText;
    protected String[] options;
    protected int correctAnswerIndex;
    protected int difficulty;
    protected String category;
    protected int points;

    public Question(String questionText, String[] options, int correctAnswerIndex){
        this (questionText, options, correctAnswerIndex, 1, "General", 1);
    }

    public Question(String questionText, String[] options, int correctAnswerIndex, int difficulty, String category, int points) {
    this.questionText = questionText;
    this.options = options;
    this.correctAnswerIndex = correctAnswerIndex;
    this.difficulty = difficulty;
    this.category = category;
    this.points = points;
}


    public String getQuestionText(){
        return questionText;
    }

    public String[] getOptions(){
        return options;
    }

    public int getCorrectAnswerIndex() {
    return correctAnswerIndex;
}

public int getCorrectAnswer() {
    return getCorrectAnswerIndex(); 
}

    public int getDifficulty(){
        return difficulty;
    }

    public String getCategory(){
        return category;
    }

    public int getPoints(){
        return points;
    }

    public void displayQuestion(){
        System.out.println("\n"+ questionText);
        System.out.println("Category: "+ category+ " |Diffeiculty: "+ getDifficultyString());
        for(int i = 0; i<options.length; i++){
            System.out.println((i+1)+ ". "+ options[i]);
        }
    }

    public boolean isCorrect(int userAnswer){
        return userAnswer == correctAnswerIndex;
    }

    private String getDifficultyString(){
        switch(difficulty){
            case 1: 
                return "Easy";
            case 2:
                return "Medium";
            case 3:
                return "Hard";
            default:
                return "Unknown";
        }
    }

    public String toString(){
        return questionText + "|"+ String.join(", ", options)+ "|"+ correctAnswerIndex+ "|"+difficulty+"|"+category+"|"+points;

    }
}
