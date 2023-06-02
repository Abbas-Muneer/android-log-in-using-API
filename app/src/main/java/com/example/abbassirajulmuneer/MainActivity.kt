package com.example.abbassirajulmuneer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var detailsDao: DetailsDao
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var incorrectMessage: TextView
    private lateinit var loginBtn: Button



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        incorrectMessage = findViewById(R.id.incorrectmessage)
        incorrectMessage.setText("")

        loginBtn = findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            println(username)

            runBlocking {
                launch {
                    withContext(Dispatchers.IO) {
                        val jsonResponse = getJsonResponse(username, password)
                        println("Abbas $jsonResponse")
                        //use a json parser to access the res code
                        //if success navigate to new page
                        //store in the local database
                        //if error, stay, make the toast message, change UI

                        //home screen
                        //access the database and show on the screen
                    }
                }
            }
        }
    }

    private suspend fun getJsonResponse(username: String, password: String): String =
        withContext(Dispatchers.IO) {
            val urlEndpoint = "http://123.231.9.43:3999/"
            val url = URL(urlEndpoint)
            val connection = url.openConnection() as HttpURLConnection

            // Set the request method to POST
            connection.requestMethod = "POST"

            // Set the request properties
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")

            // Create the JSON request
            val jsonRequest = """
                {
                    "username": "$username",
                    "password": "$password"
                }
            """.trimIndent()

            // Enable output and set the request body
            connection.doOutput = true
            connection.outputStream.write(jsonRequest.toByteArray())

            // Get the response code
            val responseCode = connection.responseCode

            // If the response code indicates success, read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()

                val jsonResponse = response.toString()

                // Parse the JSON response
                val jsonObject = JSONObject(jsonResponse)
                val resCode = jsonObject.getInt("res_code")
                val resDesc = jsonObject.getString("res_desc")

                if(resCode == 0){
                    incorrectMessage.setText("")
                    val userDataObject = jsonObject.getJSONObject("user_data")
                    val id = userDataObject.getString("id")
                    val email = userDataObject.getString("email")
                    val name = userDataObject.getString("name")
                    val dob = userDataObject.getString("dob")
                    val gender = userDataObject.getString("gender")
                    val company = userDataObject.getString("company")
                    val position = userDataObject.getString("position")

                    // Print the extracted values
                    println("res_code: $resCode")
                    println("res_desc: $resDesc")
                    println("id: $id")
                    println("email: $email")
                    println("name: $name")
                    println("dob: $dob")
                    println("gender: $gender")
                    println("company: $company")
                    println("position: $position")

                    insertDetails(id,email,name,dob,gender,company,position)



                    openHomePage()
                }
                else if(resCode == -1){
                    incorrectMessage.setText("Username or Password is incorrect.")
                }






                //loginResponse(resCode)

                jsonResponse
            } else {
                "Error: $responseCode"
            }
        }

    private suspend fun insertDetails(id: String, email: String, name: String, dob: String, gender: String, company: String, position: String) {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "DetailDB").fallbackToDestructiveMigration().build()
        val detailDao = db.DetailsDaoDao()

        val details = Details(id = "ID: $id", email = "Email: $email", name = "Name: $name", dob = "Dob: $dob", gender = "Gender: $gender", company = "Company: $company", position = "Position: $position")
        detailDao.insertDetail(details)

    }

    private fun openHomePage() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
    }

}
