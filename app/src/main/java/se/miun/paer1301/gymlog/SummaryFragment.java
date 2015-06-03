package se.miun.paer1301.gymlog;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import calendarview.CalendarProvider;
import calendarview.Event;
import gymdatabase.Set;
import gymdatabase.SetDataSource;

/**
 * Created by pär on 2015-04-25.
 */

public class SummaryFragment extends Fragment {

    private SetDataSource dataSource;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        dataSource = new SetDataSource(getActivity());
        dataSource.open();

        List<Set> sets = dataSource.getSetsFromDate(SetDataSource.getDate());
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
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, test);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_summary, container, false);

        ListView listView = (ListView) view.findViewById(R.id.summary_list);
        listView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_summary, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_workout) {
            //save this workout to calendar
            ContentValues values = new ContentValues();
            values.put(CalendarProvider.COLOR, Event.COLOR_GREEN);
            values.put(CalendarProvider.DESCRIPTION, "Some Description");
            values.put(CalendarProvider.LOCATION, "Some location");
            values.put(CalendarProvider.EVENT, "Event name");

            Calendar cal = Calendar.getInstance();

            values.put(CalendarProvider.START, cal.getTimeInMillis());
            TimeZone tz = TimeZone.getDefault();
            int julianDay = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));
            values.put(CalendarProvider.START_DAY, julianDay);
            values.put(CalendarProvider.END, cal.getTimeInMillis());
            values.put(CalendarProvider.END_DAY, julianDay);

            Uri uri = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, values);
        }
        return super.onOptionsItemSelected(item);
    }

}
