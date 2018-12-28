package com.collabolab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.bumptech.glide.Glide;

public class ShowActivity extends FragmentActivity {
    private  static final int NUM_PAGES = 4;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    ImageView iv_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        ImageButton ibMenu = findViewById(R.id.ib_menu);
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent;
                        if (item.getTitle().equals("공간소개")) {
                            intent = new Intent(getApplicationContext(), ShowActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (item.getTitle().equals("예약하기")) {
                            intent = new Intent(getApplicationContext(), SetDateActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (item.getTitle().equals("사용현황")) {
                            intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (item.getTitle().equals("이용내역")) {
                            intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });

        mPager = findViewById(R.id.container);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }
            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        iv_bar.setImageDrawable(getResources().getDrawable(R.drawable.bar1)); break;
                    case 1:
                        iv_bar.setImageDrawable(getResources().getDrawable(R.drawable.bar2)); break;
                    case 2:
                        iv_bar.setImageDrawable(getResources().getDrawable(R.drawable.bar3)); break;
                    case 3:
                        iv_bar.setImageDrawable(getResources().getDrawable(R.drawable.bar4)); break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });

        iv_bar = findViewById(R.id.iv_bar);
    }

    public static class FirstFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater,container,savedInstanceState);

            View v = inflater.inflate(R.layout.fragment_1,container,false);
            ImageView ivInfo = v.findViewById(R.id.iv_info);
            Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.room1)).into(ivInfo);
            return v;
        }
    }
    public static class SecondFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater,container,savedInstanceState);

            View v = inflater.inflate(R.layout.fragment_2,container,false);
            ImageView ivInfo = v.findViewById(R.id.iv_info);
            Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.room4)).into(ivInfo);
            return v;
        }
    }
    public static class ThirdFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater,container,savedInstanceState);

            View v = inflater.inflate(R.layout.fragment_3,container,false);
            ImageView ivInfo = v.findViewById(R.id.iv_info);
            Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.room3)).into(ivInfo);
            return v;
        }
    }
    public static class FourthFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater,container,savedInstanceState);

            View v = inflater.inflate(R.layout.fragment_4,container,false);
            ImageView ivInfo = v.findViewById(R.id.iv_info);
            Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.room2)).into(ivInfo);
            return v;
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment newFragment = new Fragment();
            switch (position) {
                case 0:
                    newFragment = new FirstFragment();break;
                case 1:
                    newFragment = new SecondFragment();
                    break;
                case 2:
                    newFragment = new ThirdFragment();
                    break;
                case 3:
                    newFragment = new FourthFragment();
                    break;
            }
            return newFragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
