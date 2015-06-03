package exerciseactivity;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gymdatabase.Exercise;
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
public class ExerciseHistoryFragment extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM1 = "param1";

    private Exercise exercise;
    private String exerciseName;
    private ArrayAdapter<String> adapter;
    SetDataSource dataSource;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddExerciseFragment.
     */

    public static ExerciseHistoryFragment newInstance(String param1) {
        ExerciseHistoryFragment fragment = new ExerciseHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ExerciseHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseName = getArguments().getString(ARG_PARAM1);
        }
        this.setHasOptionsMenu(true);
        dataSource = new SetDataSource(getActivity());
        dataSource.open();

        List<Set> sets = dataSource.getAllSets(exerciseName);
        List<String> test = new ArrayList<String>();
        String previousSetDate="";
        String tmp="";

        for(Set s: sets) {
            if (s.getDate().equals(previousSetDate)) {
                tmp = test.get(test.size()-1);
                tmp+= "\n" + s.toString();
                test.set(test.size()-1, tmp);
            }
            else
                test.add(s.getDate()+"\n"+s.toString());
                previousSetDate=s.getDate();
        }
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, test);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_history, container, false);

        ListView listView = (ListView) view.findViewById(R.id.set_list);
        listView.setAdapter(adapter);
        return view;
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
