package org.investsoft.bazar.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import org.investsoft.bazar.R;
import org.investsoft.bazar.app.presenter.WorkflowPresenter;
import org.investsoft.bazar.app.view.WorkflowView;
import org.investsoft.bazar.ui.holder.ToolbarHeaderHolder;
import org.investsoft.bazar.utils.AndroidUtils;
import org.investsoft.bazar.utils.events.EventManager;
import org.investsoft.bazar.utils.events.EventType;

public class WorkflowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        EventManager.EventReceiver, WorkflowView {

    private WorkflowPresenter presenter = null;

    private ToolbarHeaderHolder headerHolder;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_workflow);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_workflow);
        if (drawerToggle == null) {
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

                @Override
                public void onDrawerSlide(View view, float slideOffset) {
                    AndroidUtils.hideKeyboard(view);
                }

                @Override
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    presenter.onDrawerClosed();
                }
            };
        }
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.workflow_navigation);
        if (headerHolder == null) {
            headerHolder = new ToolbarHeaderHolder(navigationView.getHeaderView(0));
        }
        navigationView.setNavigationItemSelectedListener(this);

        if (presenter == null) {
            presenter = new WorkflowPresenter();
        }

        if (!presenter.isBinded()) {
            presenter.bindView(this);
        }
        presenter.init();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        presenter.onNavigationItemSelected(item.getItemId());
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onEventReceive(int eventId, Object... data) {
        presenter.onEventReceive(eventId, data);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int stackSize = getFragmentManager().getBackStackEntryCount();
            if (stackSize > 0) {
                showFragment(WorkflowFragment.getInstance(), R.string.app_name);
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
        if (!presenter.isBinded()) {
            presenter.bindView(this);
        }
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventManager.getInstance().unregisterReceiver(EventType.USER_DATA_CHANGED, this);
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter.unbindView();
    }

    @Override
    public void navigateToActivity(Class activity, boolean finishRootActivity) {
        startActivity(new Intent(this, activity));
        if (finishRootActivity) {
            finish();
        }
    }

    @Override
    public void showFragment(Fragment fragment, int titleId) {
        if (fragment == null || fragment.isVisible()) {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.workflow_container);
        FragmentTransaction tx = fragmentManager.beginTransaction();
        if (fragment instanceof WorkflowFragment) {
            if (currentFragment == null) {
                //First app init
                tx.add(R.id.workflow_container, fragment);
            } else {
                //Pop workflow fragment from stack
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        } else {
            if (currentFragment != null) {
                tx.remove(currentFragment);
            }
            tx.add(R.id.workflow_container, fragment).addToBackStack(null);
        }
        tx.commit();
        //Change toolbar title
        getSupportActionBar().setTitle(titleId);
    }

    @Override
    public void changeDrawerHeader(String name, String email) {
        headerHolder.getNameView().setText(name);
        headerHolder.getEmailView().setText(email);
    }

}

