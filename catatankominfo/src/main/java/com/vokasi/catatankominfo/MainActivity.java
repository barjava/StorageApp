package com.vokasi.catatankominfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, tambah.class);

                Map<String, Object> data = (Map<String, Object>) parent.getAdapter().getItem(position);

                intent.putExtra("filename", data.get("name").toString());

                Toast.makeText(MainActivity.this, "You clicked " + data.get("name"), Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }

        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, Object> data = (Map<String, Object>) parent.getAdapter().getItem(position);


                tampilkanDialogKonfirmasiHapusCatatan(data.get("name").toString());

                return true;

            }

        });
    }
// menampilkanalertdialog

    void tampilkanDialogKonfirmasiHapusCatatan(final String filename) {

        new AlertDialog.Builder(this)

                .setTitle("Hapus Catatan Ini?")

                .setMessage("Apakah Anda yakin ingin menghapus " + filename + "?")

                .setIcon(android.R.drawable.ic_dialog_alert)

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        hapusFile(filename);

                    }

                })

                .setNegativeButton(android.R.string.no, null).show();

    }

    void hapusFile(String filename) {

        File path = getDir("NOTES", MODE_PRIVATE);

        File file = new File(path, filename);

        if (file.exists()) {

            file.delete();

        }

        mengambilList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_tambah) {
            startActivity(new Intent(this, tambah.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mengambilList();
    }

    private void mengambilList() {
        File path = getDir("NOTES", MODE_PRIVATE);
        File directory = new File(path.toString());
        if (directory.exists()) {
            File[] files = directory.listFiles();
            String[] filenames = new String[files.length];
            String[] dateCreate = new String[files.length];

            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("dd mmm yyyy hh:mm:ss");
            List<Map<String, Object>> itemDataList =
                    new ArrayList<Map<String, Object>>();
            for (int i = 0; i < files.length; i++) {
                filenames[i] = files[i].getName();
                Date lastModeDate = new Date(files[i].lastModified());
                dateCreate[i] = simpleDateFormat.format(lastModeDate);
                Map<String, Object> listMap = new HashMap<>();
                listMap.put("name", filenames[i]);
                listMap.put("date", dateCreate[i]);
                itemDataList.add(listMap);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, new String[]{"name", "date"}, new int[]{android.R.id.text1, android.R.id.text2});
            list.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();

        }
    }


}



