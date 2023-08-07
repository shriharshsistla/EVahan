package com.example.evahan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIClient {
    @FormUrlEncoded
    @POST("Multipart_save.php")
    Call<model> getdata(
            @Field("vehicle_no") String vehicle_no
            , @Field("engine_no") String engine_no,
            @Field("chasis_no") String chasis_no);
}
