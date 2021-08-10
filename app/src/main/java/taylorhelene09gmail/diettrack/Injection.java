package taylorhelene09gmail.diettrack;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


/**
 * A simple {@link Fragment} subclass.
 */
public class Injection extends Fragment {


    public Injection() {
        // Required empty public constructor
    }
    private SharedViewModel model;
    EditText bpmeasure, medication;
    String addbp,addmed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_injection, container, false);
        Button save=v.findViewById(R.id.floatingActionButton);
      bpmeasure=v.findViewById(R.id.bpmeasure);
       medication=v.findViewById(R.id.medication);
        CardView cardView=v.findViewById(R.id.cardview);
        cardView.setPreventCornerOverlap(false);
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
       save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( bpmeasure==null && medication==null ){
                    Toast.makeText(getContext(),"no entry",Toast.LENGTH_SHORT).show();
                }else if( bpmeasure==null  && medication!=null ){
                    Toast.makeText(getContext(),"Add blood preesure measure",Toast.LENGTH_SHORT).show();
                }
                else if( medication==null &&  bpmeasure!=null  ){
                    Toast.makeText(getContext(),"Add medication",Toast.LENGTH_SHORT).show();
                }else if( bpmeasure!=null && medication!=null ){
                    addbp= bpmeasure.getText().toString();
                   addmed=medication.getText().toString();
                    model.setbpitem(addbp);
                    model.setmeditem(addmed);
                    Toast.makeText(getContext(),"Done",Toast.LENGTH_SHORT).show();
                    bpmeasure.setText("");
                   medication.setText("");


                }



            }
        });

        return v;
    }

}
