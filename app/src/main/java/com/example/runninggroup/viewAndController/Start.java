package com.example.runninggroup.viewAndController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.runninggroup.R;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

        Thread myThread = new Thread(){
                @Override
                public void run(){
                    try{
                        sleep(3000);
                        Intent intent = new Intent(Start.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }


        };
        myThread.start();


    }
}
