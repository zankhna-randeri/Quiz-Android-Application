package sjsu.zankhna.quizapp.model;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sjsu.zankhna.quizapp.utils.Constants;

public interface ILoadQuiz {

    String BASE_URL = "https://opentdb.com/";
    String API_URL = "api.php";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET(API_URL)
    Call<RequestQuestionResponse> loadQuestions(@Query(Constants.PARAM_AMOUNT) int amount,
                                            @Query(Constants.PARAM_CATEGORY) int category,
                                            @Query(Constants.PARAM_DIFFICULTY) String difficulty,
                                            @Query(Constants.PARAM_TYPE) String type);
}
