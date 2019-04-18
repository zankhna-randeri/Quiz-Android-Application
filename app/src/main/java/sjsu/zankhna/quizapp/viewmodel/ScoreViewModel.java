package sjsu.zankhna.quizapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import sjsu.zankhna.quizapp.db.AppRepository;
import sjsu.zankhna.quizapp.db.entity.ScoreEntity;

public class ScoreViewModel extends AndroidViewModel {

    private List<ScoreEntity> scores;
    private AppRepository repository;

    public ScoreViewModel(@NonNull Application application) {
        super(application);
        repository = AppRepository.getInstance(application.getApplicationContext());
        scores = repository.getScores();
    }

    public AppRepository getRepository() {
        return repository;
    }


    public void addScore(ScoreEntity score) {
        repository.addScore(score);
    }
}
