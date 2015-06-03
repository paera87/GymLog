package gymdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by pär on 2015-05-06.
 */
public class SetDataSource {
    // Database fields
    private SQLiteDatabase database;
    private ExerciseDataBaseHelper dbHelper;
    private String[] allColumns = { SetTableHelper.COLUMN_ID,
            SetTableHelper.COLUMN_EXERCISE, SetTableHelper.COLUMN_DATE, SetTableHelper.COLUMN_REPS, SetTableHelper.COLUMN_WEIGHT };

    public SetDataSource(Context context) {
        dbHelper = new ExerciseDataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public Set createSet(Set set) {
        ContentValues values = new ContentValues();
        values.put(SetTableHelper.COLUMN_EXERCISE, set.getExercise());
        values.put(SetTableHelper.COLUMN_DATE, getDate());
        values.put(SetTableHelper.COLUMN_REPS, set.getReps());
        values.put(SetTableHelper.COLUMN_WEIGHT, set.getWeight());
        long insertId = database.insert(SetTableHelper.TABLE_SET, null,
                values);
        Cursor cursor = database.query(SetTableHelper.TABLE_SET,
                allColumns, SetTableHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Set newSet = cursorToSet(cursor);
        cursor.close();
        return newSet;
    }

    public void deleteSet (Set set) {
        long id = set.getId();
        System.out.println("Set deleted with id: " + id);
        database.delete(SetTableHelper.TABLE_SET, SetTableHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Set> getAllSets(String exercise) {
        List<Set> sets = new ArrayList<>();

        Cursor cursor = database.query(SetTableHelper.TABLE_SET,
                allColumns, SetTableHelper.COLUMN_EXERCISE+"=?", new String[]{exercise}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Set set = cursorToSet(cursor);
            sets.add(set);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return sets;
    }

    public List<Set> getSetsFromDate(String date) {
        List<Set> sets = new ArrayList<>();

        Cursor cursor = database.query(SetTableHelper.TABLE_SET,
                allColumns, SetTableHelper.COLUMN_DATE+"=?", new String[]{date}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Set set = cursorToSet(cursor);
            sets.add(set);
            cursor.moveToNext();
        }

        cursor.close();
        return sets;
    }

    private Set cursorToSet(Cursor cursor) {
        Set set = new Set();
        set.setId(cursor.getLong(0));
        set.setExercise(cursor.getString(1));
        set.setDate(cursor.getString(2));
        set.setReps(cursor.getInt(3));
        set.setWeight(cursor.getDouble(4));

        return set;
    }



}
