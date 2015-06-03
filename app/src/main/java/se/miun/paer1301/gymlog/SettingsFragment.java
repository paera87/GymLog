package se.miun.paer1301.gymlog;

import android.os.Bundle;
import android.os.Environment;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import java.io.File;
import java.util.ArrayList;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        fillLists();
    }

    public void fillLists()
    {
    /*    ListPreference voice = (ListPreference) getPreferenceScreen().findPreference("voice_type");
        ListPreference delete = (ListPreference) getPreferenceScreen().findPreference("voice_delete");
        File voiceFolder = new File(Environment.getExternalStorageDirectory().toString() + "/dialpad/voices/");

        String[] directories = voiceFolder.list();
        ArrayList<String> entries = new ArrayList<String>();
        ArrayList<String> entryValues = new ArrayList<String>();

        for (String s : directories)
            entries.add(s);
        for (String s : directories)
            entryValues.add(voiceFolder.toString() + "/" + s);

        String[] entriesArr = new String[entries.size()];
        entriesArr = entries.toArray(entriesArr);
        String[] entryValuesArr = new String[entryValues.size()];
        entryValuesArr = entryValues.toArray(entryValuesArr);

        voice.setEntries(entriesArr);
        voice.setEntryValues(entryValuesArr);
        delete.setEntries(entriesArr);
        delete.setEntryValues(entryValuesArr);*/
    }
}
