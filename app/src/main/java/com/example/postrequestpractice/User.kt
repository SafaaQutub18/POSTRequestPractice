package com.example.postrequestpractice

class User {

    var userData: ArrayList<UserItem>? = null


    class UserItem(
        val name: String,
        val location: String,
        val id : Int? = null
    )
}