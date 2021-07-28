package com.example.filemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.IntentCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int Per_Code = 123;
    String mainDire = Environment.getExternalStorageState();
    ArrayList<String> arrayList = new ArrayList<>();

    private boolean checkPermission(){
        int theResult = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if(theResult == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    private void requestPermssion(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"Allow the permission to read file",Toast.LENGTH_LONG).show();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},Per_Code);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        final ListView theList = findViewById(R.id.mylist);
        Button mainBtn = findViewById(R.id.main1);
        Button createBtn = findViewById(R.id.b1);

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder createFolder = new AlertDialog.Builder(MainActivity.this);
                createFolder.setTitle("Create New Folder");
                final EditText mytext = new EditText(MainActivity.this);
                mytext.setInputType(InputType.TYPE_CLASS_TEXT);
                createFolder.setView(mytext);
                createFolder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        File dir = new File(getApplicationContext().getExternalFilesDir(null), String.valueOf(mytext.getText()));
                        if(!dir.exists()){
                            dir.mkdirs();
                            arrayList.add(dir.getName());
                        }
                    }
                });
                createFolder.show();
            }
        });

        listDirectory();
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,createFile.class));

            }
        });


    }

    public void listDirectory(){
        final ListView theList = findViewById(R.id.mylist);
        if(Environment.MEDIA_MOUNTED.equals(mainDire)) {
                if (checkPermission()) {
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/com.example.filemanager/files");
                    if(dir.exists()) {
                        final File mylist[] = dir.listFiles();
                        if(mylist.length != 0){
                            for(int i = 0; i < mylist.length; i++) {
                                arrayList.add(mylist[i].getName());
                            }
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
                        theList.setAdapter(arrayAdapter);

                    }
                } else {
                    requestPermssion();
                }

        }

    }


}
