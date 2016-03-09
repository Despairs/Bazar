package org.investsoft.bazar.action.common;

/**
 * Created by Despairs on 09.03.16.
 */
public class BasePresenter<View> {

    protected View view;

    public void bindView(View view) {
        this.view = view;
    }

    public void unbindView() {
        view = null;
    }
}
