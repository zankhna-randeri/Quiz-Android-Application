package sjsu.zankhna.quizapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
        setUpToolbar();
        initScoreView();
        initViewModel();
    }

    private void initViewModel() {
        Observer<List<ScoreEntity>> scoreObserver = new Observer<List<ScoreEntity>>() {
            @Override
            public void onChanged(@Nullable List<ScoreEntity> scoreEntities) {
                scores.clear();
                if (scoreEntities != null) {
                    scores.addAll(scoreEntities);
                }
                if (adpScore == null) {
                    adpScore = new ScoreAdapter(scores, activityContext);
                    scoreView.setAdapter(adpScore);
                } else {
                    adpScore.notifyDataSetChanged();
                }
            }
        };
        scoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        scoreViewModel.getScores().observe(this, scoreObserver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
