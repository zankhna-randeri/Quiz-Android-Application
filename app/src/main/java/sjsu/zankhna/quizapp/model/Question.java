package sjsu.zankhna.quizapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Question POJO that maps to API response
 */
public class Question implements Parcelable {

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private String[] incorrect_answers;


    public Question(String category, String type, String difficulty, String question,
                    String correct_answer, String[] incorrect_answers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
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

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String[] getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(String[] incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", question='" + question + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", incorrect_answers=" + Arrays.toString(incorrect_answers) +
                '}';
    }

    protected Question(Parcel in) {
        this.category = in.readString();
        this.type = in.readString();
        this.difficulty = in.readString();
        this.question = in.readString();
        this.correct_answer = in.readString();
        this.incorrect_answers = in.createStringArray();
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
        dest.writeString(this.correct_answer);
        dest.writeStringArray(this.incorrect_answers);
    }
}
