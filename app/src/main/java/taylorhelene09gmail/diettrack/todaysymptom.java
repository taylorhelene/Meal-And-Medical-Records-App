package taylorhelene09gmail.diettrack;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


/**
 * A simple {@link Fragment} subclass.
 */
public class todaysymptom extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Oneslide= "OneKey";
    public static final String Twoslide = "TwoKey";
    public static final String Threeslide ="ThreeKey";

    SharedPreferences sharedpreferences;

    public todaysymptom() {
        // Required empty public constructor
    }
SeekBar one,two,three;
    TextView textView1,textView2,textView3;
    EditText add;
    Button additem;
    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;

    private SharedViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_todaysymptom, container, false);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);


        add= v.findViewById(R.id.addsymptom);
      additem= v.findViewById(R.id.floatingActionButto);
      additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( textView1==null || textView2==null || textView3==null){
                    Toast.makeText(getContext(),"no value in symptom",Toast.LENGTH_SHORT).show();
                }else if(textView1!=null && textView2!=null && textView3!=null ){
                    String addsym= add.getText().toString();
                    String addone= textView1.getText().toString();
                    String addtwo= textView2.getText().toString();
                    String addthree= textView3.getText().toString();
                    model.setItem("Symptom: "+addsym);
                    model.setSlideitem("migraine: "+addone);
                    model.setSlideitem2("Fatigue: "+addtwo);
                    model.setSlideitem3("Increased thirst: "+addthree);
                    Toast.makeText(getContext(),"Done",Toast.LENGTH_SHORT).show();
                    add.setText("");


                }


            }
        });


        one=v.findViewById(R.id.seek1);
        one.setProgress(0);
        one.incrementProgressBy(50);
        one.setMax(100);
        two=v.findViewById(R.id.seek2);
       two.setProgress(0);
        two.incrementProgressBy(50);
        two.setMax(100);
        three=v.findViewById(R.id.seek3);
        three.setProgress(0);
        three.incrementProgressBy(50);
        three.setMax(100);
        textView1=v.findViewById(R.id.text1);
        textView2=v.findViewById(R.id.text2);
        textView3=v.findViewById(R.id.text3);

        one.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int step=50;
                i=((int)Math.round(i/step))*step;
                textView1.setText(""+i+"%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
       two.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int j, boolean b) {
                int step=50;
                j=((int)Math.round(j/step))*step;
                textView2.setText(""+j+"%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
       three.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int k, boolean b) {
                int step=50;
                k=((int)Math.round(k/step))*step;
                textView3.setText(""+k+"%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return v;
    }




    private void updateViewModel(String yourItem){
        model.setItem(yourItem);
    }


}
