package sjsu.zankhna.quizapp.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "score")
public class ScoreEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date date;
    private int score;
    private String category;

    
}
