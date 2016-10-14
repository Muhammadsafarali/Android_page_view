package ru.norbit.pageview;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends FragmentActivity {

    static final String LOG_TAG = "MainActivity";
    static final int PAGE_COUNT = 3;

    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();

        // Индекс дня недели
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];
//        Log.e(LOG_TAG, "День Недели2 ->" + weekday);

        // Получить неделю с Пн по Вс
        String[] days = getWeekOfMonth(14,Calendar.OCTOBER,2016);
        for (int i = 0; i < days.length; i++) {
            Log.e(LOG_TAG, days[i]);
        }

        pager = (ViewPager)findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(100/2);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(LOG_TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public static String[] getWeekOfMonth(int day, int month, int year) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        String[] days = new String[7];

        now.set(year, month, day);
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];
//        Log.e(LOG_TAG, "День Недели2 ->" + weekday);
//        Log.e(LOG_TAG, "dayOfWeek -> " + String.valueOf(dayOfWeek));
//        Log.e(LOG_TAG, "DAY_OF_WEEK -> " + now.get(GregorianCalendar.DAY_OF_WEEK));
        int delta = -(now.get(GregorianCalendar.DAY_OF_WEEK)-2);
        if (delta > 0)
            delta = -6;
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = sdf.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        return days;
    }

    private class MyFragmentPagerAdapter extends MyFragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
//            Log.e(LOG_TAG, "Count page_view -> " + String.valueOf(100));
            return 100;
        }
    }

}
