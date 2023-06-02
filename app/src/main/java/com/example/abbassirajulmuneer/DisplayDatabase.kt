package com.example.abbassirajulmuneer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DisplayDatabase : AppCompatActivity()  {
    lateinit var dbTitle: TextView
    lateinit var tableData: TextView
    private val dbData: ArrayList<String> = ArrayList()
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displaydatabase)

        dbTitle = findViewById(R.id.tableTitle)
        tableData = findViewById(R.id.tableData)

        displayDetailList()
    }


    private fun displayDetailList(){
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "DetailDB").fallbackToDestructiveMigration().build()
        val detailDao = db.DetailsDaoDao()

        runBlocking {
            launch {
                val details: List<Details> = detailDao.getAll()
                for (m in details) {
                    dbData.add("\n${m.id}\n ${m.name}\n ${m.email}\n ${m.dob}\n ${m.gender}\n ${m.company}\n ${m.position}\n")
                }

                for (m in dbData) {
                    result += m + "\n"
                }
                tableData.setText(result)
            }
        }
    }

}
