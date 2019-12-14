package com.spaceshipfreehold.tirecorrector;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

public class TireUtilityActivity extends AppCompatActivity implements ITireUtility.View{

    private ITireUtility.Presenter mPresenter;
    private TireUtilityAdapter mUtilitiesAdapter;
    private ViewPager mViewPager;
    private TireCorrectionFragment mTireCorrectionFragment;
    private TireSizeFragment mTireSizeFragment;
    private IdealAxleRatioFragment mIdealAxleRatioFragment;
    private TireFitmentFragment mTireFitmentFragment;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new TireUtilityPresenter(new TireUtilitiesModel(this), this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUtilitiesAdapter = new TireUtilityAdapter(this.getSupportFragmentManager());
        mViewPager = findViewById(R.id.tire_view_pager);
        mViewPager.setAdapter(mUtilitiesAdapter);
        mViewPager.addOnPageChangeListener(new UtilityChangeListener());

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tire_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Simple.
        mTireCorrectionFragment = new TireCorrectionFragment();
        mTireSizeFragment = new TireSizeFragment();
        mIdealAxleRatioFragment = new IdealAxleRatioFragment();
        //mTireFitmentFragment = new TireFitmentFragment();

        // Give our viewpager references to our associated fragments to display.
        mUtilitiesAdapter.addUtility(mTireCorrectionFragment);
        mUtilitiesAdapter.addUtility(mTireSizeFragment);
        mUtilitiesAdapter.addUtility(mIdealAxleRatioFragment);

        //mUtilitiesAdapter.addUtility(mTireFitmentFragment);
        mUtilitiesAdapter.notifyDataSetChanged(); // required when adding or removing fragments.

        mPresenter.onStarted();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mOptionsMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Prepare.
        mPresenter.onOptionsMenuPrepared();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectionId = item.getItemId();
        if(selectionId == R.id.imperial_unit_item){
            mPresenter.imperialUnitsSelected();
        } else if(selectionId == R.id.metric_unit_item){
            mPresenter.metricUnitsSelected();
        } else if (selectionId == R.id.dark_theme_item){
            mPresenter.darkThemeSelected();
        } else if (selectionId == R.id.light_theme_item){
            mPresenter.lightThemeSelected();
        } else if (selectionId == R.id.about_application_item) {
            mPresenter.aboutOptionSelected();
        }

        return true;
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goToUtility(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void setThemeDark() { setTheme(R.style.SpaceshipFreeholdTheme_Dark); }

    @Override
    public void setThemeLight() {
        setTheme(R.style.SpaceshipFreeholdTheme_Light);
    }

    @Override
    public void enableLightThemeOption(boolean enabled) {
        if(mOptionsMenu != null) {
            MenuItem item = mOptionsMenu.findItem(R.id.light_theme_item); // Define items in XML having ids. add and find via id not by string.
            item.setVisible(enabled);
        }
    }

    @Override
    public void enableDarkThemeOption(boolean enabled) {
        if(mOptionsMenu != null) {
            MenuItem item = mOptionsMenu.findItem(R.id.dark_theme_item);
            item.setVisible(enabled);
        }
    }

    @Override
    public void enableImperialUnitsOption(boolean enabled) {
        if(mOptionsMenu != null) {
            MenuItem item = mOptionsMenu.findItem(R.id.imperial_unit_item);
            item.setVisible(enabled);
        }
    }

    @Override
    public void enableMetricUnitsOption(boolean enabled) {
        if(mOptionsMenu != null) {
            MenuItem item = mOptionsMenu.findItem(R.id.metric_unit_item);
            item.setVisible(enabled);
        }
    }

    @Override
    public void recreateView() {
        // Recreate the activity and its views. Useful for applying settings? changes.
        recreate();
    }

    @Override
    public void displayAboutMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogFragment aboutFragment = new AboutDialogFragment();
        aboutFragment.show(fragmentManager, "About Fragment");
    }

    private class UtilityChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){ }

        @Override
        public void onPageSelected(int position) {
            mPresenter.onUtilitySelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    }
}
