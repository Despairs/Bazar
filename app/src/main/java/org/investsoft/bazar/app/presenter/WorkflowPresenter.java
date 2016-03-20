package org.investsoft.bazar.app.presenter;

import android.app.Fragment;
import android.text.TextUtils;

import org.investsoft.bazar.R;
import org.investsoft.bazar.app.view.WorkflowView;
import org.investsoft.bazar.ui.AboutFragment;
import org.investsoft.bazar.ui.LoginActivity;
import org.investsoft.bazar.ui.PasscodeActivity;
import org.investsoft.bazar.ui.SettingsActivity;
import org.investsoft.bazar.ui.WorkflowFragment;
import org.investsoft.bazar.utils.AndroidUtils;
import org.investsoft.bazar.utils.ApplicationLoader;
import org.investsoft.bazar.utils.UserConfig;
import org.investsoft.bazar.utils.events.EventType;

/**
 * Created by Despairs on 18.03.16.
 */
public class WorkflowPresenter extends BasePresenter<WorkflowView>  {

    public static final int NO_ACTION = -1;
    public static final int LOGOUT = 0;
    public static final int SHOW_SETTINGS = 1;
    public static final int CHANGE_FRAGMENT = 2;

    private int currentAction = NO_ACTION;
    private Fragment fragmentToShow = null;
    private int titleToShow = 0;

    public void init() {
        //Init common app resources
        ApplicationLoader.initApplication();
        //Navigate to Login screen if user is not logged in
        if (UserConfig.sessionId == null) {
            view.navigateToActivity(LoginActivity.class, true);
            return;
        }
        changeDrawerHeader();
        //WorkflowFragment = main screen
        //onBackPressed must return to this workflow
        view.showFragment(WorkflowFragment.getInstance(), R.string.app_name);
    }

    public void onNavigationItemSelected(int id) {
        switch (id) {
            case R.id.menu_workflow_about:
                fragmentToShow = AboutFragment.getInstance();
                titleToShow = R.string.about;
                currentAction = CHANGE_FRAGMENT;
                break;
            case R.id.menu_workflow_preferences:
                currentAction = SHOW_SETTINGS;
                break;
            case R.id.menu_workflow_main:
                fragmentToShow = WorkflowFragment.getInstance();
                titleToShow = R.string.app_name;
                currentAction = CHANGE_FRAGMENT;
                break;
            case R.id.menu_workflow_logout:
                currentAction = LOGOUT;
                break;
        }
    }

    public void onDrawerClosed() {
        switch (currentAction) {
            case LOGOUT:
                UserConfig.clearPersonalInfo();
                UserConfig.save();
                view.navigateToActivity(LoginActivity.class, true);
                break;
            case SHOW_SETTINGS:
                view.navigateToActivity(SettingsActivity.class, false);
                break;
            case CHANGE_FRAGMENT:
                //Change fragment only when drawer closed coz of animations
                //and add to backstack only when choosed fragment is not main workflow fragment
                view.showFragment(fragmentToShow, titleToShow);
                break;
        }
        currentAction = NO_ACTION;
        fragmentToShow = null;
        titleToShow = 0;
    }

    public void onPause() {
        if (UserConfig.passcodeEnabled) {
            UserConfig.lastPauseTime = System.currentTimeMillis();
            UserConfig.save();
        } else {
            UserConfig.lastPauseTime = 0;
        }
    }

    public void onResume() {
        if (AndroidUtils.needShowPasscode(true)) {
            UserConfig.lastPauseTime = 0;
            UserConfig.save();
            view.navigateToActivity(PasscodeActivity.class, true);
        }
    }

    public void onDestroy() {
        if (!UserConfig.rememberMe && !UserConfig.passcodeEnabled) {
            UserConfig.clearPersonalInfo();
            UserConfig.save();
        }
    }

    public void onEventReceive(int eventId, Object... data) {
        switch (eventId) {
            case EventType.USER_DATA_CHANGED:
                changeDrawerHeader();
                break;
        }
    }

    private void changeDrawerHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(UserConfig.user.getLastname())
                .append(" ")
                .append(UserConfig.user.getName());
        if (!TextUtils.isEmpty(UserConfig.user.getSurname())) {
            sb.append(" ")
                    .append(UserConfig.user.getSurname());
        }
        view.changeDrawerHeader(sb.toString(), UserConfig.user.getEmail());
        sb.delete(0, sb.length());
        sb = null;
    }
}
