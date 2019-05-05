package com.specialtopics.flost.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.OnFormDataListener;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;

public class FormPage2 extends Fragment implements OnFormDataListener {
    TextView title;
    EditText editText;
    String input;
    Item newItem;
    Fragment nextFrag;
    int page;
    FormActivity activity;
    boolean answered = false;

    public static FormPage2 newInstance() {
        FormPage2 fragment = new FormPage2();
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
        View view = inflater.inflate(R.layout.form_page2, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && activity != null) {
            activity.showPrevButton();
            if(!answered)
                activity.hideNextButton();
            else
                activity.showNextButton();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        nextFrag = Utils.getNextFrag(getActivity(), page);
        activity = (FormActivity) getActivity();

        title = view.findViewById(R.id.tv_q2);
        title.setText(Utils.getQ2());
        editText = view.findViewById(R.id.et_q2);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                input = editText.getText().toString();
                if(newItem != null) {
                    newItem.setName(input);
                }
                activity.showNextButton();
            }
        });

    }


    @Override
    public void onFormDataReceived(Item item) {
        newItem = item;
        Log.d("test", item.getType());
    }

    @Override
    public void passDataThrough() {
        answered = true;
        ((FormPage3) nextFrag).onFormDataReceived(newItem);
    }
}