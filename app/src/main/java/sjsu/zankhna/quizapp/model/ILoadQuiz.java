package sjsu.zankhna.quizapp.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import sjsu.zankhna.quizapp.model.Question;

public interface ILoadQuiz {

    String BASE_URL = "https://opentdb.com/";
    String API_URL = "api.php";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET(API_URL)
    Call<List<Question>> loadQuestions();
}
