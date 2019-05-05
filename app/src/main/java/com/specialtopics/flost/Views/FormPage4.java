package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.OnFormDataListener;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

import java.util.Arrays;

public class FormPage4 extends Fragment implements OnFormDataListener {
    TextView title;
    String verb;
    Item newItem;
    FormActivity activity;
    Spinner spinnerDay, spinnerTime;
    int page;
    Fragment nextFrag;

    public static FormPage4 newInstance() {
        FormPage4 fragment = new FormPage4();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (FormActivity) getActivity();
        View view = inflater.inflate(R.layout.form_page4, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        title = view.findViewById(R.id.tv_q4);
        nextFrag = Utils.getNextFrag(getActivity(), page);
        setUpSpinnerDay(view);
        setUpSpinnerTime(view);


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

        }

    }

    public void setUpSpinnerDay(View view){
        String[] days = Utils.getRecentDays();
        spinnerDay = view.findViewById(R.id.spinner_day);
        Log.d("days", Arrays.toString(days));
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                days
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenItem = (String) parent.getSelectedItem();

                if(newItem != null) {
                    newItem.setInputDay(chosenItem);
                    ((FormPage5) nextFrag).onFormDataReceived(newItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void setUpSpinnerTime(View view){
        spinnerTime = view.findViewById(R.id.spinner_time);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapter);
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenItem = (String) parent.getSelectedItem();

                if(newItem != null) {
                    newItem.setInputTime(chosenItem);
                    ((FormPage5) nextFrag).onFormDataReceived(newItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onFormDataReceived(Item item) {
        newItem = item;
        if(newItem != null) {
            verb = Utils.getVerb(newItem.getType());
            title.setText(Utils.getQ4(verb));
            Log.d("test", item.getLocation());
        }

    }
}