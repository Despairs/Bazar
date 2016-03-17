package org.investsoft.bazar.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import org.investsoft.bazar.R;
import org.investsoft.bazar.ui.holder.ToolbarHeaderHolder;
import org.investsoft.bazar.utils.AndroidUtils;
import org.investsoft.bazar.utils.ApplicationLoader;
import org.investsoft.bazar.utils.UserConfig;
import org.investsoft.bazar.utils.WorkflowActivityAction;
import org.investsoft.bazar.utils.events.EventManager;
import org.investsoft.bazar.utils.events.EventType;

public class WorkflowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EventManager.EventReceiver {

    private Fragment choosenFragment;
    private int choosenFragmentTitle;

    private AboutFragment aboutFragment;
    private WorkflowFragment workflowFragment;

    private int currentAction = WorkflowActivityAction.NO_ACTION;

    private ToolbarHeaderHolder headerHolder;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationLoader.initApplication();

        if (UserConfig.sessionId == null) {
            startLoginActivity();
            return;
        }

        fm = getFragmentManager();

        setContentView(R.layout.activity_workflow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_workflow);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View view, float slideOffset) {
                AndroidUtils.hideKeyboard(view);
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                switch (currentAction) {
                    case WorkflowActivityAction.LOGOUT:
                        UserConfig.clearPersonalInfo();
                        UserConfig.save();
                        startLoginActivity();
                        break;
                    case WorkflowActivityAction.SHOW_SETTINGS:
                        startSettingsActivity();
                        break;
                    case WorkflowActivityAction.CHANGE_FRAGMENT:
                        //Change fragment only when drawer closed coz of animations
                        //and add to backstack only when choosed fragment is not main workflow fragment
                        changeFragment(choosenFragment, choosenFragmentTitle);
                        break;
                }
                currentAction = WorkflowActivityAction.NO_ACTION;
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.workflow_navigation);
        if (headerHolder == null) {
            headerHolder = new ToolbarHeaderHolder(navigationView.getHeaderView(0));
        }
        updateNavigationHeader();
        navigationView.setNavigationItemSelectedListener(this);

        //Init fragments
        aboutFragment = new AboutFragment();
        workflowFragment = new WorkflowFragment();
        //WorkflowFragment = main screen
        //onBackPressed must return to this workflow
        fm.beginTransaction()
                .add(R.id.workflow_container, workflowFragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_workflow_about:
                if (!aboutFragment.isVisible()) {
                    choosenFragment = aboutFragment;
                    choosenFragmentTitle = R.string.about;
                    currentAction = WorkflowActivityAction.CHANGE_FRAGMENT;
                }
                break;
            case R.id.menu_workflow_preferences:
                currentAction = WorkflowActivityAction.SHOW_SETTINGS;
                break;
            case R.id.menu_workflow_main:
                if (!workflowFragment.isVisible()) {
                    choosenFragment = workflowFragment;
                    choosenFragmentTitle = R.string.app_name;
                    currentAction = WorkflowActivityAction.CHANGE_FRAGMENT;
                }
                break;
            case R.id.menu_workflow_logout:
                currentAction = WorkflowActivityAction.LOGOUT;
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onEventReceive(int eventId, Object... data) {
        if (eventId == EventType.SHOW_PASSCODE) {
            startPasscodeActivity();
        } else if (eventId == EventType.USER_DATA_CHANGED) {
            updateNavigationHeader();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int stackSize = fm.getBackStackEntryCount();
            if (stackSize > 0) {
                changeFragment(workflowFragment, R.string.app_name);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch (keycode) {
            case KeyEvent.KEYCODE_MENU:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onKeyDown(keycode, e);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventManager.getInstance().registerReceiver(EventType.USER_DATA_CHANGED, this);
        if (AndroidUtils.needShowPasscode(true)) {
            UserConfig.lastPauseTime = 0;
            UserConfig.save();
            startPasscodeActivity();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventManager.getInstance().unregisterReceiver(EventType.USER_DATA_CHANGED, this);
        if (UserConfig.passcodeEnabled) {
            UserConfig.lastPauseTime = System.currentTimeMillis();
            UserConfig.save();
        } else {
            UserConfig.lastPauseTime = 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!UserConfig.rememberMe && !UserConfig.passcodeEnabled) {
            UserConfig.clearPersonalInfo();
            UserConfig.save();
        }
    }

    private void changeFragment(Fragment fragment, int titleId) {
        if (fragment == null) {
            return;
        }
        if (fragment == workflowFragment) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            FragmentTransaction tx = fm.beginTransaction().replace(R.id.workflow_container, fragment);
            tx.addToBackStack(null);
            tx.commit();
        }
        //Change toolbar title
        changeToolbarTitle(titleId);
        choosenFragment = null;
        choosenFragmentTitle = 0;
    }

    private void changeToolbarTitle(int titleId) {
        getSupportActionBar().setTitle(titleId);
    }

    private void updateNavigationHeader() {
        headerHolder.getNameView().setText(UserConfig.user.getLastname() + " " + UserConfig.user.getName());
        headerHolder.getEmailView().setText(UserConfig.user.getEmail());
    }

    private void startLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void startSettingsActivity() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void startPasscodeActivity() {
        Intent i = new Intent(this, PasscodeActivity.class);
        startActivity(i);
        finish();
    }
}

