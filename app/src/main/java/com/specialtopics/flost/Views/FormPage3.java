package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public static FormPage3 newInstance(int page) {
        FormPage3 fragment = new FormPage3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_page3, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        FormActivity activity = (FormActivity) getActivity();
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
                activity.showNextButton();
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
        if(newItem != null) {
            verb = newItem.getType();
            if(verb == "lost")
                verb = "lose";
            else
                verb = "find";
            title.setText(Utils.getQ3(verb));
        }
    }

}
