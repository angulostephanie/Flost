package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

public class FormPage1 extends Fragment {
    TextView title;
    Button find_button;
    Button lost_button;
    boolean lostItem;

    public static FormPage1 newInstance(int page) {
        FormPage1 fragmentFirst = new FormPage1();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.form_page1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        FormActivity activity = (FormActivity) getActivity();
        find_button = view.findViewById(R.id.find_item_btn);
        lost_button = view.findViewById(R.id.lost_item_btn);

        activity.hideNextButton();
        activity.hidePrevButton();

        View.OnClickListener btnClickListeners = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.find_item_btn:
                        focusButton(find_button, lost_button);
                        lostItem = false;
                        break;
                    case R.id.lost_item_btn:
                        focusButton(lost_button, find_button);
                        lostItem = true;
                        break;
                }
                activity.showNextButton();
            }
        };

        find_button.setOnClickListener(btnClickListeners);
        lost_button.setOnClickListener(btnClickListeners);

        title = view.findViewById(R.id.tv_q1);
        title.setText(Utils.getQ1());

    }
    public void focusButton(Button focusButton, Button unfocusButton){
        focusButton.setBackgroundColor(getResources().getColor(R.color.themecolor));
        focusButton.setTextColor(getResources().getColor(R.color.white));
        unfocusButton.setBackgroundColor(getResources().getColor(R.color.white));
        unfocusButton.setTextColor(getResources().getColor(R.color.black));
    }
}
