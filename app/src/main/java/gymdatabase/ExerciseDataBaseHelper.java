package gymdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pär on 2015-04-28.
 */
public class ExerciseDataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXERCISE = "Exercise";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MAX = "max";
    public static final String COLUMN_CATEGORY = "category";

    private static final String DATABASE_NAME = "gymlog.db";
    private static final int DATABASE_VERSION = 6;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_EXERCISE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null unique, " + COLUMN_CATEGORY +" text not null, " +
            COLUMN_MAX + " integer default 0);";

    public ExerciseDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
        database.execSQL(SetTableHelper.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ExerciseDataBaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + SetTableHelper.TABLE_SET);
        onCreate(db);
    }

}

