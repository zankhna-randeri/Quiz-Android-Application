package sjsu.zankhna.quizapp;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sjsu.zankhna.quizapp.db.entity.ScoreEntity;
import sjsu.zankhna.quizapp.model.Question;
import sjsu.zankhna.quizapp.services.RequestQuestionService;
import sjsu.zankhna.quizapp.utils.Constants;
import sjsu.zankhna.quizapp.utils.NetworkHelper;
import sjsu.zankhna.quizapp.viewmodel.QuizViewModel;
import sjsu.zankhna.quizapp.viewmodel.ScoreViewModel;

public class QuizActivity extends AppCompatActivity {

    private Context activityContext;

    private QuizViewModel quizModel;
    private ScoreViewModel scoreModel;

    private Question currentQuestion;
    private Unbinder unbinder;
    private final Handler handler = new Handler();

    private final String TAG = "QuizActivity";

    private boolean isNetworkConnected = false;
    private int categoryId = -1;
    //    private List<Question> questions;
    private List<String> options;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.txt_question)
    TextView questionView;

    @BindViews({R.id.btn_option1, R.id.btn_option2, R.id.btn_option3, R.id.btn_option4})
    List<Button> optionViews;

    @BindView(R.id.lyt_progress)
    LinearLayout progressLayout;

    @BindView(R.id.lyt_quiz)
    LinearLayout quizLayout;

    @BindView(R.id.txt_progress_message)
    TextView progressMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        unbinder = ButterKnife.bind(this);

        activityContext = QuizActivity.this;
        initViewModel();


        getIntentData(getIntent());
        setUpToolbar();

        isNetworkConnected = NetworkHelper.hasNetworkAccess(this);
        if (isNetworkConnected) {
            // TODO: Intent service call
//            Intent intent = new Intent(this, RequestQuestionService.class);
//            startService(intent);
            if (quizModel.getQuestions() == null || quizModel.getQuestions().size() == 0) {
                setUpProgress();
                RequestQuestionService.startActionRequestQuestions(QuizActivity.this,
                        categoryId);
            } else {
                handleOrientation();
            }
        } else {
            Toast.makeText(activityContext, getString(R.string.message_no_network), Toast.LENGTH_LONG)
                    .show();
        }
    }



    private void initViewModel() {
        quizModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        scoreModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
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
        unbinder.unbind();
        LocalBroadcastManager.getInstance(getApplicationContext()).
                unregisterReceiver(questionResponseReceiver);
    }

    private void setUpProgress() {
        progressLayout.setVisibility(View.VISIBLE);
        progressMessage.setText(getString(R.string.message_loading_quiz));
    }

    private BroadcastReceiver questionResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Question> questions = intent
                    .getParcelableArrayListExtra(Constants.PAYLOAD_QUESTION_SERVICE);
            quizModel.setQuestions(questions);
            Toast.makeText(QuizActivity.this,
                    "Received : " + questions.size() + "questions from service.",
                    Toast.LENGTH_LONG).show();
            progressLayout.setVisibility(View.INVISIBLE);
            quizLayout.setVisibility(View.VISIBLE);
            generateQuizData();
        }
    };

    private void handleOrientation() {
        progressLayout.setVisibility(View.INVISIBLE);
        quizLayout.setVisibility(View.VISIBLE);
        int index = quizModel.getCurrentQueIndex();
        currentQuestion = quizModel.getQuestions().get(index);
        options = new ArrayList<>();
        options.add(currentQuestion.getCorrect_answer());
        for (String incorrectAnswer : currentQuestion.getIncorrect_answers()) {
            options.add(incorrectAnswer);
        }
        Collections.shuffle(options);
        displayQuiz();
    }

    private void generateQuizData() {
        currentQuestion = quizModel.nextQuestion();
        options = new ArrayList<>();
        options.add(currentQuestion.getCorrect_answer());
        for (String incorrectAnswer : currentQuestion.getIncorrect_answers()) {
            options.add(incorrectAnswer);
        }
        Collections.shuffle(options);
        displayQuiz();
    }

    private void displayQuiz() {

        Log.d(TAG, "questionView: " + currentQuestion.getQuestion());
        questionView.setText(Html.fromHtml(currentQuestion.getQuestion()).toString());

        for (int i = 0; i < options.size(); i++) {
            optionViews.get(i).setText(Html.fromHtml(options.get(i)).toString());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                optionViews.get(i).
                        setBackground(getResources().getDrawable(R.drawable.option_normal));
            } else {
                optionViews.get(i).
                        setBackgroundDrawable(getResources().getDrawable(R.drawable.option_normal));
            }
        }
    }

    private void getIntentData(Intent intent) {
        if (intent.getExtras() != null &&
                intent.getExtras().containsKey(Constants.EXTRA_CATEGORY_ID)) {
            categoryId = intent.getIntExtra(Constants.EXTRA_CATEGORY_ID, -1);
        }
    }


    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText(getString(R.string.app_name));
    }


    @OnClick({R.id.btn_option1, R.id.btn_option2, R.id.btn_option3, R.id.btn_option4})
    public void optionClick(Button option) {
        if (option.getText().equals(currentQuestion.getCorrect_answer())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                option.setBackground(getResources().getDrawable(R.drawable.option_correct));
            } else {
                option.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_correct));
            }
            Log.d(TAG, "questionView Type : " + currentQuestion.getDifficulty());
            quizModel.incrementScore();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                option.setBackground(getResources().getDrawable(R.drawable.option_wrong));
            } else {
                option.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_wrong));
            }
        }

        // Wait for 1 second before loading new questionView
        if (quizModel.getCurrentQueIndex() < quizModel.totalQuestions() - 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    generateQuizData();
                }
            }, 1000);
        } else {
            displayScoreDialog();
        }
    }

    private void displayScoreDialog() {
        final Dialog dialog = new Dialog(activityContext);
        dialog.setContentView(R.layout.dialog_score);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        Button ok = dialog.findViewById(R.id.btn_ok);
        TextView message = dialog.findViewById(R.id.txt_dialog_message);
        message.setText(
                String.format(Locale.getDefault(),
                        getString(R.string.txt_score_achieved) + " %d",
                        quizModel.getScore()));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore();
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void addScore() {
        ScoreEntity score = new ScoreEntity(
                new Date(),
                quizModel.getScore(),
                currentQuestion.getCategory());
        scoreModel.addScore(score);
    }
}
