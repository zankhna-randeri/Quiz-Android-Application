package sjsu.zankhna.quizapp.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import sjsu.zankhna.quizapp.db.entity.ScoreEntity;

public class AppRepository {

    private List<ScoreEntity> scores;
    private QuizDatabase quizDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    private static AppRepository repoInstance;

    public static AppRepository getInstance(Context context) {
        if (repoInstance == null) {
            repoInstance = new AppRepository(context);
        }
        return repoInstance;
    }


    private AppRepository(Context context) {
        scores = new ArrayList<>();
        quizDb = QuizDatabase.getInstance(context);
    }

    public List<ScoreEntity> getScores() {
        return scores;
    }

    public void addScore(final ScoreEntity score) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                quizDb.scoreDao().insertScore(score);
            }
        });
    }
}
