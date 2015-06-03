package se.miun.paer1301.gymlog;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import gymdatabase.Exercise;
import gymdatabase.ExercisesDataSource;


public class DownloadExerciseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_exercise);

        final ExercisesDataSource datasource = new ExercisesDataSource(this);
        datasource.open();
        final List<Exercise> exercisesAlreadySaved = datasource.getAllExercises();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "eZfIWGUYsCvwCWHwvd2TsusdzIiuZzDrWbwNO2vt", "yBm2kLsYADsjurtVIbT9G40AhTgral8GwL3vt6Nc");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Exercise");
        query.findInBackground(new FindCallback<ParseObject>() {
            String name, category;
            public void done(List<ParseObject> exercises, ParseException e) {
                if (e == null) {
                    boolean alreadyExists;
                    for(int i=0;i<exercises.size();i++) {
                        alreadyExists = false;
                        name = exercises.get(i).getString("name");
                        for(Exercise ex : exercisesAlreadySaved)
                        {
                            if (ex.getName().equals(name))
                                alreadyExists = true;
                        }
                        if (alreadyExists == false)
                        {
                            category = exercises.get(i).getString("category");
                            Exercise exercise = new Exercise();
                            exercise.setName(name);
                            exercise.setCategory(category);
                            datasource.createExercise(exercise);
                        }
                    }
                    Toast.makeText(getApplicationContext(), "New exercises added!", Toast.LENGTH_LONG).show();
                } else {
                    // handle Parse Exception here
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_download_exercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
