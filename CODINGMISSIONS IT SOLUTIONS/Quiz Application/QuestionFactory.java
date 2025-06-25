public class QuestionFactory {

    public static TrueFalseQuestion createTrueFalseQuestion(String text, boolean answer, int difficulty, String category, int points) {
        return new TrueFalseQuestion(text, answer, difficulty, category, points);
    }

    public static MultipleChoiceQuestion createMultipleChoiceQuestion(String text, String[] options, int correctAnswerIndex, int difficulty, String category, int points) {
        return new MultipleChoiceQuestion(text, options, correctAnswerIndex, difficulty, category, points);
    }
}
