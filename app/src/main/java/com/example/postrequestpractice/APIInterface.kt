package com.example.postrequestpractice

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterface {
    @GET("/test/")
    fun getUsers(): Call<ArrayList<User.UserItem>>

    @POST("/test/")
    fun postUser(@Body userData: User.UserItem): Call<User.UserItem>


}