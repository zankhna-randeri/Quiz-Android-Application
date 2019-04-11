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
    private static final String EXTRA_PARAM1 = "sjsu.zankhna.quizapp.services.extra.PARAM1";


    public RequestQuestionService() {
        super("RequestQuestionService");
    }

    /**
     * Starts this service to perform action RequestQuestion with the given parameters. If
     * the service is already performing a task this action will be queued.
     */
    public static void startActionRequestQuestions(Context context, String param1, String param2) {
        Intent intent = new Intent(context, RequestQuestionService.class);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            final String param1 = intent.getStringExtra(EXTRA_PARAM1);
            handleActionRequestQuestion(param1);
        }
    }

    /**
     *
     */
    private void handleActionRequestQuestion(String param1) {
        if (NetworkHelper.hasNetworkAccess(getApplicationContext())) {
            IQuizWebService quizWebService = IQuizWebService.retrofit.create(IQuizWebService.class);
            Call<RequestQuestionResponse> call = quizWebService.loadQuestions(
                    Constants.VALUE_AMOUNT,
                    Constants.VALUE_CATEGORY,
                    Constants.VALUE_DIFFICULTY,
                    Constants.VALUE_TYPE);

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
