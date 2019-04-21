package sjsu.zankhna.quizapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import sjsu.zankhna.quizapp.model.Question;
import sjsu.zankhna.quizapp.utils.Constants;

public class QuizViewModel extends AndroidViewModel {

    private int score = 0;
    private int currentQueIndex = -1;
    private ArrayList<Question> questions;
    private Question currentQuestion;

    public QuizViewModel(@NonNull Application application) {
        super(application);
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        switch (currentQuestion.getDifficulty()) {
            case Constants.DIFFICULTY_EASY:
                score = score + 1;
                break;
            case Constants.DIFFICULTY_MEDIUM:
                score = score + 3;
                break;
            case Constants.DIFFICULTY_HARD:
                score = score + 5;
                break;
        }
    }

    public Question nextQuestion() {
        currentQueIndex++;
        currentQuestion = questions.get(currentQueIndex);
        return currentQuestion;
    }

    public int getCurrentQueIndex() {
        return currentQueIndex;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int totalQuestions() {
        return questions.size();
    }


}
