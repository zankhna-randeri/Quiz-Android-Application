package sjsu.zankhna.quizapp.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import sjsu.zankhna.quizapp.model.Question;

public class QuizViewModel extends ViewModel {

    private int score;
    private int currentQueIndex = -1;
    private ArrayList<Question> questions;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore() {
        score = score + 1;
    }

    public Question nextQuestion() {
        currentQueIndex++;
        return questions.get(currentQueIndex);
    }

    public int getCurrentQueIndex() {
        return currentQueIndex;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public int totalQuestions() {
        return questions.size();
    }
}
