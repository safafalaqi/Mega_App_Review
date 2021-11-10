package com.example.megaapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Users")
data class User(
    @SerializedName("pk")
    @PrimaryKey
    val pk: Int?=null,
    @SerializedName("name")
    var name: String?,
    @SerializedName("location")
    var location: String?
)