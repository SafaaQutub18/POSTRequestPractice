package com.example.postrequestpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.postrequestpractice.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.postBtn.setOnClickListener {
            var name = binding.nameET.text.toString()
            var location = binding.locationET.text.toString()

            if (name!= "" || location != "")
               postUser(name,location)
            else
                Toast.makeText(this , "Empty name or location!!", Toast.LENGTH_SHORT).show()
        }


            binding.viewBtn.setOnClickListener {
                intent = Intent(applicationContext, ViewActivity::class.java)
                startActivity(intent)
            }
        }


    private fun postUser(name: String, location: String) {
        // below line is for displaying our progress bar.
        binding.loadingPB.visibility = View.VISIBLE;

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.postUser(User.UserItem(name,location))
        retrofitData.enqueue(object : Callback<User.UserItem> {
            override fun onResponse(call: Call<User.UserItem>, response: Response<User.UserItem>) {
                Toast.makeText(this@MainActivity, "User added to API", Toast.LENGTH_SHORT).show();
                // below line is for hiding our progress bar.
                binding.loadingPB.visibility = View.GONE;

                binding.nameET.setText("")
                binding.locationET.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                val responseFromAPI: User.UserItem? = response.body()
                val responseString = """
                    Response Code : ${response.code()}
                    Name : ${responseFromAPI?.name }
                    """.trimIndent()

                Log.d("Main" , responseString)


            }
            override fun onFailure(call: Call<User.UserItem>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: user not added to API", Toast.LENGTH_LONG)
            }
            })
    }

}