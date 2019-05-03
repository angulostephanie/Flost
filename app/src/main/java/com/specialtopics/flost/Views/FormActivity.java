package com.specialtopics.flost.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.specialtopics.flost.R;

public class FormActivity extends AppCompatActivity {
    Context mContext;
    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;
    Button nextPageButton;
    Button previousPageButton;
    ImageButton backButton;
    int currIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        viewPager = findViewById(R.id.vpPager);
        fragmentPagerAdapter = new FormAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        nextPageButton = findViewById(R.id.nextPageButton);
        previousPageButton = findViewById(R.id.previousPageButton);
        backButton = findViewById(R.id.back_button);
        mContext = this;

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });
        previousPageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getNextPossibleItemIndex(-1));
            }
        });
        nextPageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getNextPossibleItemIndex(1));
            }
        });

// For scrolling to previous item

    }

    public void hidePrevButton(){
        previousPageButton.setVisibility(View.INVISIBLE);
    }

    public void showPrevButton(){
        previousPageButton.setVisibility(View.VISIBLE);
    }

    public void hideNextButton(){
        nextPageButton.setVisibility(View.INVISIBLE);
    }

    public void showNextButton(){
        nextPageButton.setVisibility(View.VISIBLE);
    }


    private int getNextPossibleItemIndex (int change) {
        int currentIndex = viewPager.getCurrentItem();
        int total = viewPager.getAdapter().getCount();

        if (currIndex + change < 0) {
            return 0;
        }

        return Math.abs((currentIndex + change) % total) ;
    }

}
