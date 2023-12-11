package com.example.localysearchrecyclerview.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class PostApiResponseItem(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String,
)