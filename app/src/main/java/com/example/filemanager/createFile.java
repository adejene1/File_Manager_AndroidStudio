package com.example.filemanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class createFile extends AppCompatActivity {
    EditText inputText;
    TextView responce;
    Button save,load;
    private String filepath = "";
    File myExtrnalFile;
    String mytextFile = "";
    private String filename = "Savedfile.txt";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_file);

        Button home = findViewById(R.id.homebtn);

        inputText = findViewById(R.id.editText3);

        save = findViewById(R.id.savebtn);
        load = findViewById(R.id.loadbtn);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(createFile.this,MainActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(createFile.this,"Text Saved",Toast.LENGTH_LONG).show();
                try{
                    FileOutputStream textInput = new FileOutputStream(myExtrnalFile);
                    textInput.write(inputText.getText().toString().getBytes());
                    textInput.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputText.setText("");

            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream textOutput = new FileInputStream(myExtrnalFile);
                    DataInput inData = new DataInputStream(textOutput);
                    BufferedReader readFile = new BufferedReader(new InputStreamReader((InputStream) inData));
                    String letter;
                    while((letter = readFile.readLine()) != null){
                        mytextFile = mytextFile + letter;
                    }
                    ((DataInputStream) inData).close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputText.setText(mytextFile);

            }
        });


            myExtrnalFile = new File(getExternalFilesDir(filepath), filename);
    }
}







