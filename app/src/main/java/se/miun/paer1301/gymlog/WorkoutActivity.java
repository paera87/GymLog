package se.miun.paer1301.gymlog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gymdatabase.Set;
import gymdatabase.SetDataSource;


public class WorkoutActivity extends ActionBarActivity{

    private SetDataSource dataSource;
    private ArrayAdapter<String> adapter;
    private String date;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        ListView listView = (ListView) findViewById(R.id.performed_exercises);
        listView.setAdapter(adapter);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F77300")));

        dataSource = new SetDataSource(this);
        dataSource.open();
        date = getIntent().getExtras().getString("date");
        setTitle(date);
        List<Set> sets = dataSource.getSetsFromDate(date);
        List<String> test = new ArrayList<String>();
        String previousExercice="";
        String tmp="";

        for(Set s: sets) {
            if (s.getExercise().equals(previousExercice)) {
                tmp = test.get(test.size()-1);
                tmp+= "\n" + s.toString();
                test.set(test.size()-1, tmp);
            }
            else
                test.add(s.getExercise()+"\n"+s.toString());
            previousExercice=s.getExercise();
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, test);
        listView.setAdapter(adapter);
    }
}
