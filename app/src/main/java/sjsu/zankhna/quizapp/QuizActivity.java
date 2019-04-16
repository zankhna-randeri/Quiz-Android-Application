package sjsu.zankhna.quizapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sjsu.zankhna.quizapp.model.Question;
import sjsu.zankhna.quizapp.services.RequestQuestionService;
import sjsu.zankhna.quizapp.utils.Constants;
import sjsu.zankhna.quizapp.utils.NetworkHelper;
import sjsu.zankhna.quizapp.viewmodel.ScoreViewModel;

public class LoadQuizActivity extends AppCompatActivity {

    private Context activityContext;
    private final String TAG = "LoadQuizActivity";
    private ScoreViewModel scoreModel;
    private boolean isNetworkConnected = false;
    private int categoryId = -1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_quiz);
        ButterKnife.bind(this);

        activityContext = LoadQuizActivity.this;
        scoreModel = ViewModelProviders.of(this).get(ScoreViewModel.class);

        getIntentData(getIntent());
        setUpToolbar();

        isNetworkConnected = NetworkHelper.hasNetworkAccess(this);
        if (isNetworkConnected) {
            // TODO: Intent service call
//            Intent intent = new Intent(this, RequestQuestionService.class);
//            startService(intent);
            RequestQuestionService.startActionRequestQuestions(LoadQuizActivity.this,
                    categoryId);
        } else {
            // TODO : Show appropriate message
        }
    }

    private BroadcastReceiver questionResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Question> questions = intent
                    .getParcelableArrayListExtra(Constants.PAYLOAD_QUESTION_SERVICE);
            Toast.makeText(LoadQuizActivity.this,
                    "Received : " + questions.size() + "questions from service.",
                    Toast.LENGTH_LONG).show();
            displayQuestion(questions);
        }
    };

    private void displayQuestion(ArrayList<Question> questions) {
        for (Question q : questions) {
            Log.d(TAG, "displayQuestion: " + q.toString());
        }
    }

    private void getIntentData(Intent intent) {
        if (intent.getExtras() != null &&
                intent.getExtras().containsKey(Constants.EXTRA_CATEGORY_ID)) {
            categoryId = intent.getIntExtra(Constants.EXTRA_CATEGORY_ID, -1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return false;
        }
        return false;
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText(getString(R.string.app_name));
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
