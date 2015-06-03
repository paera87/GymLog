package se.miun.paer1301.gymlog;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import calendarview.ExtendedCalendarView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends android.support.v4.app.Fragment {

private ExtendedCalendarView calendar;
    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendar = (ExtendedCalendarView) view.findViewById(R.id.calendarView);
        return view;
    }


}
