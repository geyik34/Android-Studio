package com.geyik26.bussiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GsonList extends AppCompatActivity {

    private TextView textView;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_list);

        textView = findViewById(R.id.textView);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cpanel.diyetsaati.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPost();
    }
   private void getPost() {
       try{
           Call<PostList> call = jsonPlaceHolderApi.getPosts();
           call.enqueue(new Callback<PostList>() {
               @Override
               public void onResponse(Call<PostList> call, Response<PostList> response) {
                   if (response.isSuccessful()) {
                       textView.setText("Code : " + response.code());
                       try{
                           //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                           textView.setText(response.body().getData().get(0).getName() +"\n"+ response.body().getData().get(0).getId());
                          // String jsonElement =response.body().toString();
                           //JSONArray jsonObject = new JSONArray(response.body().toString());
                          // String strBody = jsonObject.get(0).toString();
                           //Post post = gson.fromJson((JsonElement) response.body(),Post.class);
                       }
                       catch (Throwable e){
                           Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                           e.printStackTrace();
                       }

                   }
               }

               @Override
               public void onFailure(Call<PostList> call, Throwable t) {
                   textView.setText(t.getMessage());
               }
           });
       }catch (Exception e){
           e.printStackTrace();
       }
   }


 // private void getPost2() {
 //     try{
 //         Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
 //         call.enqueue(new Callback<List<Post>>() {
 //             @Override
 //             public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
 //                 if (!response.isSuccessful()) {
 //                     textView.setText("Code : " + response.code());
 //                 }
 //
 //                 List<Post> posts = response.body();
 //                 for (Post post : posts) {
 //                     String content = "";
 //                     content += "ID : " + post.getId();
 //                     content += "Name : " + post.getName();
 //                     content+= "TCKN : " + post.getTckn();
 //                     content += "Gender : " + post.getGender();
 //                     content += "BirthDate += " + post.getBirthdate();
 //
 //                     textView.append(content);
 //                 }
 //             }
 //
 //             @Override
 //             public void onFailure(Call<List<Post>> call, Throwable t) {
 //                 textView.setText(t.getMessage());
 //             }
 //         });
 //     }catch (Exception e){
 //         e.printStackTrace();
 //     }
 // }
}
