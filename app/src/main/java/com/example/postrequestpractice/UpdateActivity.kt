package com.example.postrequestpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.postrequestpractice.databinding.ActivityMainBinding
import com.example.postrequestpractice.databinding.ActivityUpdateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_update)

        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.editBtn.setOnClickListener {
            var name = binding.nameET.text.toString()
            var location = binding.locationET.text.toString()
            var id = binding.idET.text.toString().toInt()

            if (name!= "" || location != "" || id != null)
                editUser(name,location, id)
            else
                Toast.makeText(this , "Empty name or location or id!!", Toast.LENGTH_SHORT).show()
        }

        binding.deleteBtn.setOnClickListener {
            var id = binding.idET.text.toString().toInt()
            if (id != null)
                deleteUser(id)
            else
                Toast.makeText(this , "Please Enter User id!!", Toast.LENGTH_SHORT).show()
        }

        binding.viewBtn.setOnClickListener {
            intent = Intent(applicationContext, ViewActivity::class.java)
            startActivity(intent)
        }

    }

    private fun deleteUser(id: Int) {
// below line is for displaying our progress bar.
        binding.loadingPB.visibility = View.VISIBLE;

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.deleteUser(id)
        retrofitData.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@UpdateActivity, "User info. has been deleted", Toast.LENGTH_SHORT).show();
                // below line is for hiding our progress bar.
                binding.loadingPB.visibility = View.GONE;

                binding.nameET.setText("")
                binding.locationET.setText("");
                binding.idET.setText("");

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UpdateActivity, "Error: user not added to API", Toast.LENGTH_LONG)
            }
        })
    }

    private fun editUser(name: String, location: String, id: Int) {

        // below line is for displaying our progress bar.
        binding.loadingPB.visibility = View.VISIBLE;

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.updateUser(id, User.UserItem(name,location,id))
        retrofitData.enqueue(object : Callback<User.UserItem> {
            override fun onResponse(call: Call<User.UserItem>, response: Response<User.UserItem>) {
                Toast.makeText(this@UpdateActivity, "User info. has been modified", Toast.LENGTH_SHORT).show();
                // below line is for hiding our progress bar.
                binding.loadingPB.visibility = View.GONE;

                binding.nameET.setText("")
                binding.locationET.setText("");
                binding.idET.setText("");

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
                Toast.makeText(this@UpdateActivity, "Error: user not added to API", Toast.LENGTH_LONG)
            }
        })
    }

}