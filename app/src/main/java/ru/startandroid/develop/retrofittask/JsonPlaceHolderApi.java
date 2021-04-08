package ru.startandroid.develop.retrofittask;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<MessageApiModel>> getMessages();


}
