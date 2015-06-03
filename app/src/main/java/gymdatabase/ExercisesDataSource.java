package gymdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pär on 2015-04-28.
 */
public class ExercisesDataSource {
    // Database fields
    private SQLiteDatabase database;
    private ExerciseDataBaseHelper dbHelper;
    private String[] allColumns = { ExerciseDataBaseHelper.COLUMN_ID,
            ExerciseDataBaseHelper.COLUMN_NAME, ExerciseDataBaseHelper.COLUMN_CATEGORY, ExerciseDataBaseHelper.COLUMN_MAX };

    public ExercisesDataSource(Context context) {
        dbHelper = new ExerciseDataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Exercise createExercise(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(ExerciseDataBaseHelper.COLUMN_NAME, exercise.getName());
        values.put(ExerciseDataBaseHelper.COLUMN_CATEGORY, exercise.getCategory());
        values.put(ExerciseDataBaseHelper.COLUMN_MAX, exercise.getMax());
        long insertId = database.insert(ExerciseDataBaseHelper.TABLE_EXERCISE, null,
                values);

        Cursor cursor = database.query(ExerciseDataBaseHelper.TABLE_EXERCISE,
                allColumns, ExerciseDataBaseHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Exercise newExercise = cursorToExercise(cursor);
        cursor.close();
        return newExercise;
    }



    public void deleteExercise (Exercise exercise) {
        long id = exercise.getId();
        System.out.println("Number deleted with id: " + id);
        database.delete(ExerciseDataBaseHelper.TABLE_EXERCISE, ExerciseDataBaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();

        Cursor cursor = database.query(ExerciseDataBaseHelper.TABLE_EXERCISE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return exercises;
    }

    public List<Exercise> getExercisesInCategory(String category) {
        List<Exercise> exercises = new ArrayList<>();

        Cursor cursor = database.query(ExerciseDataBaseHelper.TABLE_EXERCISE,
                allColumns, ExerciseDataBaseHelper.COLUMN_CATEGORY+"=?", new String[]{category}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = cursorToExercise(cursor);
            exercises.add(exercise);
            cursor.moveToNext();
        }

        cursor.close();
        return exercises;
    }

    public Exercise getExercise(String name) {

        Cursor cursor = database.query(ExerciseDataBaseHelper.TABLE_EXERCISE,
                allColumns, ExerciseDataBaseHelper.COLUMN_NAME+"=?", new String[]{name}, null, null, null);

        cursor.moveToFirst();
        Exercise exercise = cursorToExercise(cursor);
        cursor.close();
        return exercise;
    }

    private Exercise cursorToExercise(Cursor cursor) {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setName(cursor.getString(1));
        exercise.setCategory(cursor.getString(2));
        exercise.setMax(cursor.getDouble(3));

        return exercise;
    }
}

