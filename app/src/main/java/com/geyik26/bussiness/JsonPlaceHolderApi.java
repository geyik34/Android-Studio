package com.geyik26.bussiness;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("api/users")
    Call<PostList> getPosts();

    @POST("api/users")
    Call<Object> setPosts(@Body Post itemData);

}
