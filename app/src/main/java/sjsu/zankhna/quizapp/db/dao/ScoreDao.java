package sjsu.zankhna.quizapp.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import sjsu.zankhna.quizapp.db.entity.ScoreEntity;

@Dao
public interface ScoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(ScoreEntity score);

    @Query("SELECT * from score ORDER BY date DESC")
    LiveData<List<ScoreEntity>> getAllScores();

}
