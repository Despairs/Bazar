package org.investsoft.bazar.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.investsoft.bazar.R;
import org.investsoft.bazar.ui.adapter.WorkflowCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkflowFragment extends Fragment {

    private RecyclerView recyclerView;
    private WorkflowCardAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> l = new ArrayList<>();
        l.add("1");
        l.add("2");
        l.add("3");
        l.add("4");
        l.add("5");
        l.add("6");
        l.add("7");
        adapter = new WorkflowCardAdapter(l);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workflow, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView)v.findViewById(R.id.workflow_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

}
