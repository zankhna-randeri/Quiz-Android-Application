package sjsu.zankhna.quizapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import sjsu.zankhna.quizapp.model.Question;
import sjsu.zankhna.quizapp.services.RequestQuestionService;
import sjsu.zankhna.quizapp.utils.Constants;
import sjsu.zankhna.quizapp.utils.NetworkHelper;

public class LoadQuizActivity extends AppCompatActivity {

    private final String TAG = "LoadQuizActivity";
    private boolean isNetworkConnected = false;

    private BroadcastReceiver questionResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Question> questions = intent
                    .getParcelableArrayListExtra(Constants.PAYLOAD_QUESTION_SERVICE);
            Toast.makeText(LoadQuizActivity.this,
                    "Received : " + questions.size() + "questions from service.",
                    Toast.LENGTH_LONG).show();
            displayData(questions);
        }
    };

    private void displayData(ArrayList<Question> questions) {
        for (Question q : questions) {
            Log.d(TAG, "displayData: " + q.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_quiz);


        isNetworkConnected = NetworkHelper.hasNetworkAccess(this);
        if (isNetworkConnected) {
            // TODO: Intent service call
//            Intent intent = new Intent(this, RequestQuestionService.class);
//            startService(intent);
            RequestQuestionService.startActionRequestQuestions(LoadQuizActivity.this,
                    "", "");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getApplicationContext()).
                registerReceiver(questionResponseReceiver,
                        new IntentFilter(Constants.RESPONSE_QUESTION_SERVICE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).
                unregisterReceiver(questionResponseReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext()).
                unregisterReceiver(questionResponseReceiver);
    }
}
