package ru.vuchobe.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
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

    ViewPager2 pages;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initUI();

        if(pages.getAdapter() == null) {
            pages.setAdapter(new PagesAdapter(getSupportFragmentManager(), getLifecycle()));
            pages.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    //TODO SET BUTTON NAVIGATION
                    int selectId;
                    switch (position){
                        case 1: selectId = R.id.main_menu_bottom_university; break;
                        case 2: selectId = R.id.main_menu_bottom_time_table; break;
                        case 3: selectId = R.id.main_menu_bottom_profile; break;
                        case 0:
                        default: selectId = R.id.main_menu_bottom_events; break;
                    }
                    bottomNavigationView.setSelectedItemId(selectId);
                }
            });
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int position;
            switch (item.getItemId()){
                default:
                case R.id.main_menu_bottom_events: position = 0; break;
                case R.id.main_menu_bottom_university: position = 1; break;
                case R.id.main_menu_bottom_time_table: position = 2; break;
                case R.id.main_menu_bottom_profile: position = 3; break;
            }
            if(position >= 0 && position < pages.getAdapter().getItemCount()){
                pages.setCurrentItem(position);
                return true;
            }
            return false;
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
            switch (position) {
                case 0: return EventListFragment.newInstance();
                case 1: return UniversityFragment.newInstance();
                case 2: return TimetableFragment.newInstance();
                case 3: return ProfileFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
