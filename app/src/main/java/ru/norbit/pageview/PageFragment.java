package ru.norbit.pageview;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by safarali.alisultanov on 13.10.2016.
 */
public class PageFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_NEXT_PAGE = "arg_next_page";

    @InjectView(R.id.monday) TextView monday;
    @InjectView(R.id.tuesday) TextView tuesday;
    @InjectView(R.id.wednesday) TextView wednesday;
    @InjectView(R.id.thursday) TextView thursday;
    @InjectView(R.id.friday) TextView friday;
    @InjectView(R.id.saturday) TextView saturday;
    @InjectView(R.id.sunday) TextView sunday;

    int pageNumber;
    int backColor;
    Calendar cal;

    static PageFragment newInstance(int page, Calendar c) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putSerializable(ARGUMENT_NEXT_PAGE, c);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        cal = (Calendar) getArguments().getSerializable(ARGUMENT_NEXT_PAGE);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        String[] days = getWeekOfMonth(day,month,year);
        TextView monday = (TextView)view.findViewById(R.id.monday);
        TextView tuesday = (TextView)view.findViewById(R.id.tuesday);
        TextView wednesday = (TextView)view.findViewById(R.id.wednesday);
        TextView thursday = (TextView)view.findViewById(R.id.thursday);
        TextView friday = (TextView)view.findViewById(R.id.friday);
        TextView saturday = (TextView)view.findViewById(R.id.saturday);
        TextView sunday = (TextView)view.findViewById(R.id.sunday);

        monday.setText(days[Constant.monday]);
        tuesday.setText(days[Constant.tuesday]);
        wednesday.setText(days[Constant.wednesday]);
        thursday.setText(days[Constant.thursday]);
        friday.setText(days[Constant.friday]);
        saturday.setText(days[Constant.saturday]);
        sunday.setText(days[Constant.sunday]);


//        TextView tvPage = (TextView) view.findViewById(R.id.asthma_action_plan);
//        tvPage.setText("Page " + pageNumber);
//        tvPage.setBackgroundColor(backColor);

        return view;
    }

    public static String[] getWeekOfMonth(int day, int month, int year) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String[] days = new String[7];

        now.set(year, month, day);
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];
        int delta = -(now.get(GregorianCalendar.DAY_OF_WEEK)-2);
        if (delta > 0)
            delta = -6;
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = sdf.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);   // Сдвиг даты на 1. Все равно, что date++
        }
        return days;
    }
}
