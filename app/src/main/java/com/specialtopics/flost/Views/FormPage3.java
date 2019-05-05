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

public class FormPage3 extends Fragment implements OnFormDataListener {
    TextView title;
    String verb;
    Spinner spinner;
    Item newItem;
    int page;
    Fragment nextFrag;
    FormActivity activity;
    boolean answered = false;

    public static FormPage3 newInstance(int page) {
        FormPage3 fragment = new FormPage3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = (FormActivity) getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if(!answered)
                activity.hideNextButton();
            else
                activity.showNextButton();
        }
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        page = getArguments().getInt("page");
        View view = inflater.inflate(R.layout.form_page3, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        title = view.findViewById(R.id.tv_q3);
        nextFrag = Utils.getNextFrag(getActivity(), page);

        spinner = view.findViewById(R.id.spinner_building);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.buildings_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String chosenItem = (String) parent.getSelectedItem();
                if(newItem!=null) {
                    newItem.setLocation(chosenItem);
                    activity.showNextButton();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                activity.hideNextButton();
            }
        });


    }


    @Override
    public void onFormDataReceived(Item item) {
        newItem = item;
        verb = Utils.getVerb(newItem.getType());
        title.setText(Utils.getQ3(verb));
        Log.d("test", item.getName());

    }

    @Override
    public void passDataThrough() {
        ((FormPage4) nextFrag).onFormDataReceived(newItem);
        answered = true;
    }

}