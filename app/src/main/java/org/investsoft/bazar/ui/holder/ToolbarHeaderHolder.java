package org.investsoft.bazar.ui.holder;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.investsoft.bazar.R;

/**
 * Created by Despairs on 20.02.16.
 */
public class ToolbarHeaderHolder {

    private View headerView;
    private TextView nameView;
    private TextView emailView;

    public ToolbarHeaderHolder(View headerView) {
        this.headerView = headerView;
        this.nameView = (TextView) headerView.findViewById(R.id.workflow_toolbar_header_name);
        this.emailView = (TextView) headerView.findViewById(R.id.workflow_toolbar_header_email);
    }

    public TextView getNameView() {
        return nameView;
    }

    public void setNameView(EditText nameView) {
        this.nameView = nameView;
    }


    public TextView getEmailView() {
        return emailView;
    }

    public void setEmailView(EditText emailView) {
        this.emailView = emailView;
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }
}
