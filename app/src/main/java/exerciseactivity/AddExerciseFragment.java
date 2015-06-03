package exerciseactivity;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import gymdatabase.Exercise;
import gymdatabase.ExercisesDataSource;
import gymdatabase.Set;
import gymdatabase.SetDataSource;
import se.miun.paer1301.gymlog.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExerciseFragment extends android.support.v4.app.Fragment {
        private static final String ARG_PARAM1 = "param1";

    private Exercise exercise;
    private String exerciseName;
    private ExercisesDataSource exerciseDataSource;
    private SetDataSource setDataSource;
    private List<Set> sets;
    private String[] weightNumbers;
    private TextView addedSets;
    private NumberPicker reps,weight;



    public static AddExerciseFragment newInstance(String param1) {
        AddExerciseFragment fragment = new AddExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public AddExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseName = getArguments().getString(ARG_PARAM1);
        }
        this.setHasOptionsMenu(true);
        sets = new ArrayList<>();
        exerciseDataSource = new ExercisesDataSource(getActivity());
        exerciseDataSource.open();
        exercise = exerciseDataSource.getExercise(exerciseName);
        setDataSource = new SetDataSource(getActivity());
        setDataSource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        addedSets = (TextView) view.findViewById(R.id.add_exercise_textview);
        TextView name = (TextView) view.findViewById(R.id.add_exercise_name);
        name.setText("One rep max: " + exercise.getMax());
        weight = (NumberPicker) view.findViewById(R.id.numberPicker_weight);
        weight.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        reps = (NumberPicker) view.findViewById(R.id.numberPicker_reps);
        reps.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        weight.setMinValue(1);
        weight.setMaxValue(100);
        reps.setMinValue(1);
        reps.setMaxValue(50);
        weightNumbers = new String[100];
        String number;
        double doubleNumber=15;
        for (int i = 0; i < weightNumbers.length; i++) {
            if(i<30)
                number = Double.toString((i+1.0)/2);
            else {
                doubleNumber += 2.5;
                number = Double.toString(doubleNumber);
            }
            weightNumbers[i] = number;
        }
        weight.setDisplayedValues(weightNumbers);

        Button btn = (Button) view.findViewById(R.id.button_add_set);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set set = new Set();
                set.setExercise(exerciseName);
                set.setReps(reps.getValue());
                set.setWeight(weightPickerConvert(weight.getValue()));
                sets.add(set);

                String txt = addedSets.getText().toString();
                txt+="\n";
                txt+="weight: "+weightPickerConvert(weight.getValue())+". reps: "+reps.getValue();
                addedSets.setText(txt);
            }
        });


        return view;
    }

    public Double weightPickerConvert(Integer i)
    {
        if(i <30)
            return i/2.0;
        else
            return 15+2.5*(i-30);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_add_exercise, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_exercise) {
            for(Set s : sets){
            setDataSource.createSet(s);}
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        exerciseDataSource.open();
        setDataSource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        setDataSource.close();
        exerciseDataSource.close();
        super.onPause();
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
