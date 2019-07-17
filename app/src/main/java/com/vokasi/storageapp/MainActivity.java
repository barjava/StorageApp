package com.vokasi.storageapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
//item xml
    private Button buttonBuatFile;
    private Button buttonUbahFile;
    private Button buttonBacaFile;
    private Button buttonHapusFile;
    private TextView textBaca;

    //---- TAMBAH
    public static final String FILENAME="namafile.txt";
    public static final String PREFNAME="PREFNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        buttonBuatFile = (Button) findViewById(R.id.buttonBuatFile);
        buttonUbahFile = (Button) findViewById(R.id.buttonUbahFile);
        buttonBacaFile = (Button) findViewById(R.id.buttonBacaFile);
        buttonHapusFile = (Button) findViewById(R.id.buttonHapusFile);
        textBaca = (TextView) findViewById(R.id.textBaca);
    }
    //

//simpan
    private void simpanPref() {
        String isiFile="Coba Isi Data File";
        SharedPreferences sharedPreferences =getSharedPreferences(PREFNAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=
                sharedPreferences.edit();
        editor.putString(FILENAME,isiFile);
        editor.commit();
    }

    //update
    private void UpdatePref() {
        String isiFile="Update Isi Data File";
        SharedPreferences sharedPreferences =getSharedPreferences(PREFNAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=
                sharedPreferences.edit();
        editor.putString(FILENAME,isiFile);
        editor.commit();
    }
    //baca
    private void BacaPref() {
        SharedPreferences sharedPreferences
                =getSharedPreferences(PREFNAME,MODE_PRIVATE);
              if(sharedPreferences.contains(FILENAME)){
        String mytext=sharedPreferences
        .getString(FILENAME,"");
        textBaca.setText(mytext);
              }else {
                  textBaca.setText("");
              }

    } private void hapusPref() {
        SharedPreferences sharedPreferences
                =getSharedPreferences(PREFNAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();


    }
    //dari alt+enter di onclick act.xml


    public void bacaFile(View view) {
        bacaEX();
    }
    public void buatFile(View view) {
        simpanEx();
    }

    public void ubahFile(View view) {
    ubahEx();
    }

    public void hapusFile(View view) {
    hapusEx();
    }
    void simpanEx(){
        //external dan new File(getFilesDir()ganti File Path.toString()
        //dan di tambah di manifest <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
        //<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
        File Path= Environment.getExternalStorageDirectory();
        //jika didirektory picture File Path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String isiFile="Coba Isi Data File Internal";
        File file=new File(Path.toString(),FILENAME);
        FileOutputStream outputStream=null;

            try {
                file.createNewFile();
                outputStream=new FileOutputStream(file,false);
                outputStream.write(isiFile.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        void ubahEx(){
        File Path= Environment.getExternalStorageDirectory();
        String isiFile="Update Isi Data File Internal";
        File file=new File(Path.toString(),FILENAME);
        FileOutputStream outputStream=null;
            try {
                file.createNewFile();
                outputStream=new FileOutputStream(file,false);
                outputStream.write(isiFile.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        void bacaEX(){
        File Path= Environment.getExternalStorageDirectory();
        File file=new File(Path.toString(),FILENAME);
        if (file.exists()){
            StringBuilder text=new StringBuilder();
            try {
                BufferedReader br = new
                        BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textBaca.setText(text.toString());
        }else {
            textBaca.setText("");
        }

    }
    void hapusEx(){
        File Path= Environment.getExternalStorageDirectory();
        File file=new File(Path.toString(),FILENAME);
        if (file.exists()){
            file.delete();
        }
    }
}
// JIKA INTERNAL  File file=new File(getFilesDir(),FILENAME);