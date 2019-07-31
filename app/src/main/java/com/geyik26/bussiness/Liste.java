package com.geyik26.bussiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Liste extends AppCompatActivity {

    public boolean listele;

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    ListView listView;
    ListView listViewAd;
    ListView listViewTc;
    ListView listViewGender;
    ListView listViewDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        listView = findViewById(R.id.listview_id);
        listViewAd = findViewById(R.id.listview_ad);
        listViewTc = findViewById(R.id.listview_tc);
        //listViewGender = findViewById(R.id.listview_gender);
        //listViewDT = findViewById(R.id.listview_dt);
        listele = true;

        final Button buttonListele = findViewById(R.id.buttonListele);
        buttonListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase db = new DataBase(Liste.this);

                listele = false;
                List<String> veriler = db.VeriListeleID();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Liste.this, android.R.layout.simple_list_item_1, android.R.id.text1, veriler);
                listView.setAdapter(adapter);

                List<String> verilerAd = db.VeriListeleAD();
                ArrayAdapter<String> adapterAd = new ArrayAdapter<String>(Liste.this, android.R.layout.simple_list_item_1, android.R.id.text1, verilerAd);
                listViewAd.setAdapter(adapterAd);

                List<String> verilerTC = db.VeriListeleTC();
                ArrayAdapter<String> adapterTC = new ArrayAdapter<String>(Liste.this, android.R.layout.simple_list_item_1, android.R.id.text1, verilerTC);
                listViewTc.setAdapter(adapterTC);
            }
        });

        int noOfSeconds = 1;
        if (listele) {
            if (noOfSeconds > 0.1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonListele.performClick();
                    }
                }, noOfSeconds * 100);
            }
        }

        //uzun tık menüsü
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
                if(view.getId() == R.id.listview_id) {
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                    menu.setHeaderTitle(listView.getItemAtPosition(info.position).toString());
                    menu.add(0, 0, 0, "Sil");
                    menu.add(0, 1, 0, "Düzenle");
                }
            }
        });//uzun tık menüsü son
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.artı:
                Intent i = new Intent(Liste.this,MainActivity.class);
                startActivity(i);

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public boolean onContextItemSelected(MenuItem item){
        boolean donus;
        switch (item.getItemId()){
            case 0:

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final String secili = listView.getItemAtPosition(info.position).toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Veri Silme");
                builder.setMessage("\""+secili + "silmek istediğinizden emin misiniz?");

                builder.setNegativeButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataBase dataBase = new DataBase(Liste.this);
                        long id = Long.parseLong(secili);
                        dataBase.VeriSilID(id);
                    }
                });

                builder.setPositiveButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("asd","asd");
                }
            });

                AlertDialog dialog = builder.create();
                dialog.show();
                donus=true;
                break;

            case 1:
                DataBase db = new DataBase(this);
                AdapterView.AdapterContextMenuInfo info2 = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final String secili2 = listView.getItemAtPosition(info2.position).toString();
                String s = db.GetInfo(secili2);
                String tc = db.GetInfoTC(secili2);

                Intent i = new Intent(Liste.this,MainActivity.class);
                i.putExtra("id",secili2);
                i.putExtra("tc",tc);
                i.putExtra("ad",s);
                startActivity(i);
                default:
                    donus = false;
        }
        return donus;
    }
}
