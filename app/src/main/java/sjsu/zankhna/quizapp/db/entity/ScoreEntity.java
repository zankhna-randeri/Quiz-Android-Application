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


    public ScoreEntity(int score, String category) {
        this.date = new Date();
        this.score = score;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getScore() {
        return score;
    }

    public String getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ScoreEntity{" +
                "id=" + id +
                ", date=" + date +
                ", score=" + score +
                ", category='" + category + '\'' +
                '}';
    }
}
