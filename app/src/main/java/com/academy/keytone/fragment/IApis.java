package com.academy.keytone.fragment;

import com.academy.keytone.model.ResponseAPI;
import com.academy.keytone.model.SendUserDataResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IApis {

    @Multipart
    @POST("user_profile_update")
    Call<ResponseAPI> uploadImage(@Part MultipartBody.Part file, @Part("user_id") RequestBody user_id, @Part("fname") RequestBody fname, @Part("lname") RequestBody lname, @Part("profile_image") RequestBody profile_image, @Part("phone") RequestBody phone);
    // Call<IntruderResponse> uploadImage(@Field("email") String emailID, @Field("location") String location);



}
