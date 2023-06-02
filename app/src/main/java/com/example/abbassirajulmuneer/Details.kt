package com.example.abbassirajulmuneer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class Details(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "dob")
    val dob: String?,

    @ColumnInfo(name = "gender")
    val gender: String?,

    @ColumnInfo(name = "company")
    val company: String?,

    @ColumnInfo(name = "position")
    val position: String?
) {

}
