package com.example.abbassirajulmuneer


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DetailsDao {
    @Insert
    suspend fun insertAll(vararg detail: Details)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetail(vararg detail: Details)

    @Query("SELECT * FROM details")
    suspend fun getAll(): List<Details>

    @Query("SELECT * FROM details WHERE name LIKE '%' || :name || '%' ")
    suspend fun getSpecificDetailTitle(name: String): List<Details>


}