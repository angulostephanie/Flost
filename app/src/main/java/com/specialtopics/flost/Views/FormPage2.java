package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

public class FormPage2 extends Fragment {
    TextView title;

    public static FormPage2 newInstance(int page) {
        FormPage2 fragment = new FormPage2();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.form_page2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        FormActivity activity = (FormActivity) getActivity();
        activity.showPrevButton();
        title = view.findViewById(R.id.tv_q2);
        title.setText(Utils.getQ2());

    }
}
