package org.investsoft.bazar.action.workflow;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import org.investsoft.bazar.R;

public class WorkflowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AboutFragment aboutFragment;
    private SettingsFragment settingsFragment;
    private WorkflowFragment workflowFragment;

    private DrawerLayout drawerLayout;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();

        setContentView(R.layout.activity_workflow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_workflow);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_workflow);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

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
        FragmentTransaction tx = fm.beginTransaction();
        boolean fragmentChanged = false;
        switch (id) {
            case R.id.menu_workflow_about:
                if (!aboutFragment.isVisible()) {
                    tx.replace(R.id.container_workflow, aboutFragment);
                    fragmentChanged = true;
                }
                break;
            case R.id.menu_workflow_settings:
                if (!settingsFragment.isVisible()) {
                    tx.replace(R.id.container_workflow, settingsFragment);
                    fragmentChanged = true;
                }
                break;
        }
        if (fragmentChanged) {
            tx.addToBackStack(this.getClass().getSimpleName());
        }
        tx.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int stackSize = fm.getBackStackEntryCount();
            if (stackSize == 1) {
                fm.popBackStack();
            } else if (stackSize > 1) {
                //Clear all stack
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.beginTransaction()
                        .replace(R.id.container_workflow, workflowFragment)
                        .commit();
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

}
