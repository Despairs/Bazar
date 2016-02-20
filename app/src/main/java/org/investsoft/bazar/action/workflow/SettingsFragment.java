package org.investsoft.bazar.action.workflow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.investsoft.bazar.R;

public class SettingsFragment extends Fragment {

    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        text = (TextView) view.findViewById(R.id.txt);
        text.setText("settings");
        return view;
    }

}
