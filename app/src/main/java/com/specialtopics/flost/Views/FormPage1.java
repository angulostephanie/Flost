package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

public class FormPage1 extends Fragment {
    TextView title;
    FormActivity activity;
    Button find_button;
    Button lost_button;
    boolean lostItem;
    Item newItem;
    Fragment nextFrag;
    int page;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        newItem = getArguments().getParcelable("data");
        page = getArguments().getInt("page");
        View view = inflater.inflate(R.layout.form_page1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        nextFrag = Utils.getNextFrag(getActivity(), page);
        activity = (FormActivity) getActivity();
        find_button = view.findViewById(R.id.find_item_btn);
        lost_button = view.findViewById(R.id.lost_item_btn);

        activity.hideNextButton();

        find_button.setOnClickListener(createOnClickListeners());
        lost_button.setOnClickListener(createOnClickListeners());

        title = view.findViewById(R.id.tv_q1);
        title.setText(Utils.getQ1());

    }
    public View.OnClickListener createOnClickListeners() {
        View.OnClickListener btnClickListeners = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.find_item_btn:
                        focusButton(find_button, lost_button);
                        newItem.setType("found");
                        ((FormPage2) nextFrag).onFormDataReceived(newItem);
                        break;
                    case R.id.lost_item_btn:
                        focusButton(lost_button, find_button);
                        newItem.setType("lost");
                        ((FormPage2) nextFrag).onFormDataReceived(newItem);
                        break;
                }
                activity.showNextButton();
            }
        };
        return btnClickListeners;
    }

    public void focusButton(Button focusButton, Button unfocusButton){
        focusButton.setBackgroundColor(getResources().getColor(R.color.themecolor));
        focusButton.setTextColor(getResources().getColor(R.color.white));
        unfocusButton.setBackgroundColor(getResources().getColor(R.color.white));
        unfocusButton.setTextColor(getResources().getColor(R.color.black));
    }
}
