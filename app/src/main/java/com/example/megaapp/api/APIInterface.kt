package com.example.megaapp.api

import com.example.megaapp.model.User
import com.example.megaapp.model.Users
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getUsersInfo(): Call<Users?>?

    @Headers("Content-Type: application/json")
    @POST("/test/") //insert
    fun addUsersInfo(@Body requestBody: User?): Call<User>

    @Headers("Content-Type: application/json")
    @PUT("/test/{id}") //for update
    fun updateUsersInfo(@Path("id")id:Int, @Body requestBody: User?): Call<User>

    @Headers("Content-Type: application/json")
    @DELETE("/test/{id}")
    fun deleteUsersInfo(@Path("id")id:Int): Call<Void>

}