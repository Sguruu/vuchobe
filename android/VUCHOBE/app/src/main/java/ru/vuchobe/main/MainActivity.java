package ru.vuchobe.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import ru.vuchobe.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager2 pages;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();

        pages.setAdapter(new PagesAdapter(getSupportFragmentManager(), getLifecycle()));
        pages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //TODO SET BUTTON NAVIGATION
            }
        });


    }

    private void initUI() {
        pages = findViewById(R.id.viewPagesId);
        bottomNavigationView = findViewById(R.id.mainBottomNavigationId);
    }

    private static class PagesAdapter extends FragmentStateAdapter {

        public PagesAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return null;
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
