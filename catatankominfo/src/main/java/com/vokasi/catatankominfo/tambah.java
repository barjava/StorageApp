package com.vokasi.catatankominfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class tambah extends AppCompatActivity {


    private EditText judulTeks;
    private EditText isiCatatan;
   Button simpan;
    String filename = "";
    String catatan = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        judulTeks=findViewById(R.id.judul);
        isiCatatan=findViewById(R.id.isiTeks);
        simpan=findViewById(R.id.simpan);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            filename = extras.getString("filename");
            judulTeks.setText(filename);
            getSupportActionBar().setTitle("Ubah Catatan");
            baca();
        } else {
            getSupportActionBar().setTitle("Tambah Catatan");

        }
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FILENAME = judulTeks.getText().toString();
                String isi = isiCatatan.getText().toString();
                File path=getDir("NOTES",MODE_PRIVATE);
                File file=new File(path.toString(),FILENAME);
                FileOutputStream output=null;
                try {
                    file.createNewFile();
                    output=new FileOutputStream(file,false);
                    output.write(isi.getBytes());
                    output.flush();
                    output.close();
                    Toast.makeText(getApplicationContext(),"catatan tersimpan", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                onBackPressed();
            }

        });
    }


    public void baca(){
        File path=getDir("NOTES",MODE_PRIVATE);
        File file = new File(path,judulTeks.getText().toString());
        if(file.exists()){
            StringBuilder text = new StringBuilder();
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while(line != null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            }
            catch(IOException e){
                System.out.println("Error " + e.getMessage());
            }
            catatan = text.toString();
            isiCatatan.setText(catatan);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
