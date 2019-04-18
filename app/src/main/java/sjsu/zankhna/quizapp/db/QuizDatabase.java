package sjsu.zankhna.quizapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import sjsu.zankhna.quizapp.db.converter.DateConverter;
import sjsu.zankhna.quizapp.db.dao.ScoreDao;
import sjsu.zankhna.quizapp.db.entity.ScoreEntity;

@Database(entities = {ScoreEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class QuizDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "QuizDatabase.db";

    private static volatile QuizDatabase quizDbInstance;
    private static final Object LOCK = new Object();

    public abstract ScoreDao scoreDao();

    public static QuizDatabase getInstance(Context context) {
        if (quizDbInstance == null) {
            synchronized (LOCK) {
                if (quizDbInstance == null) {
                    quizDbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            QuizDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return quizDbInstance;
    }
}
