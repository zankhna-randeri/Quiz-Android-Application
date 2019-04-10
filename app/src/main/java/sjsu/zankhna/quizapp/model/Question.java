package sjsu.zankhna.quizapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Question implements Parcelable {

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private String[] incorrectAnswers;


    public Question(String category, String type, String difficulty, String question,
                    String correctAnswer, String[] incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(String[] incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", incorrectAnswers=" + Arrays.toString(incorrectAnswers) +
                '}';
    }

    protected Question(Parcel in) {
        this.category = in.readString();
        this.type = in.readString();
        this.difficulty = in.readString();
        this.question = in.readString();
        this.correctAnswer = in.readString();
        int size = in.readInt();
        this.incorrectAnswers = new String[size];
        for (int i = 0; i < size; i++) {
            incorrectAnswers[i] = in.readString();
        }
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.type);
        dest.writeString(this.difficulty);
        dest.writeString(this.question);
        dest.writeString(this.correctAnswer);
        for (String answer : incorrectAnswers) {
            dest.writeString(answer);
        }
    }
}
