package com.davidllorca.filesstorage;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {

    private EditText textboxSave;
    private EditText textboxLoad;
    private static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textboxSave = (EditText) findViewById(R.id.text1_ed);
        textboxLoad = (EditText) findViewById(R.id.text2_ed);

    }

    public void onClickSave(View view) {
        String str = textboxSave.getText().toString();
        try {
            /*
                MODE_WORLD_READABLE: This file can be read from all apps.
                MODE_WORLD_WRITABLE: This file can be read/written from all apps.
                MODE_PRIVATE: THis file is just accessible by own app.
             */
            FileOutputStream fOut = openFileOutput("textfile.txt", MODE_WORLD_READABLE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write string in file
            osw.write(str);
            osw.flush();
            osw.close();

            // Show info message
            Toast.makeText(getBaseContext(), "File saved successfully", Toast.LENGTH_SHORT).show();

            // Clear EditText
            textboxSave.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickSaveSD(View view) {
        String str = textboxSave.getText().toString();
        try {
            // Path to SDcard
            File sdCard = Environment.getExternalStorageDirectory();
            // Create directory
            File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
            directory.mkdirs();
            // Create file into directory
            File file = new File(directory, "textfile.txt");

            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write string in file
            osw.write(str);
            osw.flush();
            osw.close();

            // Show info message
            Toast.makeText(getBaseContext(), "File saved on SD successfully in " + directory.getAbsolutePath(), Toast.LENGTH_SHORT).show();

            // Clear EditText
            textboxSave.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickLoad(View view) {
        try {
            FileInputStream fIn = openFileInput("textfile.txt");
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String str = "";

            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) { // read() return -1 if file end
                // Convert to string
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;

                inputBuffer = new char[READ_BLOCK_SIZE];
            }

            // Set string in text box
            textboxLoad.setText(str);

            // Show info message
            Toast.makeText(getBaseContext(), "File loaded successfully", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickLoadSD(View view) {
        try {
            // Path to SDcard
            File sdCard = Environment.getExternalStorageDirectory();
            // References directory
            File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
            directory.mkdirs();
            // References file
            File file = new File(directory, "textfile.txt");
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String str = "";

            int charRead;
            while ((charRead = isr.read(inputBuffer)) > 0) { // read() return -1 if file end
                // Convert to string
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;

                inputBuffer = new char[READ_BLOCK_SIZE];
            }

            // Set string in text box
            textboxLoad.setText(str);

            // Show info message
            Toast.makeText(getBaseContext(), "File loaded successfully", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickLoadResources(View view) {
        try {
            InputStream is = this.getResources().openRawResource(R.raw.textfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = null;

            while ((str = br.readLine()) != null) {
                textboxLoad.setText(str);
                Toast.makeText(getBaseContext(), "File loaded successfully", Toast.LENGTH_SHORT).show();
            }
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}