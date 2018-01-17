package com.leon.skillshare.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.leon.skillshare.R;
import com.leon.skillshare.activities.adapters.SectionsPageAdapter;
import com.leon.skillshare.fragments.AboutFragment;
import com.leon.skillshare.fragments.CourseSearchFragment;
import com.leon.skillshare.fragments.OfferCourseFragment;
import com.leon.skillshare.fragments.UserDetailsFragment;

public class MainActivity extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAndInitTabs();
    }

    private void setupAndInitTabs() {
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        drawIconsOnTabs(tabLayout);
    }

    private void setupViewPager(ViewPager viewPager) {
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        sectionsPageAdapter.addFragment(new UserDetailsFragment());
        sectionsPageAdapter.addFragment(new CourseSearchFragment());
        sectionsPageAdapter.addFragment(new OfferCourseFragment());
        sectionsPageAdapter.addFragment(new AboutFragment());

        viewPager.setAdapter(sectionsPageAdapter);
    }

    private void drawIconsOnTabs(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(android.R.drawable.ic_menu_myplaces);
        tabLayout.getTabAt(1).setIcon(android.R.drawable.ic_menu_search);
        tabLayout.getTabAt(2).setIcon(android.R.drawable.ic_input_add);
        tabLayout.getTabAt(3).setIcon(android.R.drawable.ic_menu_help);
    }
}
