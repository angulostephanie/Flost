package com.specialtopics.flost.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.specialtopics.flost.Controllers.FlostRestClient;
import com.specialtopics.flost.Models.Item;
import com.specialtopics.flost.OnFormDataListener;
import com.specialtopics.flost.R;
import com.specialtopics.flost.Utils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class FormPage8 extends Fragment implements OnFormDataListener {
    private TextView title;
    private Item newItem;
    private Button uploadButton;
    private Button cameraButton;
    public ImageView mImageview;
    private StorageReference mStorage;
    Button finishBtn;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_INTENT = 2;

    public static FormPage8 newInstance(int page) {
        FormPage8 fragment = new FormPage8();
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
        View view = inflater.inflate(R.layout.form_page8, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mStorage = FirebaseStorage.getInstance().getReference();
        mImageview = view.findViewById(R.id.photo_display);
        title = view.findViewById(R.id.tv_q5);
        title.setText(Utils.getQ8());
        uploadButton = view.findViewById(R.id.upload_btn);
        cameraButton = view.findViewById(R.id.camera_btn);
        finishBtn = view.findViewById(R.id.finish_btn);
        finishBtn.setVisibility(View.INVISIBLE);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlostRestClient.postItem(getContext(),newItem);
                Log.d("test", "Finsh btn clicked");
                Intent  intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, CAMERA_REQUEST_CODE);
            }
        });

        /*
        mImageview.setDrawingCacheEnabled(true);
        mImageview.buildDrawingCache();
        Bitmap bitmap = mImageview .getDrawingCache();

        ByteArrayOutputStream baas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baas);

        byte[] data = baas.toByteArray();

        UploadTask uploadTask = mStorage.child("{image-folder}").child("unique-id-image").putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "Picture upload failed", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl();
            }
        }); */
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            final Uri uri = data.getData();
            Picasso.get().load(uri).fit().centerCrop().into(mImageview);
        }
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] bitmapdata = stream.toByteArray();
            newItem.setImage(bitmapdata);
            mImageview.setImageBitmap(photo);
            Uri uri1 = data.getData();
            Picasso.get().load(uri1).fit().centerCrop().into(mImageview);

            finishBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFormDataReceived(Item item) {
        newItem = item;
        Log.d("test", newItem.toString());
    }

    @Override
    public void passDataThrough() {

    }


}
