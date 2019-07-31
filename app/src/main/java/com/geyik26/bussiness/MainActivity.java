package com.geyik26.bussiness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Liste liste = new Liste();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DataBase db = new DataBase(this);

        final EditText etAd = findViewById(R.id.editName);
        final EditText etTCKN = findViewById(R.id.editTCKN);
        //etTCKN.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        final Spinner spinner = findViewById(R.id.spinner);
        final DatePicker etBirth = findViewById(R.id.datePicker);
        final EditText etID = findViewById(R.id.editID);
        final String strBirthdate = etBirth.getDayOfMonth() + "/" + etBirth.getMonth() + "/" + etBirth.getYear();

        textView = findViewById(R.id.textView);

        //ID'Yİ LİSTELEMEDEN POST EDİYORUZ
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String ad = intent.getStringExtra("ad");
        String tc = intent.getStringExtra("tc");
        etTCKN.setText(tc);
        etID.setText(id);
        etAd.setText(ad);

        Button buttonJson = findViewById(R.id.button_json);
        buttonJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GsonList.class);
                startActivity(i);
            }
        });

        findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = db.updateData(etAd.getText().toString(), strBirthdate, etTCKN.getText().toString(), spinner.getSelectedItem().toString(), etID.getText().toString());

                if (isUpdate) {
                    Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, Liste.class);
                    startActivity(i);
                    liste.listele = true;
                }
            }
        });

        Button buttonEkle = findViewById(R.id.buttonEkle);
        buttonEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    DataBase db = new DataBase(MainActivity.this);

                    boolean ekle = false;

                    if (hasWindowFocus()) {
                        if (etTCKN.getText().toString().trim().length() != 11) {
                            etTCKN.setError("Failed");
                        } else {
                            etTCKN.setError(null);
                            ekle = true;
                        }
                    } else {
                        if (etTCKN.getText().toString().trim().length() != 11) {
                            etTCKN.setError("Failed");
                        } else {
                            etTCKN.setError(null);
                            ekle = true;
                        }
                    }

                    if (hasWindowFocus()) {
                        if (etAd.getText().toString().trim().length() < 5) {
                            etAd.setError("Failed");
                        } else {
                            Intent i = new Intent(MainActivity.this, Liste.class);
                            startActivity(i);
                            ekle = true;
                            etAd.setError(null);
                        }
                    } else {
                        if (etAd.getText().toString().trim().length() < 5) {
                            etAd.setError("Failed");
                        } else {
                            Intent i = new Intent(MainActivity.this, Liste.class);
                            startActivity(i);
                            ekle = true;
                            etAd.setError(null);
                        }
                    }
                    if (ekle) {
                        db.VeriEkle(etAd.getText().toString(), etTCKN.getText().toString(), "-" + spinner.getSelectedItem().toString(), strBirthdate);
                        liste.listele = true;
                    }

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://cpanel.diyetsaati.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    final List<Post> data = new ArrayList<>();
                    Post post = new Post(12345678910L,"12sdfasd3","Erkek","asd","12sdfasd3","dsaasdaasd asdadas","mail@abc.com.tr","mAddrress");
                    data.add(post);
                    PostList postList = new PostList();
                    postList.setData(data);
                    Call<Object> call = jsonPlaceHolderApi.setPosts(post);
                    call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            if (!response.isSuccessful()) {
                                textView.setText("Code : " + response.code());
                                try {
                                    String strResponse =   response.body().toString();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
// [size=154 text={"id_number":["id number dizge olmal\\u0131d\\u0131r."],"password"…]
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            textView.setText(t.getMessage());
                        }
                    });

                    ////////////////////////
                }catch (Throwable ex){
                    ex.printStackTrace();
                }
            }
        });
    }

}


