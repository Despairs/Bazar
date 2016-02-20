package org.investsoft.bazar.action.workflow.card;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.investsoft.bazar.R;

/**
 * Created by Despairs on 20.02.16.
 */
public abstract class WorkflowCardHolder extends RecyclerView.ViewHolder {

    private CardView cardView;
    private TextView textView;

    public abstract void onRemove();

    public WorkflowCardHolder(View view) {
        super(view);
        cardView =(CardView) view.findViewById(R.id.workflow_card);
        textView = (TextView) view.findViewById(R.id.uniq);
        Button removeButton = (Button) view.findViewById(R.id.workflow_remove_card);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRemove();
            }
        });
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}

