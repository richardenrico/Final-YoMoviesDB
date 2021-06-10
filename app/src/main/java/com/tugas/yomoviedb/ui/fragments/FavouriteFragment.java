package com.tugas.yomoviedb.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tugas.yomoviedb.R;
import com.tugas.yomoviedb.ui.adapters.ViewPagerAdapter;

public class FavouriteFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());

        ViewPager2 viewPager2 = view.findViewById(R.id.vp_fav);
        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(2);
        viewPager2.getAdapter().notifyDataSetChanged();
        viewPager2.setCurrentItem(0);

        TabLayout tabLayout = view.findViewById(R.id.tab_fav);
        new TabLayoutMediator(tabLayout, viewPager2,
                ((tab, position) -> tab.setText("OBJECT" + (position + 1)))
        ).attach();

        (tabLayout.getTabAt(0)).setText("Movie");
        (tabLayout.getTabAt(1)).setText("TV Show");

        return view;
    }
}