package taylorhelene09gmail.diettrack;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public  class Track extends Fragment {


    public Track() {
        // Required empty public constructor
    }

private FloatingActionButton  add,delete,meal,update;
    private LinearLayout mealy,updatey;
    AlertDialog.Builder alertDialog;
    ListView morning;
    ImageView emptylist;
    ArrayList<String>list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    LottieAnimationView animationView;
    LinearLayout linearLayout;
    SharedPreferences sharedPreferences;
   int noteId;
private Context mcontext;


    private SharedViewModel model;
    private viewmodel mode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View v= inflater.inflate(R.layout.fragment_track, container, false);
        loadData();
        CardView cardView=v.findViewById(R.id.card);
        cardView.setPreventCornerOverlap(false);
        //get the bottom sheet view
        linearLayout = v.findViewById(R.id.bottom_sheet);

        add=(FloatingActionButton)v.findViewById(R.id.fab);
       meal= (FloatingActionButton)v.findViewById(R.id.fab3);
       delete=(FloatingActionButton) v.findViewById(R.id.fab4);
        update=(FloatingActionButton) v.findViewById(R.id.fabupdate);
       mealy=(LinearLayout) v.findViewById(R.id.lay2);
        updatey=(LinearLayout) v.findViewById(R.id.lay3);
       morning=(ListView) v.findViewById(R.id.listview1);
        emptylist= (ImageView) v.findViewById(R.id.emptyitem);
       animationView=(LottieAnimationView)v.findViewById(R.id.lottie);
mcontext=getContext();


        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("shared preferences", MODE_PRIVATE);


        if( getActivity()!=null)
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
        mode = ViewModelProviders.of(getActivity()).get(viewmodel.class);

        mode.getListitem().observe(requireActivity(), new Observer(){

            @Override
            public void onChanged(@Nullable Object updatedObject ) {
                Log.i(TAG, "onChanged: recieved freshObject");
                if (updatedObject != null) {
                   // list.clear();
                    Calendar c=Calendar.getInstance();
                    SimpleDateFormat dateFormat=new SimpleDateFormat("h:mm a ");
                    String formattedDate=dateFormat.format(c.getTime());
                    list.add(formattedDate + updatedObject.toString());
                    adapter.notifyDataSetChanged();
                    getSharedpref();
                    updatedObject=null;

                }
            }
        });

       add.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {

              mealy.setVisibility(View.VISIBLE);
              delete.setVisibility(View.VISIBLE);
              updatey.setVisibility(View.VISIBLE);
               add.setVisibility(View.INVISIBLE);
           }
       });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                mealy.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
                updatey.setVisibility(View.INVISIBLE);
                add.setVisibility(View.VISIBLE);
            }
        });
        meal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //init the bottom sheet view
                new CustomBottomSheetDialogFragment().show(getFragmentManager(),"Dialog");



            }

        });

        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //init the dialog
                // Create a alert dialog builder.
               final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
               LayoutInflater inflater1=getActivity().getLayoutInflater();
               //builder.setView(inflater1.inflate(R.layout.customview,null));
               View convertView =inflater1.inflate(R.layout.customview, null);

                Button buttonyes=convertView.findViewById(R.id.button1);
               Button buttonno=convertView.findViewById(R.id.button2);
               builder.setView(convertView);
                final AlertDialog alert = builder .create();

               buttonyes.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (!list.isEmpty()) {
                            String lis= list.toString();
                            model.setList(lis);
                            Toast.makeText(getActivity(),"done",Toast.LENGTH_LONG).show();
                            list.clear();
                            Gson gson = new Gson();
                            adapter.notifyDataSetChanged();
                            String json = sharedPreferences.getString("task list", null);
                            if(json== null){
                            }else{
                                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                                list = gson.fromJson(json, type);
                                }
                            SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getContext());
                                  preferences.edit().remove("shared preferences").apply();


                            FragmentTransaction fragmentTransaction = getActivity()
                                    .getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, new view_records());
                            fragmentTransaction.commit();
                            alert.dismiss();

                        }else{
                            alert.dismiss();
                        }

                    }

                });
               buttonno.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        alert.dismiss();


                    }

                });

                alert.show();


            }

        });
        morning.setEmptyView(animationView);
        morning.setEmptyView(emptylist);

        morning.setAdapter(adapter);

        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        Calendar c=Calendar.getInstance();


       return v;
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        if(json== null){
            Toast.makeText(getActivity(),"No data",Toast.LENGTH_LONG).show();
        }else{
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        list = gson.fromJson(json, type);
        if (list == null) {
            list = new ArrayList<>();
        }}
    }
    private void getSharedpref(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("task list", json);
        editor.apply();

    }
}
