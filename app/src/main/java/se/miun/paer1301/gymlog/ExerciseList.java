package se.miun.paer1301.gymlog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import exerciseactivity.ExerciseTabbedActivity;
import gymdatabase.Exercise;
import gymdatabase.ExercisesDataSource;

/**
 * Created by pär on 2015-06-02.
 */
public class ExerciseList extends ActionBarActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F77300")));

        String selectedCategory = getIntent().getExtras().getString("categoryName");
        setTitle(selectedCategory+" exercises");
        MyFragment fragment = MyFragment.newInstance(selectedCategory);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }



    public static class MyFragment extends ListFragment {
        String category;
        private ExercisesDataSource dataSource;
        private ArrayAdapter<Exercise> adapter;
        public static MyFragment newInstance(String param1) {
            MyFragment fragment = new MyFragment();
            Bundle args = new Bundle();
            args.putString("category", param1);
            fragment.setArguments(args);
            return fragment;
        }

        public MyFragment() {
        }
        public void onActivityCreated(Bundle savedState) {
            registerForContextMenu(getListView());
            super.onActivityCreated(savedState);

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (getArguments() != null) {
                category = getArguments().getString("category");
            }
            dataSource = new ExercisesDataSource(getActivity());
            dataSource.open();
            List<Exercise> values = dataSource.getExercisesInCategory(category);


            //(ListView) rootView.findViewById(R.id.exerciselist);
            adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);

            return super.onCreateView(inflater, container, savedInstanceState);

        }
        public void onListItemClick(ListView listView, View view, int position, long id) {
            Exercise exercise = (Exercise) getListAdapter().getItem(position);
            Intent intent = new Intent(getActivity(), ExerciseTabbedActivity.class);
            intent.putExtra("exerciseName", exercise.toString());
            startActivity(intent);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            AdapterView.AdapterContextMenuInfo info;
            info = (AdapterView.AdapterContextMenuInfo) menuInfo;

            Exercise exercise = (Exercise) getListAdapter().getItem(info.position);
            menu.setHeaderTitle(exercise.toString());
            menu.add(0, 1, 1, "Delete!");
        }
    }
}