package ru.vuchobe.main;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ru.vuchobe.R;
import ru.vuchobe.util.threadUtil.ThreadFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends ThreadFragment {

    private CoordinatorLayout main;
    private Toolbar toolbar;
    private LinearLayout settingMain;

    public SettingFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        initUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    /**
     * find all uses UI elements
     * Поиск всех UI элементов
     */
    private void initUI() {
        main = getView().findViewById(R.id.mainId);                                                 //Container all next UI elements (Контейнер всех следующих UI)
        toolbar = getView().findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        settingMain = getView().findViewById(R.id.setting_main_id);
    }
}
