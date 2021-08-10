package taylorhelene09gmail.diettrack;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class Homepage extends AppCompatActivity  {
    Fragment selectedFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

    androidx.appcompat.widget.Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

        TextView textView1=(TextView) findViewById(R.id.Title);
      TextView textView2=(TextView) findViewById(R.id.subTitle);
        BubbleNavigationLinearView bubbleNavigation = findViewById(R.id.bubbleNavigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Track()).commit();

        bubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position){
                    case 0:
                        selectedFragment = new  Track();
                        break;
                    case 1:
                        selectedFragment = new Symptom();
                        break;
                    case 2:
                        selectedFragment = new view_records();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
        });
    Calendar c=Calendar.getInstance();
        int timeOfDay=c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay>=0&&timeOfDay<12){
           textView1.setText("Good Morning");
        }else if(timeOfDay>=12&&timeOfDay<16){
            textView1.setText("Good Afternoon");
        }else if(timeOfDay>=16&&timeOfDay<21){
            textView1.setText("Good Evening");
        }else if(timeOfDay>=21&&timeOfDay<24){
            textView1.setText("Good Night");
        }else{ Homepage.this.setTitle("Hello");}
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE dd MMM,yyyy");
        String formattedDate=dateFormat.format(c.getTime());

        textView2.setText((formattedDate));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


}
