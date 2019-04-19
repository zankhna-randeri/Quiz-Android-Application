package sjsu.zankhna.quizapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import sjsu.zankhna.quizapp.db.AppRepository;
import sjsu.zankhna.quizapp.db.entity.ScoreEntity;

public class ScoreViewModel extends AndroidViewModel {

    private LiveData<List<ScoreEntity>> scores;
    private AppRepository repository;

    public ScoreViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
        scores = repository.getAllScores();
    }

    public void addScore(ScoreEntity score) {
        repository.addScore(score);
    }

    public LiveData<List<ScoreEntity>> getScores() {
        scores = repository.getScores();
        return scores;
    }
}
