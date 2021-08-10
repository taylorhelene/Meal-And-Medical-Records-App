package taylorhelene09gmail.diettrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private viewmodel mode;
    @Override
    public void onCreate(@Nullable Bundle savedInstaneState){
        super.onCreate(savedInstaneState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.CustomBottomSheetDialogTheme);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_box_prompt, container,false);


        mode = ViewModelProviders.of(getActivity()).get(viewmodel.class);
        final EditText userInput=(EditText)v.findViewById(R.id.input);
         Button button=(Button)v.findViewById(R.id.save);
        ImageView imageView =(ImageView) v.findViewById(R.id.image);
        imageView.bringToFront();

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar c=Calendar.getInstance();
                SimpleDateFormat dateFormat=new SimpleDateFormat("h:mm a ");
                String formattedDate=dateFormat.format(c.getTime());
                String addsym= userInput.getText().toString();
                mode.setListitem(addsym);
                Toast.makeText(getActivity(),"Done",Toast.LENGTH_SHORT).show();
                userInput.setText("");
               dismiss();


            }

        });
        return v;
    }

}
