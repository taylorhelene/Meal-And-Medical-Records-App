package taylorhelene09gmail.diettrack;


import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class view_records extends Fragment {

    private static final String TAG = "MainActivity";
    private SharedViewModel model;
    public String file="my data";
    private Context mcontext;
    CardView cardView;
    ImageButton PreviouseButton,NextButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_Days = 42;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    List<Events> eventsList = new ArrayList<>();
    List<Date> dateList = new ArrayList<>();
    DBOpenHelper dbOpenHelper;
    AlertDialog alertDialog;
    MyGridAdapter adapter;

    public view_records() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainTabView =  inflater.inflate(R.layout.fragment_view_records, container, false);

        CurrentDate = mainTabView.findViewById(R.id.current_Date);
         cardView= mainTabView.findViewById(R.id.card);
        cardView.setPreventCornerOverlap(false);

        context=getContext();

      // final SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("set", MODE_PRIVATE);
        gridView= mainTabView.findViewById(R.id.gridview);
        PreviouseButton= mainTabView.findViewById(R.id.previousBtn);
        NextButton= mainTabView.findViewById(R.id.nextBtn);
        SetupCalendar();
        PreviouseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                SetupCalendar();

            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                SetupCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final String date = dateFormat.format(dateList.get(position));
                model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

                model.getMessage().observe(getActivity(), new Observer() {

                    @Override
                    public void onChanged(@Nullable Object updatedObject) {
                        Log.i(TAG, "onChanged: recieved freshObject");

                        if (updatedObject != null) {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            String formattedDate = dateFormat.format(c.getTime());
                            SaveEvent(updatedObject.toString(),formattedDate,date
                                    ,monthFormat.format(dateList.get(position)),yearFormat.format(dateList.get(position)));
                            SetupCalendar();
                        }


                    }
                });


                model.getbpitem().observe(getActivity(), new Observer() {

                    @Override
                    public void onChanged(@Nullable Object update) {
                        Log.i(TAG, "onChanged: recieved freshObject");
                        if (update != null) {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                            String formattedDate = dateFormat.format(c.getTime());
                            SaveEvent(update.toString(),formattedDate,date
                                    ,monthFormat.format(dateList.get(position)),yearFormat.format(dateList.get(position)));
                            SetupCalendar();

                        }

                    }
                });
                model.getmeditem().observe(getActivity(), new Observer() {

                    @Override
                    public void onChanged(@Nullable Object updated) {
                        Log.i(TAG, "onChanged: recieved freshObject");
                        if (updated != null) {

                        }
                    }
                });
                model.getList().observe(getActivity(), new Observer() {

                            @Override
                            public void onChanged(@Nullable Object updatesList) {
                                Log.i(TAG, "onChanged: recieved freshObject");
                                if (updatesList != null) {

                                }
                            }
                        }

                );
                model.geSlideitem().observe(getActivity(), new Observer() {

                            @Override
                            public void onChanged(@Nullable Object slide) {
                                Log.i(TAG, "onChanged: recieved freshObject");
                                if (slide != null) {

                                }
                            }
                        }

                );
                model.geSlideitem2().observe(getActivity(), new Observer() {

                            @Override
                            public void onChanged(@Nullable Object slide2) {
                                Log.i(TAG, "onChanged: recieved freshObject");
                                if (slide2 != null) {

                                }
                            }
                        }

                );
                model.geSlideitem3().observe(getActivity(), new Observer() {

                            @Override
                            public void onChanged(@Nullable Object slide3) {
                                Log.i(TAG, "onChanged: recieved freshObject");

                                if (slide3 != null) {

                                }
                            }
                        }

                );



                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setCancelable(true);
                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.showeventslayout,null);
                RecyclerView EventRV= (RecyclerView) showView.findViewById(R.id.EventsRV);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                EventRV.setLayoutManager(layoutManager);
                EventRV.setHasFixedSize(true);

                EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext()
                        ,CollectEvent(date));
                EventRV.setAdapter(eventRecyclerAdapter);
                eventRecyclerAdapter.notifyDataSetChanged();
                builder2.setView(showView);
                alertDialog =builder2.create();
                alertDialog.show();



            }
        });



        return mainTabView;
    }

    /*private void LoadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("set", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list", "");
        if(json.isEmpty()){

        }else{
            Type type = new TypeToken<ArrayList<Event>>() {}.getType();
           ArrayList<Event> arrdata= gson.fromJson(json, type);
           if (!arrdata.isEmpty()) {
               for (int i = 0; i < arrdata.size(); i++){
                   Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
                   compactCalendarView.getEvents(currentCalender.getTime());
                   compactCalendarView.addEvent(arrdata.get(i));
              }

           }


        }
    }*/
    private void Data() {

    }


    @Override
    public void onResume() {
        super.onResume();

    }

public void LoadData( final int position){


}




    private void SetupCalendar(){
        String StartDate = simpleDateFormat.format(calendar.getTime());
        CurrentDate.setText(StartDate);
        dateList.clear();
        Calendar monthCalendar = (Calendar)calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayOfMonth);


        COllectEventsPerMonth(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));


        while (dateList.size() < MAX_CALENDAR_Days){
            dateList.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);

        }
        adapter = new MyGridAdapter(context,dateList,calendar,eventsList);
        gridView.setAdapter(adapter);


    }
    private void COllectEventsPerMonth(String Month,String Year){
        eventsList.clear();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEventsperMonth(Month,Year,database);
        while (cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String Time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events = new Events(event,Time,Date,month,year);
            eventsList.add(events);
        }
    }
    private ArrayList<Events> CollectEvent(String date){
        ArrayList<Events> arrayList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase sqLiteDatabase = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEvents(date,sqLiteDatabase);
        while (cursor.moveToNext()){
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String Time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events = new Events(event,Time,Date,month,year);
            arrayList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();
// Toast.makeText(context, String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();

        return arrayList;
    }
    private void SaveEvent(String event,String time,String date,String Month,String Year){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event,time,date,Month,Year,database);
        dbOpenHelper.close();
        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
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
