package com.example.evahan;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController<apiSet> {
    private static String url = "http://localhost/vd/";
    private static ApiController clientObject;

    private static Retrofit retrofit;

    ApiController() {
        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized ApiController getInstance() {
        if (clientObject == null)
            clientObject = new ApiController();

        return clientObject;
    }

    APIClient getapi() {
        return retrofit.create(APIClient.class);
    }


}
