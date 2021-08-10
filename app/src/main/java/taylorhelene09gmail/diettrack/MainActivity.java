package taylorhelene09gmail.diettrack;


import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

 ImageView imageView;
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    private ArrayList layouts;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Button btnGotIt;
    private prefManagerMain prefManager;
    private  int mCurrentPage;
    private LinearLayout mDotsLayout;
    private TextView[] mDots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checking for first time launch - before calling setContentView()
        // Checking for first time launch - before calling setContentView()
        prefManager = new prefManagerMain(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        btnGotIt = (Button) findViewById(R.id.btnOrder);
        mDotsLayout = findViewById(R.id.layoutDots);
        models = new ArrayList<>();
        models.add(new Model(R.drawable.icon_eat, "Diet records", "Record your meals and insulin injections."));
        models.add(new Model(R.drawable.icon_code, "Health diary", "A health diary for recording symptoms and blood readings."));
        models.add(new Model(R.drawable.calendar, "Trace archives", "Track your records and add notifications for doctor's appointment." ));

        adapter = new Adapter(models, this);
        addDotsIndicator(0);

        viewPager = findViewById(R.id.viewPager5);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(20, 30, 20, 90);
        //viewPager.setPageTransformer(true, new HorizontalStackTransformer() );


        Integer[] colors_temp = {
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



                viewPager.setBackgroundResource(R.color.colorWhite); }

            @Override
            public void onPageSelected(int position) {
                addDotsIndicator(position);

                mCurrentPage = position;

                if (position == 0){//we are on first page

                } else if (position == mDots.length - 1){ //last page

                } else { //neither on first nor on last page

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });



    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(MainActivity.this,Homepage.class));
        finish();
    }
    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotsLayout.removeAllViews(); //without this multiple number of dots will be created

        for (int i = 0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;")); //code for the dot icon like thing
            mDots[i].setTextSize(20);
            mDots[i].setTextColor(getResources().getColor(R.color.colorBlack));

            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length>0){
            mDots[position] .setTextColor(getResources().getColor(R.color.colorAlmostWhite)); //setting currently selected dot to white
        }
    }

    public void onPage() {

        int pos=adapter.getCount();
        if (pos < (adapter.getCount() +1)) {
            viewPager.setCurrentItem(pos);
        } else {
            launchHomeScreen();
        }


    };
}