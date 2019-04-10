package sjsu.zankhna.quizapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import sjsu.zankhna.quizapp.model.ILoadQuiz;
import sjsu.zankhna.quizapp.model.Question;
import sjsu.zankhna.quizapp.utils.Constants;
import sjsu.zankhna.quizapp.utils.NetworkHelper;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RequestQuestionService extends IntentService {

    private final String TAG = "RequestQuestionService";
    private static final String EXTRA_PARAM1 = "sjsu.zankhna.quizapp.services.extra.PARAM1";


    public RequestQuestionService() {
        super("RequestQuestionService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, RequestQuestionService.class);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void requestQuestions(Context context, String param1, String param2) {
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
            ILoadQuiz quizWebService = ILoadQuiz.retrofit.create(ILoadQuiz.class);
            Call<List<Question>> call = quizWebService.loadQuestions();
            try {
                ArrayList<Question> questions = (ArrayList<Question>) call.execute().body();
                Intent responseIntent = new Intent(Constants.RESPONSE_QUESTION_SERVICE);
                responseIntent.putParcelableArrayListExtra(Constants.PAYLOAD_QUESTION_SERVICE, questions);
                LocalBroadcastManager broadcastManager = LocalBroadcastManager
                        .getInstance(getApplicationContext());
                broadcastManager.sendBroadcast(responseIntent);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "handleActionRequestQuestion: " + e.getMessage());
                return;
            }

        }
    }
}
