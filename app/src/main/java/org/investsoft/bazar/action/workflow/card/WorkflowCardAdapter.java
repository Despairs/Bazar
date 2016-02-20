package org.investsoft.bazar.action.workflow.card;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.investsoft.bazar.R;

import java.util.List;

/**
 * Created by Despairs on 20.02.16.
 */
public class WorkflowCardAdapter extends RecyclerView.Adapter<WorkflowCardHolder>{

    List<String> cards;

    public WorkflowCardAdapter(List<String> cards){
        this.cards = cards;
    }

    @Override
    public WorkflowCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_workflow_card, parent, false);
        WorkflowCardHolder holder = new WorkflowCardHolder(view) {
            @Override
            public void onRemove() {
                cards.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), cards.size());
            }
        };
        return holder;
    }

    @Override
    public void onBindViewHolder(WorkflowCardHolder holder, int position) {
        holder.getTextView().setText(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

}