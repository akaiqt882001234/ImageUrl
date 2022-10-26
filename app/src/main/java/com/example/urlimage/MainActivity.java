package com.example.urlimage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.urlimage.database.DbHelper;
import com.example.urlimage.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    EditText urlText;
    Button addlink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        urlText = findViewById(R.id.urltext);
        addlink = findViewById(R.id.addlink);

// URL INPUT CONTROLLER
        binding.clearbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                binding.urltext.setText("");
                binding.image.setImageBitmap(null);
            }
        });

        binding.addlink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                String url = binding.urltext.getText().toString();
                new addlink(url).start();

                // DATABASE
                DbHelper dbHelper = new DbHelper(MainActivity.this);
                dbHelper.addURL(urlText.getText().toString().trim());
            }
        });
    }

    class addlink extends Thread{
        String URL;
        Bitmap bitmap;

        addlink(String URL){
            this.URL = URL;
        }
        @Override
        public void run() {


            mainHandler.post(new Runnable()
            {
                @Override
                public void run(){
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Getting URL Picture...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
            });

            InputStream inputStream = null;
            try {
                inputStream = new URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            mainHandler.post(new Runnable(){

                @Override
                public void run() {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    binding.image.setImageBitmap(bitmap);
                }
            });

        }

    }
}