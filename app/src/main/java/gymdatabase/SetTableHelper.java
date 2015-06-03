package gymdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pär on 2015-05-06.
 */
public class SetTableHelper  {

    public static final String TABLE_SET = "Set_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXERCISE = "exercise_name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_REPS = "reps";
    public static final String COLUMN_WEIGHT = "weight";

    public static final String TABLE_CREATE = "create table "
            + TABLE_SET + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_EXERCISE
            + " text not null, " + COLUMN_DATE +" date, "+ COLUMN_REPS +" text not null, " +
    COLUMN_WEIGHT + " real default 0);";






}
