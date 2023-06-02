package com.example.abbassirajulmuneer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomePageActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var viewprofile = findViewById<Button>(R.id.viewProfilebutton)

        viewprofile.setOnClickListener{
            val intent = Intent(this, DisplayDatabase::class.java)
            startActivity(intent)
        }

    }

}
