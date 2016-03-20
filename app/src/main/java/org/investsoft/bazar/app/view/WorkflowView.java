package org.investsoft.bazar.app.view;

import android.app.Fragment;

/**
 * Created by Despairs on 18.03.16.
 */
public interface WorkflowView {
    public void navigateToActivity(Class activity, boolean finishRootActivity);

    public void showFragment(Fragment fragment, int titleId);

    public void changeDrawerHeader(String name, String email);

}
