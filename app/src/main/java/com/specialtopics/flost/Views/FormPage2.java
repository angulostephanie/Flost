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
    public void onViewCreated(View view, Bundle savedInstanceState){
        FormActivity activity = (FormActivity) getActivity();
        activity.hideNextButton();
        nextFrag = Utils.getNextFrag(getActivity(), page);


        title = view.findViewById(R.id.tv_q2);
        title.setText(Utils.getQ2());
        editText = view.findViewById(R.id.et_q2);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                activity.showNextButton();
                Log.d("asdf", "2");
            }

            @Override
            public void afterTextChanged(Editable s) {
                input = editText.getText().toString();
                ((FormPage3) nextFrag).onFormDataReceived(newItem);
            }
        });

    }


    @Override
    public void onFormDataReceived(Item item) {
        newItem = item;
    }
}
