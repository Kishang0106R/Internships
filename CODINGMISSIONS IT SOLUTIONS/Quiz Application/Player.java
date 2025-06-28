
public class Player {
    private String name;
    private int score;
    private int questionAnswered;
    private int correctAnswers;

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.questionAnswered =0;
        this.correctAnswers = 0;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public void addScore(int points){
        this.score += points;
        this.correctAnswers++;
    }

    public void incrementQuestionAnswered(){
        this.questionAnswered++;
    }

    public int getQuestionAnswered(){
        return questionAnswered;
    }

    public int getCorrectAnswers(){
        return correctAnswers;
    }

    public double getAccuracy() {
        return questionAnswered > 0 ? ((double) correctAnswers / questionAnswered * 100) : 0.0;
    }

}
