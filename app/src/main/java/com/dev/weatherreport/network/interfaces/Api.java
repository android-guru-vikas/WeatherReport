package com.dev.weatherreport.network.interfaces;

import com.dev.weatherreport.network.models.WeatherResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET("forecast/daily")
    Call<WeatherResponseModel> getWeatherResports(@Query("q") String cityName, @Query("appid") String appid);
}
