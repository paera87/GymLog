package se.miun.paer1301.gymlog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import gymdatabase.Exercise;
import gymdatabase.ExercisesDataSource;

public class NewExerciseDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity a;
    public Button add;
    private EditText exerciseName;
    private Spinner spinner;

    public NewExerciseDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.a = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        exerciseName = (EditText) findViewById(R.id.exercise_name);
        spinner = (Spinner) findViewById(R.id.spinner);
        add = (Button) findViewById(R.id.button_add);
        add.setOnClickListener(this);
  }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                addExercise();
                break;
                }
        dismiss();
    }
    public void addExercise()
    {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseName.getText().toString());
        exercise.setCategory(spinner.getSelectedItem().toString());
        ExercisesDataSource datasource = new ExercisesDataSource(getContext());
        datasource.open();
        datasource.createExercise(exercise);
    }
}