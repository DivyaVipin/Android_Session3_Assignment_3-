package assignment.android.acadgild.gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static  int RESULT_LOAD_IMAGE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);


    }

    @Override//Permission for Read External Storage
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Button buttonLoadImage = (Button) findViewById(R.id.btnLoad);
                    buttonLoadImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {//Loaded image in sdcard and picking images from gallery
                            Intent gallery_intent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery_intent,RESULT_LOAD_IMAGE);//Paasing intentobject and an integer value

                        }
                    });

                } else {


                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }

    @Override//Getting the image and displaying in ImageView
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data )
        {
           Uri selectedImage= data.getData();
            String[] filePathColumn={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(selectedImage,filePathColumn,null,null,null);
            cursor.moveToFirst();
            int coloumnIndex=cursor.getColumnIndex(filePathColumn[0]);
            String  imageDecodable=cursor.getString(coloumnIndex);
            cursor.close();
            ImageView img=(ImageView)findViewById(R.id.imageViewGallery);
            img.setImageBitmap(BitmapFactory.decodeFile(imageDecodable));

        }
            else
        {
            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
        }
       }
        catch(Exception e)
        {
           Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
       }
    }
}
