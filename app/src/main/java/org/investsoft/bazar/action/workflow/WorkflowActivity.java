package org.investsoft.bazar.action.workflow;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.common.ToolbarHeaderHolder;
import org.investsoft.bazar.action.login.LoginActivity;
import org.investsoft.bazar.action.passcode.PasscodeActivity;
import org.investsoft.bazar.utils.AndroidUtils;
import org.investsoft.bazar.utils.ApplicationLoader;
import org.investsoft.bazar.utils.UserConfig;
import org.investsoft.bazar.utils.events.EventManager;
import org.investsoft.bazar.utils.events.EventType;

public class WorkflowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EventManager.EventReceiver {

    private Fragment choosenFragment;
    private int choosenFragmentTitle;

    private AboutFragment aboutFragment;
    private SettingsFragment settingsFragment;
    private WorkflowFragment workflowFragment;

    private boolean loggedOut = false;

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

        fm = getSupportFragmentManager();

        setContentView(R.layout.activity_workflow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_workflow);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_workflow);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerSlide(View view, float slideOffset) {
                AndroidUtils.hideKeyboard(view);
            }

            @Override
            public void onDrawerClosed(View view) {
                if (loggedOut) {
                    UserConfig.clearPersonalInfo(true);
                    UserConfig.save();
                    startLoginActivity();
                }
                //Change fragment only when drawer closed coz of animations
                //and add to backstack only when choosed fragment is not main workflow fragment
                changeFragment(choosenFragment, choosenFragmentTitle);

                super.onDrawerClosed(view);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.workflow_navigation);
        headerHolder = new ToolbarHeaderHolder(navigationView.getHeaderView(0));
        updateNavigationHeader();
        navigationView.setNavigationItemSelectedListener(this);

        //Init fragments
        aboutFragment = new AboutFragment();
        settingsFragment = new SettingsFragment();
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
                }
                break;
            case R.id.menu_workflow_preferences:
                if (!settingsFragment.isVisible()) {
                    choosenFragment = settingsFragment;
                    choosenFragmentTitle = R.string.preferences;
                }
                break;
            case R.id.menu_workflow_main:
                if (!workflowFragment.isVisible()) {
                    choosenFragment = workflowFragment;
                    choosenFragmentTitle = R.string.app_name;
                }
                break;
            case R.id.menu_workflow_logout:
                loggedOut = true;
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onEventReceive(int eventId, Object... data) {
        if (eventId == EventType.USER_DATA_CHANGED) {
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
        if (UserConfig.appLocked) {
            Intent i = new Intent(this, PasscodeActivity.class);
            startActivity(i);
            finish();
        }
        EventManager.getInstance().registerReceiver(EventType.USER_DATA_CHANGED, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (UserConfig.passcodeEnabled && !TextUtils.isEmpty(UserConfig.passcodeHash)) {
            UserConfig.appLocked = true;
            UserConfig.save();
        }
        EventManager.getInstance().unregisterReceiver(EventType.USER_DATA_CHANGED, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!UserConfig.rememberMe && !UserConfig.appLocked) {
            UserConfig.clearPersonalInfo(true);
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
}

