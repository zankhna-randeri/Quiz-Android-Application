package sjsu.zankhna.quizapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sjsu.zankhna.quizapp.adapters.ScoreAdapter;
import sjsu.zankhna.quizapp.db.entity.ScoreEntity;
import sjsu.zankhna.quizapp.viewmodel.ScoreViewModel;

public class ScoreActivity extends AppCompatActivity {

    private final String TAG = "ScoreActivity";

    private Context activityContext;
    private Unbinder unbinder;
    private ScoreViewModel scoreViewModel;
    private List<ScoreEntity> scores = new ArrayList<>();
    private ScoreAdapter adpScore;

    @BindView(R.id.recycler_score)
    RecyclerView scoreView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ButterKnife.bind(this);

        activityContext = ScoreActivity.this;
        unbinder = ButterKnife.bind(this);
        scoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);

        setUpToolbar();
        initScoreView();
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        title.setText(getString(R.string.txt_your_score));
    }

    private void initScoreView() {
        scoreView.setLayoutManager(new LinearLayoutManager(activityContext));
        scores = scoreViewModel.getScores();
        if (scores.size() > 0) {
            Log.d(TAG, String.format("initScoreView() received: %d scores", scores.size()));
            adpScore = new ScoreAdapter(scores, activityContext);
            scoreView.setAdapter(adpScore);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
