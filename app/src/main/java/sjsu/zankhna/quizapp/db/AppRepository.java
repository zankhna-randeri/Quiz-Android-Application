package sjsu.zankhna.quizapp.db;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import sjsu.zankhna.quizapp.db.entity.ScoreEntity;

public class AppRepository {

    private LiveData<List<ScoreEntity>> scores;
    private QuizDatabase quizDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    private static AppRepository repoInstance;

    public static AppRepository getInstance(Context context) {
        if (repoInstance == null) {
            repoInstance = new AppRepository(context);
        }
        return repoInstance;
    }

    public LiveData<List<ScoreEntity>> getScores() {
        return scores;
    }

    private AppRepository(Context context) {
        quizDb = QuizDatabase.getInstance(context);
        scores = getAllScores();
    }

    public LiveData<List<ScoreEntity>> getAllScores() {
        return quizDb.scoreDao().getAllScores();
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
