package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.OnFormDataListener;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

public class FormPage6 extends Fragment implements OnFormDataListener {
    TextView title;
    Item newItem;

    public static FormPage6 newInstance(int page) {
        FormPage6 fragment = new FormPage6();
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
        View view = inflater.inflate(R.layout.form_page6, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        title = view.findViewById(R.id.tv_q6);
        title.setText(Utils.getQ6_1());

    }

    @Override
    public void onFormDataReceived(Item item) {
        newItem = item;
    }
}
