package testWeb;
import java.util.List;

public class Question {
    private String question;
    private List<String> answers;
    private int correct;
    private int time;

    public Question(String question, List<String> answers, int correct, int time) {
        this.question = question;
        this.answers = answers;
        this.correct = correct;
        this.time = time;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrect() {
        return correct;
    }

    public int getTime() {
        return time;
    }
}

