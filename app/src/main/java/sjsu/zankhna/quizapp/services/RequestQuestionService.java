package sjsu.zankhna.quizapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import sjsu.zankhna.quizapp.model.IQuizWebService;
import sjsu.zankhna.quizapp.model.Question;
import sjsu.zankhna.quizapp.model.RequestQuestionResponse;
import sjsu.zankhna.quizapp.utils.Constants;
import sjsu.zankhna.quizapp.utils.NetworkHelper;

public class RequestQuestionService extends IntentService {

    private final String TAG = "RequestQuestionService";
    private static final String EXTRA_CATEGORY_ID = "categoryId";


    public RequestQuestionService() {
        super("RequestQuestionService");
    }


    /**
     * Starts this service to perform action RequestQuestion with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @param context    Context of activity that will invoke the service
     * @param categoryId The integer id of current category
     */
    public static void startActionRequestQuestions(Context context, int categoryId) {
        Intent intent = new Intent(context, RequestQuestionService.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final int categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, -1);
            handleActionRequestQuestion(categoryId);
        }
    }

    /**
     * Invokes webservice to fetch questions from webservice and parses response. After receiving response,
     * result is returned to caller activity.
     *
     * @param categoryId The integer id of current category
     */
    private void handleActionRequestQuestion(int categoryId) {
        if (NetworkHelper.hasNetworkAccess(getApplicationContext())) {
            IQuizWebService quizWebService = IQuizWebService.retrofit.create(IQuizWebService.class);
            Call<RequestQuestionResponse> call = quizWebService.loadQuestionsOfAnyDifficulty(
                    Constants.VALUE_AMOUNT, categoryId, Constants.VALUE_TYPE);
            try {
                RequestQuestionResponse response = call.execute().body();
                if (response.getResponse_code() == Constants.RESPONSE_SUCCESS) {
                    ArrayList<Question> questions = response.getResults();
                    Intent responseIntent = new Intent(Constants.RESPONSE_QUESTION_SERVICE);
                    responseIntent.putParcelableArrayListExtra(Constants.PAYLOAD_QUESTION_SERVICE, questions);
                    LocalBroadcastManager broadcastManager = LocalBroadcastManager
                            .getInstance(getApplicationContext());
                    broadcastManager.sendBroadcast(responseIntent);
                } else {
                    //TODO : Handle other response codes
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "handleActionRequestQuestion: " + e.getMessage());
                return;
            }

        }
    }
}
