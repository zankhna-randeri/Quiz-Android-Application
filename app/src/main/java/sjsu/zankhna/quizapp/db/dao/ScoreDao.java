package sjsu.zankhna.quizapp.db.dao;

import android.arch.persistence.room.Dao;

import java.util.List;

import sjsu.zankhna.quizapp.db.entity.ScoreEntity;

@Dao
public interface ScoreDao {

    void insertScore(ScoreEntity score);

    List<ScoreEntity> getAllScores();

}
