package com.example.postrequestpractice

import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @GET("/test/")
    fun getUsers(): Call<ArrayList<User.UserItem>>

    @POST("/test/")
    fun postUser(@Body userData: User.UserItem): Call<User.UserItem>

    @PUT("/test/{id}")
    fun updateUser(@Path("id") id:Int,  @Body userData: User.UserItem ): Call<User.UserItem>

    @DELETE("/test/{id}")
    fun deleteUser(@Path("id") id:Int):Call<Void>


}