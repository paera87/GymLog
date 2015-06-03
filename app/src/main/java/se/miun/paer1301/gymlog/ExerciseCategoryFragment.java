package se.miun.paer1301.gymlog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by pär on 2015-04-25.
 */

public class ExerciseCategoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_exercise_category, container, false);
        ListView categories = (ListView) rootView.findViewById(R.id.ListView_categories);
        categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                String category = av.getItemAtPosition(i).toString();

                Intent intent = new Intent(getActivity(), ExerciseList.class);
                intent.putExtra("categoryName", category);
                Log.d("test",category);
                startActivity(intent);
            }
        });
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_categories, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new_exercise) {
            NewExerciseDialog cdd = new NewExerciseDialog(getActivity());
            cdd.setTitle("New Exercise");
            cdd.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
