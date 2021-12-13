package com.example.postrequestpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.postrequestpractice.databinding.ActivityViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.text.method.ScrollingMovementMethod
import androidx.core.view.isVisible


class ViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewBinding

    var usersText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUsers()

        binding.postBtn.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.editBtn.setOnClickListener {
            intent = Intent(applicationContext, UpdateActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getUsers() {
        binding.idLoadingPB.isVisible = true

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getUsers()
        retrofitData.enqueue(object :Callback<ArrayList<User.UserItem>> {

            override fun onResponse(call: Call<ArrayList<User.UserItem>>, response: Response<ArrayList<User.UserItem>>) {

                for(user in response.body()!!){
                    usersText += "name: ${user.name}" + "\n" + "location: ${user.location}" + "\n" + "id: ${user.pk}"+ "\n\n"

                }
                binding.idLoadingPB.isVisible = false

                //Making TextView scrollable
                binding.allUsersTV.setMovementMethod(ScrollingMovementMethod())
                binding.allUsersTV.text = usersText
            }

            override fun onFailure(call: Call<ArrayList<User.UserItem>>, t: Throwable) {
                Log.d("errror", "$t")
            }
//
       })
    }
}