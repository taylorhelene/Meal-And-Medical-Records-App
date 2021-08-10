package taylorhelene09gmail.diettrack;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates ;
    Calendar currentDate;
    LayoutInflater inflater;
    List<Events> eventsList;

    public MyGridAdapter(Context context, List<Date> dates, Calendar currentDate, List<Events> eventsList) {
        super(context,R.layout.singlecellrow);
        this.dates = dates;
        this.currentDate = currentDate;
        inflater = LayoutInflater.from(context);
        this.eventsList=eventsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int day = dateCalendar.get(Calendar.DATE);



        View view = convertView;
        if(view == null){

            view = inflater.inflate(R.layout.singlecellrow,parent,false);
            // clear styling
            ((TextView)view).setTypeface(null, Typeface.NORMAL);
            ((TextView)view).setTextColor(Color.BLACK);
        }

        if (displayMonth == currentMonth && displayYear==currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.colorWhite));

        } else if (day == dateCalendar.get(Calendar.DATE)) {
            // if it is today, set it to blue/bold
            ((TextView)view).setTextColor(Color.WHITE);
            ((TextView) view).setGravity(Gravity.CENTER);
            view.setBackgroundResource(R.drawable.daybackground);
        }
        else {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ((TextView) view).setTextColor(Color.parseColor("#E0E0E0"));
        }
        TextView cellNumber = view.findViewById(R.id.calendar_day);
        TextView eventText = view.findViewById(R.id.events_id);
        cellNumber.setText(String.valueOf(dayNo));
        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i  = 0;i < eventsList.size();i++){
            eventCalendar.setTime(convertStringToDate(eventsList.get(i).getDate()));
            if(dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH)+1
                    && displayYear == eventCalendar.get(Calendar.YEAR)){
                arrayList.add(eventsList.get(i).getEvent());
                eventText.setText(arrayList.size()+" events");




            }



        }




        return view;
    }


    @Override
    public int getCount() {
        return dates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }


    private Date convertStringToDate(String dateInString){
        java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
