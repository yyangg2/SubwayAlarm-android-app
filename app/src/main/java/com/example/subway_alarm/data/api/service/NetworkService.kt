package com.example.subway_alarm.data.api.service

import com.example.subway_alarm.data.api.dataModel.ApiModelList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    @GET("api/subway/7a5370504e6868743131324443656265/json/realtimeStationArrival/0/1/{post}")
    fun doGetUserList(@Path("post") post: String): Call<ApiModelList>
    /*
    @GET
    fun getAvatarImage(@Url url: String): Call<ResponseBody>
     */
}