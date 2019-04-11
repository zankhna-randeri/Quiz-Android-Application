package sjsu.zankhna.quizapp.model;

import java.util.ArrayList;

/**
 * POJO to handle loadQuestions API response
 */

public class RequestQuestionResponse {

    private int response_code;
    private ArrayList<Question> results;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public ArrayList<Question> getResults() {
        return results;
    }

    public void setResults(ArrayList<Question> results) {
        this.results = results;
    }
}
