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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import org.investsoft.bazar.R;
import org.investsoft.bazar.action.login.LoginActivity;
import org.investsoft.bazar.utils.AndroidUtils;
import org.investsoft.bazar.utils.ApplicationLoader;
import org.investsoft.bazar.utils.UserConfig;

public class WorkflowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment choosenFragment;
    private int choosenFragmentTitle;

    private AboutFragment aboutFragment;
    private SettingsFragment settingsFragment;
    private WorkflowFragment workflowFragment;

    private boolean loggedOut = false;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationLoader.initApplication();
        if (UserConfig.sessionId == null) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
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
                    UserConfig.clearPersonalInfo();
                    Intent i = new Intent(ApplicationLoader.applicationContext, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                //Change fragment only when drawer closed coz of animations
                changeFragment(choosenFragment, choosenFragmentTitle, true);
                super.onDrawerClosed(view);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_workflow);
        navigationView.setNavigationItemSelectedListener(this);

        //Init fragments
        aboutFragment = new AboutFragment();
        settingsFragment = new SettingsFragment();
        workflowFragment = new WorkflowFragment();
        fm.beginTransaction()
                .add(R.id.container_workflow, workflowFragment)
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int stackSize = fm.getBackStackEntryCount();
            if (stackSize > 0) {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                changeFragment(workflowFragment, R.string.app_name, false);
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
    public void onDestroy() {
        if (!UserConfig.rememberMe) {
            UserConfig.clearPersonalInfo();
        }
        super.onDestroy();
    }

    private void changeFragment(Fragment fragment, int titleId, boolean addToBackStack) {
        if (fragment == null) {
            return;
        }
        FragmentTransaction tx = fm.beginTransaction().replace(R.id.container_workflow, fragment);
        if (addToBackStack) {
            tx.addToBackStack(null);
        }
        tx.commit();
        //Change toolbar title
        getSupportActionBar().setTitle(titleId);
        choosenFragment = null;
    }
}

