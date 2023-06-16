package com.example.figma;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {
    View fragment;
    private int IMG_REQUEST = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        fragment = findViewById(R.id.nav_host_fragment);


    }
    @Override
    public boolean onSupportNavigateUp(){
        return Navigation.findNavController(fragment).navigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if ((requestCode == IMG_REQUEST) && resultCode == RESULT_OK && data != null){

            try {
                Uri path = data.getData();
                Global.bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
            }catch (Exception e){
                e.printStackTrace();
            }
//        }
    }
}