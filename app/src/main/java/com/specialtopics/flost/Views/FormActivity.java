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

import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;

public class FormActivity extends AppCompatActivity  {
    Context mContext;
    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;
    Button nextPageButton;
    ImageButton backButton;
    Button previousPageButton;
    Item newItem;
    boolean overrideFlag;
    int nextPage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        newItem = new Item();
        overrideFlag = false;

        viewPager = findViewById(R.id.vpPager);
        fragmentPagerAdapter = new FormAdapter(getSupportFragmentManager(), newItem);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(9);

        mContext = this;
        setUpBtnListeners();

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            // optional
            @Override
            public void onPageSelected(int position) {

            }

        });


    }

    public void setUpBtnListeners(){
        nextPageButton = findViewById(R.id.nextPageButton);
        previousPageButton = findViewById(R.id.previousPageButton);
        backButton = findViewById(R.id.back_button);
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
                if(overrideFlag){
                    overrideFlag = false;
                    viewPager.setCurrentItem(nextPage);
                } else
                    viewPager.setCurrentItem(getNextPossibleItemIndex(1));
            }
        });
    }

    public void hidePrevButton(){
        previousPageButton.setVisibility(View.INVISIBLE);
    }
    public void showPrevButton(){ previousPageButton.setVisibility(View.VISIBLE); }
    public void hideNextButton(){
        nextPageButton.setVisibility(View.INVISIBLE);
    }
    public void showNextButton(){
        nextPageButton.setVisibility(View.VISIBLE);
    }

    public void setPageOnNextClick(int page){
        overrideFlag = true;
        nextPage = page - 1 ;
    }

    private int getNextPossibleItemIndex (int change) {

        int currentIndex = viewPager.getCurrentItem();
        int total = viewPager.getAdapter().getCount();

        if (currentIndex + change < 0) {
            return 0;
        }
        return Math.abs((currentIndex + change) % total) ;
    }


}