package com.specialtopics.flost.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.R;

public class ItemDetailActivity extends AppCompatActivity {
    private TextView itemName, location, time;
    private static final String TAG = "ItemDetailActivity";
    private ImageButton backBtn;
    private Button deleteBtn;
    private Intent intent;
    private Item item;
    private ImageView photo;
    private Context mContext;
    String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mContext = this;
        intent = getIntent();
        item = intent.getParcelableExtra("item");

        photo = findViewById(R.id.imageView);
        Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
        photo.setImageBitmap(bmp);

        itemName = findViewById(R.id.item_name_tv);
        location = findViewById(R.id.last_seen_location);
        time = findViewById(R.id.last_seen_time);
        deleteBtn = findViewById(R.id.delete_item_btn);
        backBtn = findViewById(R.id.back_button);

        backBtn.setOnClickListener(v -> {
            Intent backIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, backIntent);
            finish();
        });

        setUpDeleteBtn();
        setUpDisplayTextViews(item.getType());
        Log.d(TAG, item.getType());
    }

    private void setUpDisplayTextViews(String type){
        itemName.setText(item.getName().toUpperCase());
        String locationTemp, timeTemp;

        if(type.equals("lost")){
            locationTemp = "Last Seen Location:     ";
            timeTemp     = "Last Seen Time:         ";
        } else {
            locationTemp = "Found at Location:      " ;
            timeTemp     = "Found at Time:          " ;
        }
        location.setText(locationTemp + item.getLocation().toUpperCase());
        time.setText(timeTemp + item.getInputDay() + " at " + item.getInputTime());

    }

    private void setUpDeleteBtn(){
        if(currentUserEmail.equals(item.getEmail())) {
            deleteBtn.setOnClickListener(v -> {
                FlostRestClient.deleteItem(mContext, item);
                Intent deleteIntent = new Intent();
                setResult(Activity.RESULT_OK, deleteIntent);
                finish();
            });
        } else
            deleteBtn.setVisibility(View.INVISIBLE);
    }
}
