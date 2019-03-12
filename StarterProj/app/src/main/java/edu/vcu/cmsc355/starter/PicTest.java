package edu.vcu.cmsc355.starter;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PicTest extends AppCompatActivity {

    private ImageView image;
    private static final int PICK_IMAGE=100;
    Uri imageGrab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_test);

        image = (ImageView) findViewById(R.id.imageView);
    }

    private void getPic(View view){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int resultCode, int requestCode, Intent data){
        super.onActivityResult(resultCode, requestCode, data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imageGrab = data.getData();
            image.setImageURI(imageGrab);
        }
    }
}
